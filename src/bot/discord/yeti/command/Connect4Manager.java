package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.connect4.Connect4Game;
import bot.discord.yeti.game.connect4.Connect4Holder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class Connect4Manager {


    public static void run(MessageReceivedEvent e) {
        //!roulette init !roulette bet 500 red/black/green/even/odd

        String[] code = e.getMessage().getContentRaw().split(" ");
        final Connect4Holder[] connect4 = new Connect4Holder[1];
        try {
            FileInputStream fileIn = new FileInputStream("connect4.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            connect4[0] = (Connect4Holder) in.readObject();
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

//!c4 play @

        if (code.length == 3 && code[1].equals("start")) {
            String username = code[2].substring(2, code[2].indexOf(">"));
            if(!e.getAuthor().getId().equals(username)){


                boolean gameInProg = false;
                String userid = e.getAuthor().getId();
                for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {

                    if (userid.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid())||userid.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {

                        gameInProg = true;


                    }
                }
                if (!gameInProg) {
                    connect4[0].getConnect4GameArrayList().add(new Connect4Game(e.getAuthor().getId(),username,e.getAuthor().getName()));
                    int indx = connect4[0].getConnect4GameArrayList().size()-1;
                    try {
                        FileOutputStream fileOut = new FileOutputStream("connect4.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(connect4[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
                    System.out.println("working");

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                           // .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .setDescription(connect4[0].getConnect4GameArrayList().get(indx).toString())
                            .build()).queue();



                } else {
                  //  e.getChannel().sendMessage("You already are in a game. See !connect4 for commands.").queue();

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .setDescription("You are already in a game. See **yconnect4**")
                            .build()).queue();

                }





            }else{

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .addField("Creating","```yc4 start @user```",true)
                        .addField("Playing","```yc4 <column number>```",true)
                        .addField("Board","```yc4 board```",true)
                        .addField("End game","```c4 quit```",true)
                        .build()).queue();

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .setDescription("You cannot play solo Connect Four.")
                        .build()).queue();


            }


        } else if(code.length == 2 && code[1].equals("quit")){
            boolean hasGame = false;
            for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid()) || user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {


                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .setDescription("Successfully ended " + connect4[0].getConnect4GameArrayList().get(x).getName()+ "'s Connect 4 game")
                            .build()).queue();
                    hasGame= true;

                    user.equals(connect4[0].getConnect4GameArrayList().remove(x));
                    try {
                        FileOutputStream fileOut = new FileOutputStream("connect4.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(connect4[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }

                    break;
                }

                } if(!hasGame){

                //e.getChannel().sendMessage("You are not in a Connect Four game.").queue();
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .setDescription("You are not in a Connect Four game.")
                        .build()).queue();
            }


            } else if(code.length == 2 && code[1].equals("board")){
            boolean hasGame = false;
            for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid()) || user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {



                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                      //      .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .setDescription(connect4[0].getConnect4GameArrayList().get(x).toString())
                            .build()).queue();

                    hasGame= true;

                    user.equals(connect4[0].getConnect4GameArrayList().remove(x));


                    break;
                }

            } if(!hasGame){

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .setDescription("You are not in a Connect Four game.")
                        .build()).queue();
            }

    }else if (code.length == 2) {
            try{

                int move = Integer.parseInt(code[1]); // try catch

                boolean vaildBet = true;
                boolean found = false;
                if (move > 0 && move < 8) {
                    // System.out.println(bank[0].getAllBalance().get(bankAccountIndex).getBalance());
                    for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {
                        String user = e.getAuthor().getId();
                        if (user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid()) || user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {
                            //     System.out.println(se + " " + rouletteGame[0].getRouletteGames().get(x).getServerid() + " " + x);

                            int whereGo = connect4[0].getConnect4GameArrayList().get(x).move(move, e.getAuthor().getId(), e.getAuthor().getName());
                            if (whereGo == 0) {
                                int winner = connect4[0].getConnect4GameArrayList().get(x).checkWin();
                                if (winner!=0) {

                                    if (winner == 1) {

                                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                                            //    .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                                .setDescription(connect4[0].getConnect4GameArrayList().get(x).getName() + "won 50 \uD83D\uDC8E by beating " + connect4[0].getConnect4GameArrayList().get(x).getName2() + "\n" + connect4[0].getConnect4GameArrayList().get(x).toString())
                                                .build()).queue();
                                        int indx = bank[0].getAccountIndex(connect4[0].getConnect4GameArrayList().get(x).getUserid());
                                        if(indx!=-1)
                                        bank[0].getAllBalance().get(indx).setBalance(bank[0].getAllBalance().get(indx).getBalance()+50);
                                        try {
                                            FileOutputStream fileOut = new FileOutputStream("bank.ser");
                                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                            out.writeObject(bank[0]);
                                            out.close();
                                            fileOut.close();
                                        } catch (IOException ww) {
                                            ww.printStackTrace();
                                        }
                                        connect4[0].getConnect4GameArrayList().remove(x);
                                    } else {
                                           e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                                       //         .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                                .setDescription(connect4[0].getConnect4GameArrayList().get(x).getName2() + "won 50 \uD83D\uDC8E by beating " + connect4[0].getConnect4GameArrayList().get(x).getName() + "\n" + connect4[0].getConnect4GameArrayList().get(x).toString())
                                                .build()).queue();
                                        int indx = bank[0].getAccountIndex(connect4[0].getConnect4GameArrayList().get(x).getUserid2());
                                        if(indx!=-1)
                                        bank[0].getAllBalance().get(indx).setBalance(bank[0].getAllBalance().get(indx).getBalance()+50);
                                        try {
                                            FileOutputStream fileOut = new FileOutputStream("bank.ser");
                                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                            out.writeObject(bank[0]);
                                            out.close();
                                            fileOut.close();
                                        } catch (IOException ww) {
                                            ww.printStackTrace();
                                        }
                                        connect4[0].getConnect4GameArrayList().remove(x);
                                    }

                                } else {

                                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                                        //    .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                            .setDescription(connect4[0].getConnect4GameArrayList().get(x).toString())
                                            .build()).queue();

                                }
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("connect4.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(connect4[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }


                            } else if (whereGo == 3) {


                                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                                     //   .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                        .setDescription("It's a tie!\n" + connect4[0].getConnect4GameArrayList().get(x).toString())
                                        .build()).queue();

                                connect4[0].getConnect4GameArrayList().remove(x);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("connect4.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(connect4[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            } else {
                                if (whereGo == 1) {


                                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                            .setDescription(e.getAuthor().getName() + ", it is not your turn.")
                                            .build()).queue();

                                } else {



                                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle(e.getAuthor().getName()+"'s Connect 4 game")
                                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                            .setDescription("Column Full.")
                                            .build()).queue();

                                }


                            }

                            found = true;

break;
                        }





                        //error no running games


                    }
                    if(!found){

                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                                .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                                .setDescription("You are not in a Connect Four game.")
                                .build()).queue();

                    }


                } else {
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                            .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                            .setDescription("Invalid move number, only type numbers on the board")
                            .build()).queue();



                }

            }catch (Exception w){
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .setDescription("Invalid move number. Format: **yc4 <column number>**")
                        .build()).queue();



            }


            //!tic quit
            //!tic board
        }else{
            if(!e.getAuthor().isBot())
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                        .setThumbnail("https://is2-ssl.mzstatic.com/image/thumb/Purple118/v4/aa/e9/96/aae9966e-a95e-65b2-a504-afbb0c9ac51d/source/512x512bb.jpg")
                        .addField("Creating","```yc4 start @user```",true)
                        .addField("Playing","```yc4 <column number>```",true)
                        .addField("Board","```yc4 board```",true)
                        .addField("End game","```c4 quit```",true)
                        .build()).queue();
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