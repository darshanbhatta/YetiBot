package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.Dic;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Coinflip {
    private static ArrayList<Member> users = new ArrayList();
    private static Message publicmsg;
    private static int i;
    private static Bank bank;
    private static String[] arg;
    private static String price;
    private static boolean placeBet;
    private static String bet;
    private static Message m;

    public static void run(Message msg) throws IOException {
        publicmsg = msg;
        placeBet = false;

        msg.getGuild().getMembers().forEach(m -> users.add(m));

        try {
            FileInputStream fileIn = new FileInputStream("bank.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
        arg = msg.getContentRaw().split(" ");

        i = bank.getAccountIndex(msg.getAuthor().getId());

        if(arg.length == 1){
            publicmsg.getChannel().sendMessage("Format !coinflip [heads, tails] <Optional: bet amount>").queue();
        }
        else if(arg.length == 2){
            if(!arg[1].equals("heads") && !arg[1].equals("tails")) {
                msg.getChannel().sendMessage("Format: !coinflip [heads, tails] <Optional: bet amount>").queue();
            }
            else{
                publicmsg.getChannel().sendMessage(":moneybag:Coinflip:moneybag:").queue();
                bet = arg[1];
                countDown();
            }
        }
        else if(arg.length == 3) {
            if (i != -1) {
                bet = arg[1];
                price = arg[2];
                try {
                    int betAmount = Integer.parseInt(arg[2]);
                    int bal = bank.getAllBalance().get(i).getBalance();
                    System.out.println(betAmount);
                    if (betAmount > 0) {
                        if (bal >= betAmount) {
                            bank.getAllBalance().get(i).setBalance(bal - Integer.parseInt(price));
                            completeTransaction();
                            completeTransaction();

                            msg.getChannel().sendMessage(":moneybag:Coinflip:moneybag:").queue(m -> {
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            placeBet = true;
                            countDown();
                        } else {
                            msg.getChannel().sendMessage("Error insufficient funds, you have " + bal + " \uD83D\uDC8E").queue(m -> {
                            });


                        }


                    } else {
                        msg.getChannel().sendMessage(Dic.postiveNum).queue(m -> {
                        });
                    }


                } catch (Exception e) {
                    msg.getChannel().sendMessage(Dic.postiveNum).queue(m -> {
                    });
                }
            }else{
                msg.getChannel().sendMessage(Dic.noAccount).queue(m -> {
                });
            }


        }
        else{
            msg.getChannel().sendMessage("Please use the format !coinflip [heads, tails] <Optional: bet amount>").queue();
        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                msg.delete().queue();

            }
        }, 5000);
    }
    public static void completeTransaction(){
        try {
            FileOutputStream fileOut = new FileOutputStream("bank.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(bank);
            out.close();
            fileOut.close();
        } catch (IOException ww) {
            ww.printStackTrace();
        }
    }

    public static void countDown(){
        publicmsg.getChannel().sendMessage("3").queue(m -> {


            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    m.editMessage("2").queue(m -> {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                m.editMessage("1").queue(m -> {
                                    Timer timer = new Timer();
                                    timer.schedule(new TimerTask() {

                                        @Override
                                        public void run() {
                                            double random = Math.random();
                                            if(random < 0.5){//tails
                                                if(bet.equals("heads")) {
                                                    if(placeBet) {
                                                        m.editMessage("Tails\nYou lost " + (Integer.valueOf(price)) + " :gem:").queue();
                                                    }
                                                    else{
                                                        m.editMessage("Tails\nYou lost.").queue();
                                                    }
                                                }
                                                else{
                                                    if(placeBet) {
                                                        m.editMessage("You won " + 2 * (Integer.valueOf(price)) + " :gem:").queue();
                                                        bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                        completeTransaction();
                                                    }
                                                    else{
                                                        m.editMessage("You won.").queue();
                                                    }

                                                }
                                            }
                                            else{//heads
                                                if(bet.equals("tails")) {
                                                    if(placeBet) {
                                                        m.editMessage("Heads\nYou lost " + (Integer.valueOf(price)) + " :gem:").queue();
                                                    }
                                                    else{
                                                        m.editMessage("Heads\nYou lost.").queue();
                                                    }
                                                }
                                                else {
                                                    if (placeBet) {
                                                        m.editMessage("You win " + 2 * (Integer.valueOf(price)) + " :gem:").queue();
                                                        bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                        completeTransaction();
                                                    } else {
                                                        m.editMessage("You win.").queue();
                                                    }
                                                }

                                            }
                                        }
                                    }, 1000);
                                });
                            }
                        }, 1000);
                    });
                }
            }, 1000);

        });
    }


}
