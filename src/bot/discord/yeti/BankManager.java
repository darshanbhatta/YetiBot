package bot.discord.yeti;

import bot.discord.yeti.currency.Bank;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class BankManager {


    public static void run(Message msg, MessageReceivedEvent e) throws IOException {

        //!bank
        //send
        //balance
        //init
        //help

        ArrayList<Member> users = new ArrayList();
        String[] arg = msg.getContentRaw().split(" ");
        e.getGuild().getMembers().forEach(m -> users.add(m));
        if (arg.length != 1) {
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


            if (arg.length == 2 && arg[1].equals("init")) {

                if (bank.getAccountIndex(e.getAuthor().getId()) != -1) {
                    msg.getChannel().sendMessage("Error you already have an account").queue(m -> {


                    });

                } else {
                    bank.addUser(e.getAuthor().getId(), e.getAuthor().getName(), 100);
                    msg.getChannel().sendMessage("Successfully created an account for " + e.getAuthor().getName()).queue(m -> {
                    });

                }


                try {
                    FileOutputStream fileOut =
                            new FileOutputStream("bank.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(bank);
                    out.close();
                    fileOut.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }


            } else if (arg.length == 4 && arg[1].equals("send")) {
                String price = arg[3];
                int org = bank.getAccountIndex(e.getAuthor().getId());
                int bal = bank.getAllBalance().get(org).getBalance();
                // System.out.println(price + " " + bal + " " );
                if (org != -1) {
                    if (bank.getAllBalance().get(org).getBalance() >= Integer.parseInt(price)) {
                        String username = arg[2].substring(2, arg[2].indexOf(">"));

                        boolean user = true;
                        for (int x = 0; x < users.size(); x++) {

                            if (username.contains(users.get(x).getUser().getId())) {
                                user = true;
                                break;

                            } else {
                                user = false;
                            }


                        }
                        if (user) {
                            int i = bank.getAccountIndex(username);

                            if (i != -1) {
                                System.out.println(Integer.parseInt(price) + "");
                                bank.getAllBalance().get(org).setBalance(bal - Integer.parseInt(price));
                                bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + Integer.parseInt(price));
                                try {
                                    FileOutputStream fileOut =
                                            new FileOutputStream("bank.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(bank);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                                msg.getChannel().sendMessage("Sucessfully sent " + bank.getAllBalance().get(i).getName() + " " + Integer.parseInt(price) + " " + " \uD83D\uDC8E").queue(m -> {
                                });


                            } else {
                                msg.getChannel().sendMessage("Error recipient does not have an account, tell them to type !bank init to make one").queue(m -> {
                                });

                            }


                        }
                    } else {
                        msg.getChannel().sendMessage("Error insufficient funds, you have " + bal + " \uD83D\uDC8E").queue(m -> {
                        });
                    }
                } else {
                    msg.getChannel().sendMessage("Error you do not have an account, type !bank init to make one" + bal + " \uD83D\uDC8E").queue(m -> {
                    });

                }


            } else if (arg.length == 2 && arg[1].equals("balance")) {
                int number = bank.getAccountIndex((e.getAuthor().getId()));

                if (number != -1) {
                    Bank finalBank = bank;
                    e.getMember().getUser().openPrivateChannel().queue((channel) ->{

                        channel.sendMessage("You have " + finalBank.getAllBalance().get(number).getBalance() + " \uD83D\uDC8E").queue();

                            });





                } else {

                    msg.getChannel().sendMessage("Error you do not have an account type !bank init to make one").queue(m -> {

                    });
                }
            }else if (arg.length == 4 && arg[1].equals("add")) {
                System.out.println(e.getMember().hasPermission(Permission.ADMINISTRATOR));
                if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
String addToWho = arg[2].substring(2,arg[2].indexOf(">"));

                    int org = bank.getAccountIndex(addToWho);
                    int bal = bank.getAllBalance().get(org).getBalance();
                    String price = arg[3];
                   // int number = bank.getAccountIndex((e.getAuthor().getId()));
                    System.out.println(org);
                    if (org != -1) {
                        bank.getAllBalance().get(org).setBalance(bal +Integer.parseInt(price));
                        msg.getChannel().sendMessage("Added " + price+ " \uD83D\uDC8E to " + bank.getAllBalance().get(org).getName()+"'s account" ).queue(m -> {
                        });

                    } else {

                        msg.getChannel().sendMessage("Error you do not have an account type !bank init to make one").queue(m -> {

                        });
                    }

                }else{
                    msg.getChannel().sendMessage("Error you do not have permission to make this command").queue(m -> {

                    });
                }

            } else{
            msg.getChannel().sendMessage("Error invalid format These are the available commands\n!bank init\n!bank balance\n!bank send @user %amount%").queue(m -> {

            });

        }

        }
    }



}
