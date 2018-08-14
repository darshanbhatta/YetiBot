package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.API;
import bot.discord.yeti.dictionary.Dic;
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

        if (arg.length != 1) {
            System.out.println(arg.length);
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


            if (arg.length == 2 && arg[1].equals("create")) {

                if (bank.getAccountIndex(e.getAuthor().getId()) != -1) {
                    msg.getChannel().sendMessage("You already have an account").queue(m -> {


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
                e.getGuild().getMembers().forEach(m -> users.add(m));
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
                                msg.getChannel().sendMessage("Successfully sent " + bank.getAllBalance().get(i).getName() + " " + Integer.parseInt(price) + " " + " \uD83D\uDC8E").queue(m -> {
                                });


                            } else {
                                msg.getChannel().sendMessage(Dic.bank1).queue(m -> {
                                });

                            }


                        }
                    } else {
                        msg.getChannel().sendMessage("Insufficient funds, you have " + bal + " \uD83D\uDC8E").queue(m -> {
                        });
                    }
                } else {
                    msg.getChannel().sendMessage(Dic.noAccount).queue(m -> {
                    });

                }


            } else if (arg.length == 2 && arg[1].equals("bal")) {
                int number = bank.getAccountIndex((e.getAuthor().getId()));

                if (number != -1) {
                    Bank finalBank = bank;
try{

    e.getMember().getUser().openPrivateChannel().queue((channel) ->{

        channel.sendMessage("You have " + finalBank.getAllBalance().get(number).getBalance() + " \uD83D\uDC8E").queue();

    });
}catch (Exception ww){
    e.getChannel().sendMessage("You have " + finalBank.getAllBalance().get(number).getBalance() + " \uD83D\uDC8E").queue();

}






                } else {

                    msg.getChannel().sendMessage(Dic.noAccount).queue(m -> {
                    });
                }
            }else if (arg.length == 4 && arg[1].equals("add")) {
                System.out.println(e.getAuthor().getId());
                if(e.getAuthor().getId().equals(API.darshDiscordID)||e.getAuthor().getId().equals(API.arcticDiscordID)){
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

                    } else {

                        msg.getChannel().sendMessage(Dic.noAccount).queue(m -> {
                        });
                    }

                }else{
                    msg.getChannel().sendMessage(Dic.noPermission).queue(m -> {

                    });
                }

            } else{
                switch(arg[1]) {
                    case "send":  msg.getChannel().sendMessage("Format: y!bank send @user <amount>").queue();
                        break;
                    case "add":   msg.getChannel().sendMessage("Format: y!bank add @user <amount>").queue();
                        break;
                    default:     msg.getChannel().sendMessage("Error, invalid command. Try y!bank to see all available commands").queue();

                }

            }

        }else{

            String reserve = "Available commands:\n\t y!bank create\n\t y!bank bal\n\t y!bank send\n\t";
            msg.getChannel().sendMessage(reserve).queue();

        }
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                msg.delete().queue();

            }
        }, 5000);
    }



}