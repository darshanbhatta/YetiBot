package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;

import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;

import bot.discord.yeti.game.jackpot.JackpotTicket;
import bot.discord.yeti.game.jackpot.JackpotUser;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Timer;
import java.util.TimerTask;

public class JackpotManager {


    public static void run(MessageReceivedEvent e) {
        //!roulette init !roulette bet 500 red/black/green/even/odd

        String[] code = e.getMessage().getContentRaw().split(" ");
        final JackpotGameHolder[] jackpotGameHolders = new JackpotGameHolder[1];
        try {
            FileInputStream fileIn = new FileInputStream("jackpot.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            jackpotGameHolders[0] = (JackpotGameHolder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

        final Bank[] bank = {new Bank()};
        try {
            FileInputStream fileIn = new FileInputStream("bank.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank[0] = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
        int bankAccountIndex = bank[0].getAccountIndex(e.getAuthor().getId());
        if(code.length == 1){
            e.getChannel().sendMessage("Format: !jackpot start").queue();
        }
        if (code.length == 2 && code[1].equals("start")) {
            boolean gameInProg = false;
            String serverid = null;
            for (int x = 0; x < jackpotGameHolders[0].getJackPotGame().size(); x++) {
                serverid = e.getGuild().getId();

                if (serverid.equals(jackpotGameHolders[0].getJackPotGame().get(x).getServerid())) {

                    gameInProg = true;


                }
            }
            if (!gameInProg) {
                jackpotGameHolders[0].getJackPotGame().add(new JackpotGame(true, e.getGuild().getId()));

                try {
                    FileOutputStream fileOut = new FileOutputStream("jackpot.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(jackpotGameHolders[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }

                e.getChannel().sendMessage("Game created! 30 seconds to place bets.\nFormat: !jackpot bet <bet amount>").queue(m -> {

                });

                Timer ee = new Timer();
                String finalServerid = serverid;
                ee.schedule(new TimerTask() {

                    @Override
                    public void run() {

                        String finalServerid = e.getGuild().getId();
                        final Bank[] finalBank = {bank[0]};

                        e.getChannel().sendMessage("Final bets! Rolling in 3 seconds!...").queue(m -> {
                            Timer timer = new Timer();
                            timer.schedule(new TimerTask() {

                                @Override
                                public void run() {
                                    m.editMessage("Final bets! Rolling in 2 seconds!...").queue(m -> {
                                        Timer timer = new Timer();
                                        timer.schedule(new TimerTask() {

                                            @Override
                                            public void run() {
                                                m.editMessage("Final bets! Rolling in 1 seconds!...").queue(m -> {
                                                    Timer timer = new Timer();
                                                    timer.schedule(new TimerTask() {

                                                        @Override
                                                        public void run() {
                                                            try {
                                                                FileInputStream fileIn = new FileInputStream("jackpot.ser");
                                                                ObjectInputStream in = new ObjectInputStream(fileIn);
                                                                jackpotGameHolders[0] = (JackpotGameHolder) in.readObject();
                                                                in.close();
                                                                fileIn.close();
                                                            } catch (IOException i) {
                                                                i.printStackTrace();
                                                                return;
                                                            } catch (ClassNotFoundException c) {
                                                                c.printStackTrace();
                                                                return;
                                                            }
                                                            int indx = 0;
                                                            for (int x = 0; x < jackpotGameHolders[0].getJackPotGame().size(); x++) {
                                                                if (jackpotGameHolders[0].getJackPotGame().get(x).getServerid() != null) {

                                                                    if (finalServerid.equals(jackpotGameHolders[0].getJackPotGame().get(x).getServerid())) {
                                                                        indx = x;
                                                                            break;

                                                                    }


                                                                }
                                                            }
                                                            int rollNumber = (int) ((Math.random() * (jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotTicket().size())));

                                                            int finalIndx = indx;

                                                            if(jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotTicket().size()!=0){

                                                                String prob = "";
                                                                for(int x =0; x<jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotUsers().size();x++){
                                                                    //System.out.println(jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotUsers().get(x).getTicket() + " " + jackpotGameHolders[0].getJackPotGame().get(indx).getPot()*100);
                                                                 //   System.out.println((jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotUsers().get(x).getTicket() +" "+jackpotGameHolders[0].getJackPotGame().get(indx).getPot()*100));
                                                                    BigDecimal a = new BigDecimal((((double)jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotUsers().get(x).getTicket()/(double)jackpotGameHolders[0].getJackPotGame().get(indx).getPot()*100)));
                                                                    BigDecimal b = a.setScale(2, RoundingMode.DOWN);
                                                                    prob+="\t"+ jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotUsers().get(x).getName() + " | " +  b +"%\n";



                                                                }

                                                                System.out.println(jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotTicket().get(rollNumber));
                                                                m.editMessage("Pot size: " + jackpotGameHolders[0].getJackPotGame().get(indx).getPot() + "\uD83D\uDC8E\nWinner: " +jackpotGameHolders[0].getJackPotGame().get(indx).getJackpotTicket().get(rollNumber).getName()+"\n\nProbability (%)\n" + prob).queue(message -> {

                                                                    try {
                                                                        FileInputStream fileIn = new FileInputStream("bank.ser");
                                                                        ObjectInputStream in = new ObjectInputStream(fileIn);
                                                                        finalBank[0] = (Bank) in.readObject();
                                                                        in.close();
                                                                        fileIn.close();
                                                                    } catch (IOException i) {
                                                                        i.printStackTrace();
                                                                        return;
                                                                    } catch (ClassNotFoundException c) {
                                                                        c.printStackTrace();
                                                                        return;
                                                                    }
                                                                    //         System.out.println("Game indx " + jackpotGameHolders[0].getJackPotGame().get(indx).getRouletteUsers().size());

                                                                    int ida = finalBank[0].getAccountIndex(jackpotGameHolders[0].getJackPotGame().get(finalIndx).getJackpotTicket().get(rollNumber).getUserid());
                                                                    int potVal = jackpotGameHolders[0].getJackPotGame().get(finalIndx).getPot();
                                                                    finalBank[0].getAllBalance().get(ida).setBalance(finalBank[0].getAllBalance().get(ida).getBalance() + (int) (potVal*.95));
                                                                    //      System.out.println(userMulti);



                                                                    jackpotGameHolders[0].getJackPotGame().remove(finalIndx);

                                                                    try {
                                                                        FileOutputStream fileOut = new FileOutputStream("jackpot.ser");
                                                                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                                                        out.writeObject(jackpotGameHolders[0]);
                                                                        out.close();
                                                                        fileOut.close();
                                                                    } catch (IOException ww) {
                                                                        ww.printStackTrace();
                                                                    }
                                                                    try {
                                                                        FileOutputStream fileOut = new FileOutputStream("bank.ser");
                                                                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                                                        out.writeObject(finalBank[0]);
                                                                        out.close();
                                                                        fileOut.close();
                                                                    } catch (IOException ww) {
                                                                        ww.printStackTrace();
                                                                    }


                                                                });




                                                            }else{

                                                                m.editMessage("No bets have been placed.").queue();
                                                                jackpotGameHolders[0].getJackPotGame().remove(finalIndx);

                                                                try {
                                                                    FileOutputStream fileOut = new FileOutputStream("jackpot.ser");
                                                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                                                    out.writeObject(jackpotGameHolders[0]);
                                                                    out.close();
                                                                    fileOut.close();
                                                                } catch (IOException ww) {
                                                                    ww.printStackTrace();
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


//some animation


                        });


                    }


                }, 30000);


            } else {
                e.getChannel().sendMessage("Game is already in progress. Format: !jackpot bet <bet amount>").queue();

            }

        } else if (code.length == 3 && code[1].equals("bet")) {
            if (bankAccountIndex != -1) {
                int currentBal = bank[0].getAllBalance().get(bankAccountIndex).getBalance();
                try {

                    int bet = Integer.parseInt(code[2]); // try catch

                    //String whatBet = code[3]; //check if valid bet
                    boolean vaildBet = true;

                        if(bet>0){

                            boolean found = false;
                            if (bet <= currentBal) {

                               // System.out.println(bank[0].getAllBalance().get(bankAccountIndex).getBalance());
                                for (int x = 0; x < jackpotGameHolders[0].getJackPotGame().size(); x++) {
                                    String se = e.getGuild().getId();
                                    if (se.equals(jackpotGameHolders[0].getJackPotGame().get(x).getServerid())) {
                                   //     System.out.println(se + " " + jackpotGameHolders[0].getJackPotGame().get(x).getServerid() + " " + x);
                                        for(int i =0;i<bet;i++){

                                            jackpotGameHolders[0].getJackPotGame().get(x).getJackpotTicket().add(new JackpotTicket(e.getAuthor().getId(),e.getAuthor().getName()));

                                        }
                                        jackpotGameHolders[0].getJackPotGame().get(x).getJackpotUsers().add(new JackpotUser(e.getAuthor().getId(),e.getAuthor().getName(),bet));
                                        jackpotGameHolders[0].getJackPotGame().get(x).addPot(bet);

                                        try {
                                            FileOutputStream fileOut = new FileOutputStream("jackpot.ser");
                                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                            out.writeObject(jackpotGameHolders[0]);
                                            out.close();
                                            fileOut.close();
                                        } catch (IOException ww) {
                                            ww.printStackTrace();
                                        }
                                        found = true;
                                        bank[0].getAllBalance().get(bankAccountIndex).setBalance(currentBal - bet);
                                        try {
                                            FileOutputStream fileOut = new FileOutputStream("bank.ser");
                                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                            out.writeObject(bank[0]);
                                            out.close();
                                            fileOut.close();
                                        } catch (IOException ww) {
                                            ww.printStackTrace();
                                        }

                                    }


                                }

                                if (!found)
                                    e.getChannel().sendMessage("No game in progress. See !jackpot for commands.").queue();
                                //error no running games


                            } else {

                                e.getChannel().sendMessage("Insufficient funds, you have " + currentBal + " \uD83D\uDC8E").queue(m -> {
                                });


                            }









                        }else{

                            e.getChannel().sendMessage("Invalid bet, you can only bet positive integers");


                        }








                } catch (Exception e1) {
                    e.getChannel().sendMessage("You can only bet integers.");

                }

            }else{

            e.getChannel().sendMessage("You do not have a bank account to bet with, try !bank init to make one");


        }
        }else{

            e.getChannel().sendMessage("Invalid command. See !jackpot for commands.");

        }
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);
    }
}

