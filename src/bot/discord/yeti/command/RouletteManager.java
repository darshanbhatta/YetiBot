package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.roulette.Roulette;
import bot.discord.yeti.game.roulette.RouletteGame;
import bot.discord.yeti.game.roulette.RouletteGameHolder;
import bot.discord.yeti.game.roulette.RouletteUsers;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class RouletteManager {


    public static void run(MessageReceivedEvent e) {
        //!roulette init !roulette bet 500 red/black/green/even/odd

        String[] code = e.getMessage().getContentRaw().split(" ");
        final RouletteGameHolder[] rouletteGame = new RouletteGameHolder[1];
        try {
            FileInputStream fileIn = new FileInputStream("roulette.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            rouletteGame[0] = (RouletteGameHolder) in.readObject();
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
            e.getChannel().sendMessage("Start a game\n!roulette start\n\nPlace a bet\n!roulette bet <bet amount> [red, black, green, odd, even, <1-36>]").queue();
        }
        if (code.length == 2 && code[1].equals("start")) {
            boolean gameInProg = false;
            String serverid = null;
            for (int x = 0; x < rouletteGame[0].getRouletteGames().size(); x++) {
                serverid = e.getGuild().getId();

                if (serverid.equals(rouletteGame[0].getRouletteGames().get(x).getServerid())) {

                    gameInProg = true;


                }
            }
            if (!gameInProg) {
                rouletteGame[0].getRouletteGames().add(new RouletteGame(true, e.getGuild().getId()));

                try {
                    FileOutputStream fileOut = new FileOutputStream("roulette.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(rouletteGame[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }

                e.getChannel().sendMessage("Game created! 30 seconds to place bets, type !roulette bet").queue(m -> {

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

                                                            int rollNumber = (int) ((Math.random() * (37)));
                                                            System.out.println(rollNumber);
                                                            String icon = "";

                                                            if (rollNumber == 1 || rollNumber == 3 || rollNumber == 5 || rollNumber == 7 || rollNumber == 9 || rollNumber == 12 || rollNumber == 14 || rollNumber == 16 || rollNumber == 18 || rollNumber == 19 || rollNumber == 21 || rollNumber == 23 || rollNumber == 25 || rollNumber == 27 || rollNumber == 30 || rollNumber == 32 || rollNumber == 34 || rollNumber == 36) {
                                                                icon = "🔴  Red";
                                                                //red

                                                            } else if (rollNumber == 2 || rollNumber == 4 || rollNumber == 6 || rollNumber == 8 || rollNumber == 10 || rollNumber == 11 || rollNumber == 13 || rollNumber == 15 || rollNumber == 17 || rollNumber == 20 || rollNumber == 22 || rollNumber == 24 || rollNumber == 26 || rollNumber == 28 || rollNumber == 29 || rollNumber == 31 || rollNumber == 33 || rollNumber == 35) {
                                                                icon = "⚫ Black";
                                                                //black

                                                            } else if (rollNumber == 0) {
                                                                icon = "🍀 Green";
                                                                //green


                                                            }

                                                            m.editMessage(rollNumber + " | " + icon).queue(message -> {
                                                                try {
                                                                    FileInputStream fileIn = new FileInputStream("roulette.ser");
                                                                    ObjectInputStream in = new ObjectInputStream(fileIn);
                                                                    rouletteGame[0] = (RouletteGameHolder) in.readObject();
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
                                                                for (int x = 0; x < rouletteGame[0].getRouletteGames().size(); x++) {
                                                                    if (rouletteGame[0].getRouletteGames().get(x).getServerid() != null) {

                                                                        if (finalServerid.equals(rouletteGame[0].getRouletteGames().get(x).getServerid())) {
                                                                            indx = x;
                                                                            break;

                                                                        }


                                                                    }
                                                                }
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
                                                                //         System.out.println("Game indx " + rouletteGame[0].getRouletteGames().get(indx).getRouletteUsers().size());
                                                                for (int x = 0; x < rouletteGame[0].getRouletteGames().get(indx).getRouletteUsers().size(); x++) {
                                                                    int ida = finalBank[0].getAccountIndex(rouletteGame[0].getRouletteGames().get(indx).getRouletteUsers().get(x).getUserid());
                                                                    int userBalance;
                                                                    String whatBet = rouletteGame[0].getRouletteGames().get(indx).getRouletteUsers().get(x).getBet();
                                                                    int bet = rouletteGame[0].getRouletteGames().get(indx).getRouletteUsers().get(x).getBetAmount();
                                                                    Roulette ga = new Roulette(rollNumber, whatBet);
                                                                    ga.calcMulti();
                                                                    int userMulti = ga.getMultiplier();
                                                                    finalBank[0].getAllBalance().get(ida).setBalance(finalBank[0].getAllBalance().get(ida).getBalance() + (userMulti * bet));
                                                                    //      System.out.println(userMulti);


                                                                }
                                                                rouletteGame[0].getRouletteGames().remove(indx);

                                                                try {
                                                                    FileOutputStream fileOut = new FileOutputStream("roulette.ser");
                                                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                                                    out.writeObject(rouletteGame[0]);
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
                e.getChannel().sendMessage("Error game already in progress, try !roulette bet").queue();

            }

        } else if (code.length == 4 && code[1].equals("bet")) {
            if (bankAccountIndex != -1) {
                int currentBal = bank[0].getAllBalance().get(bankAccountIndex).getBalance();
                try {

                    int bet = Integer.parseInt(code[2]); // try catch

                    String whatBet = code[3]; //check if valid bet
                    boolean vaildBet = true;

                    if(bet>0){
                        try{
                            int num =       Integer.parseInt(whatBet);

                            if(num<37&&num>-1){
                                vaildBet = true;
                            }

                        }catch (Exception w){

                            vaildBet = whatBet.equals("red")||whatBet.equals("black")||whatBet.equals("odd")||whatBet.equals("even")||whatBet.equals("green");
                        }
                        if(vaildBet){

                            boolean found = false;
                            if (bet <= currentBal) {

                                // System.out.println(bank[0].getAllBalance().get(bankAccountIndex).getBalance());
                                for (int x = 0; x < rouletteGame[0].getRouletteGames().size(); x++) {
                                    String se = e.getGuild().getId();
                                    if (se.equals(rouletteGame[0].getRouletteGames().get(x).getServerid())) {
                                        //     System.out.println(se + " " + rouletteGame[0].getRouletteGames().get(x).getServerid() + " " + x);
                                        rouletteGame[0].getRouletteGames().get(x).getRouletteUsers().add(new RouletteUsers(e.getAuthor().getId(), whatBet, bet));
                                        try {
                                            FileOutputStream fileOut = new FileOutputStream("roulette.ser");
                                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                            out.writeObject(rouletteGame[0]);
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
                                    e.getChannel().sendMessage("No game in progress. Format: !roulette init").queue();
                                //error no running games


                            } else {

                                e.getChannel().sendMessage("Error insufficient funds, you have " + currentBal + " \uD83D\uDC8E").queue(m -> {
                                });


                            }







                        }else{

                            e.getChannel().sendMessage("Invalid bet, roulette only supports red, black, even, odd, or a number from 1-36");

                        }

                    }else{

                        e.getChannel().sendMessage("Invalid bet, you can only bet positive integers");


                    }








                } catch (Exception e1) {
                    e.getChannel().sendMessage("You can only bet integers");

                }

            }else{

                e.getChannel().sendMessage("You do not have a bank account to bet with, try !bank init to make one");


            }
        }else{
            if(code.length >= 2) {
                e.getChannel().sendMessage("Invalid Command. See !roulette for commands.").queue();
            }
        }
    }
}