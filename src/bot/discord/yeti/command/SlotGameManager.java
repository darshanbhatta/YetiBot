package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.slot.SlotMachine;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class SlotGameManager {


    public static void run(Message msg, MessageReceivedEvent e) throws IOException {
        Bank bank = new Bank();
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
        int indx = bank.getAccountIndex(e.getAuthor().getId());
        String[] arg = msg.getContentRaw().split(" ");
        int price = 0;
        if (arg.length == 2 && indx != -1) {

            try {
                // checking valid integer using parseInt() method
                price = Integer.parseInt(arg[1]);
                int currentBal = bank.getAllBalance().get(indx).getBalance();

                if (price <= currentBal) {
                    if(price>0){

                        SlotMachine slot = new SlotMachine();
                        int finalPrice = price;
                        Bank finalBank = bank;
                        int finalPrice1 = price;

                        msg.getChannel().sendMessage(   new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.empty()).build()).queue(message -> {


                            Timer time = new Timer();
                            time.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    slot.play();

                                    message.editMessage(    new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.firstIter()).build()).queue(message1 -> {
                                        Timer time = new Timer();
                                        time.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                message.editMessage(  new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.secondIter()).build()
                                                ).queue(message2 -> {
                                                    Timer time = new Timer();
                                                    time.schedule(new TimerTask() {
                                                        @Override
                                                        public void run() {
                                                            String winningMessage;
                                                            if (slot.getWinMultiplier() == 0) {
                                                                finalBank.getAllBalance().get(indx).setBalance(currentBal - finalPrice1);
                                                                winningMessage = "You lose.";


                                                            } else if (slot.getWinMultiplier() == 1) {

                                                                winningMessage = "You broke even. Returned " +  (Integer.valueOf(finalPrice)) + " :gem:";

                                                            } else {
                                                                finalBank.getAllBalance().get(indx).setBalance(currentBal + (finalPrice1 * slot.getWinMultiplier()));
                                                                winningMessage = "You win " + finalPrice * slot.getWinMultiplier() + " \uD83D\uDC8E";

                                                            }
                                                            message.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.thirdIter() + "\n" + " " + winningMessage).build()
                                                            ).queue();

                                                            try {
                                                                FileOutputStream fileOut =
                                                                        new FileOutputStream("bank.ser");
                                                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                                                out.writeObject(finalBank);
                                                                out.close();
                                                                fileOut.close();
                                                            } catch (IOException i) {
                                                                i.printStackTrace();
                                                            }
                                                        }
                                                    }, 750);

                                                });
                                            }
                                        }, 750);


                                    });
                                }
                            }, 750);


                        });
                    }else {
                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You can only bet positive integers","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png").build()).queue(m -> {
                        });

                    }




                    }else{
                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Insufficient funds, you have " + currentBal + " \uD83D\uDC8E","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png").build()
                    ).queue(m -> {
                    });


                    }

            } catch (NumberFormatException woah) {
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You can only bet integers","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png").build()).queue(m -> {
                });
            }

        } else if(arg.length==2){


            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You do not have a bank account to bet with, try ybank create to make one","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png","https://s3.amazonaws.com/ais-technolabs-2018/20180627073839/jsm.png").build()).queue(m -> {
            });

        }else

        {

            SlotMachine slot = new SlotMachine();


            msg.getChannel().sendMessage(   new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.empty()).build()).queue(message -> {


                Timer time = new Timer();
                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        slot.play();
                        message.editMessage(    new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.firstIter()).build()).queue(message1 -> {
                            Timer time = new Timer();
                            time.schedule(new TimerTask() {
                                @Override
                                public void run() {

                                    message.editMessage(    new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.secondIter()).build()).queue(message1 -> {
                                        Timer time = new Timer();
                                        time.schedule(new TimerTask() {
                                            @Override
                                            public void run() {
                                                String winningMessage;
                                                if (slot.getWinMultiplier() == 0) {
                                                    winningMessage = "You lose.";

                                                } else if (slot.getWinMultiplier() == 1) {

                                                    winningMessage = "You broke even.";

                                                } else {
                                                    winningMessage = "You won on " + "✖" + slot.getWinMultiplier() + " \uD83D\uDC8E";

                                                }
                                                message.editMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setDescription(slot.thirdIter() + "\n" + " " + winningMessage).build()
                                                ).queue();
                                            }
                                        }, 750);

                                    });
                                }
                            }, 750);


                        });
                    }
                }, 750);


            });


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
