package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.Dic;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
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

            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Coin Flip")
                    .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                    .addField("Format","```ycoinflip [heads, tails] <Optional: bet amount>```",true)
                    .build()).queue();

        }
        else if(arg.length == 2){
            if(!arg[1].equals("heads") && !arg[1].equals("tails")) {
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Coin Flip")
                        .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                        .addField("Format","```ycoinflip [heads, tails] <Optional: bet amount>```",true)
                        .build()).queue();
            }
            else{
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                        .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                        .setDescription("3")
                        .build()).queue(m -> {

                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            m.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                    .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                                    .setDescription("2")
                                    .build()).queue(m -> {
                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {

                                    @Override
                                    public void run() {
                                        m.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                                                .setDescription("1")
                                                .build()).queue(m -> {
                                            Timer timer = new Timer();
                                            timer.schedule(new TimerTask() {

                                                @Override
                                                public void run() {
                                                    double random = Math.random();
                                                    if (random < 0.5) {//tails
                                                        if (bet.equals("heads")) {
                                                            if (placeBet) {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                        .setDescription("Tails!\nYou lost " + (Integer.valueOf(price)) + " :gem:")
                                                                        .build()).queue();

                                                            } else {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                        .setDescription("Tails!\nYou lost.")
                                                                        .build()).queue();
                                                                //m.editMessage("Tails\nYou lost.").queue();
                                                            }
                                                        } else {
                                                            if (placeBet) {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                        .setDescription("Tails!\n You won " + 2 * (Integer.valueOf(price)) + " :gem:")
                                                                        .build()).queue();

                                                                bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                                completeTransaction();
                                                            } else {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                        .setDescription("Tails!\n You won ")
                                                                        .build()).queue();
                                                            }

                                                        }
                                                    } else {//heads
                                                        if (bet.equals("tails")) {
                                                            if (placeBet) {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                        .setDescription("Heads!\nYou lost " + (Integer.valueOf(price)) + " :gem:")
                                                                        .build()).queue();
                                                            } else {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                        .setDescription("Heads!\nYou lost!")
                                                                        .build()).queue();
                                                            }
                                                        } else {
                                                            if (placeBet) {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                        .setDescription("Heads!\n You won " + 2 * (Integer.valueOf(price)) + " :gem:")
                                                                        .build()).queue();
                                                                bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                                completeTransaction();
                                                            } else {
                                                                m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                        .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                        .setDescription("Heads!\n You won!")
                                                                        .build()).queue();
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
                bet = arg[1];

            }
        }
        else if(arg.length == 3) {
            if (i != -1) {
                bet = arg[1];
                price = arg[2];
                placeBet = true;
                try {
                    int betAmount = Integer.parseInt(arg[2]);
                    int bal = bank.getAllBalance().get(i).getBalance();
                    System.out.println(betAmount);
                    if (betAmount > 0) {
                        if (bal >= betAmount) {
                            bank.getAllBalance().get(i).setBalance(bal - Integer.parseInt(price));
                            completeTransaction();




                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                    .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                                    .setDescription("3")
                                    .build()).queue(m -> {

                                Timer timer = new Timer();
                                timer.schedule(new TimerTask() {

                                    @Override
                                    public void run() {
                                        m.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                                                .setDescription("2")
                                                .build()).queue(m -> {
                                            Timer timer = new Timer();
                                            timer.schedule(new TimerTask() {

                                                @Override
                                                public void run() {
                                                    m.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                            .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                                                            .setDescription("1")
                                                            .build()).queue(m -> {
                                                        Timer timer = new Timer();
                                                        timer.schedule(new TimerTask() {

                                                            @Override
                                                            public void run() {
                                                                double random = Math.random();
                                                                if (random < 0.5) {//tails
                                                                    if (bet.equals("heads")) {
                                                                        if (placeBet) {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                                    .setDescription("Tails!\nYou lost " + (Integer.valueOf(price)) + " :gem:")
                                                                                    .build()).queue();

                                                                        } else {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                                    .setDescription("Tails!\nYou lost.")
                                                                                    .build()).queue();
                                                                            //m.editMessage("Tails\nYou lost.").queue();
                                                                        }
                                                                    } else {
                                                                        if (placeBet) {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                                    .setDescription("Tails!\n You won " + 2 * (Integer.valueOf(price)) + " :gem:")
                                                                                    .build()).queue();

                                                                            bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                                            completeTransaction();
                                                                        } else {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/bfHNlJW.png")
                                                                                    .setDescription("Tails!\n You won!")
                                                                                    .build()).queue();
                                                                        }

                                                                    }
                                                                } else {//heads
                                                                    if (bet.equals("tails")) {
                                                                        if (placeBet) {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                                    .setDescription("Heads!\nYou lost " + (Integer.valueOf(price)) + " :gem:")
                                                                                    .build()).queue();
                                                                        } else {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                                    .setDescription("Heads!\n You lost!")
                                                                                    .build()).queue();
                                                                        }
                                                                    } else {
                                                                        if (placeBet) {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                                    .setDescription("Heads!\n You won " + 2 * (Integer.valueOf(price)) + " :gem:")
                                                                                    .build()).queue();
                                                                            bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                                                                            completeTransaction();
                                                                        } else {
                                                                            m.editMessage( new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(":moneybag:Coinflip:moneybag:")
                                                                                    .setThumbnail("https://i.imgur.com/u2LaWB9.png")
                                                                                    .setDescription("Heads!\n You won!")
                                                                                    .build()).queue();
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

                        } else {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Bank")
                                    .setThumbnail("https://images.emojiterra.com/twitter/v11/512px/1f48e.png")
                                    .setDescription("Insufficient funds, you have " + bal + " \uD83D\uDC8E")
                                    .build()).queue();


                        }


                    } else {

                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Bank")
                                .setThumbnail("https://images.emojiterra.com/twitter/v11/512px/1f48e.png")
                                .setDescription(Dic.postiveNum)
                                .build()).queue();
                    }


                } catch (Exception e) {


                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Bank")
                            .setThumbnail("https://images.emojiterra.com/twitter/v11/512px/1f48e.png")
                            .setDescription(Dic.postiveNum)
                            .build()).queue();

                }
            }else{


                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Bank")
                        .setThumbnail("https://images.emojiterra.com/twitter/v11/512px/1f48e.png")
                        .setDescription(Dic.noAccount)
                        .build()).queue();

            }


        }
        else{
            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Coin Flip")
                    .setThumbnail("https://lh6.ggpht.com/WI_lOeFpGKlj-mMGUJC1OqopcbXdwyPPxbcAqWSozCZhZh9fV_0O__HImZE2qjyv7rkf=w300")
                    .addField("Format","```ycoinflip [heads, tails] <Optional: bet amount>```",true)
                    .build()).queue();
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





}
