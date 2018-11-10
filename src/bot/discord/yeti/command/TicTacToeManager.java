package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;


import bot.discord.yeti.game.tictactoe.TicTacToeGame;
import bot.discord.yeti.game.tictactoe.TicTacToeHolder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

public class TicTacToeManager {


    public static void run(MessageReceivedEvent e) {
        //!roulette init !roulette bet 500 red/black/green/even/odd

        String[] code = e.getMessage().getContentRaw().split(" ");
        final TicTacToeHolder[] ticTacToe = new TicTacToeHolder[1];
        try {
            FileInputStream fileIn = new FileInputStream("tictactoe.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ticTacToe[0] = (TicTacToeHolder) in.readObject();
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
//!tic play @
        if (code.length == 3 && code[1].equals("start")) {
            String username = code[2].substring(2, code[2].indexOf(">"));
            if(!e.getAuthor().getId().equals(username)){


                boolean gameInProg = false;
                String userid = e.getAuthor().getId();
                for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {

                    if (userid.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid())||userid.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {

                        gameInProg = true;


                    }
                }
                if (!gameInProg) {
                    ticTacToe[0].getTicTacToeGameArrayList().add(new TicTacToeGame(e.getAuthor().getId(),username,e.getAuthor().getName()));
                    int indx = ticTacToe[0].getTicTacToeGameArrayList().size()-1;
                    try {
                        FileOutputStream fileOut = new FileOutputStream("tictactoe.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(ticTacToe[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }



                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(e.getAuthor().getName() + "'s Tic-Tac-Toe game","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(indx).toString())
                            .build()).queue();



                } else {

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                            .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .setDescription("You already are in a game, type `ytic play <move number>` or ytic quit")
                            .build()).queue();
                }





            }else{


                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                        .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                        .setDescription("You cannot play Tic-Tac-Toe with yourself.")
                        .build()).queue();
            }



            //!tic quit
            //!tic board
        } else if(code.length == 2 && code[1].equals("quit")){
            boolean hasGame = false;
            for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid()) || user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {


                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Successfully ended " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName()+ "'s Tic-Tac-Toe game","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                            .build()).queue();
                    hasGame= true;

                    user.equals(ticTacToe[0].getTicTacToeGameArrayList().remove(x));
                    try {
                        FileOutputStream fileOut = new FileOutputStream("tictactoe.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(ticTacToe[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }

                    break;
                }

                } if(!hasGame){

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                        .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                        .setDescription("You are not in a game")
                        .build()).queue();
            }


            } else if(code.length == 2 && code[1].equals("board")){
            boolean hasGame = false;
            for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid()) || user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {



                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + "'s Tic-Tac-Toe game","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                            .build()).queue();
                    hasGame= true;

                    user.equals(ticTacToe[0].getTicTacToeGameArrayList().remove(x));


                    break;
                }

            } if(!hasGame){


                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                        .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                        .setDescription("You are not in a game")
                        .build()).queue();
            }

    }else if (code.length == 2) {

try{
    int move = Integer.parseInt(code[1]); // try catch
    boolean vaildBet = true;
    boolean found = false;
    if (move > 0 && move < 10) {
        // System.out.println(bank[0].getAllBalance().get(bankAccountIndex).getBalance());
        for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {
            String user = e.getAuthor().getId();
            if (user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid()) || user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {
                //     System.out.println(se + " " + rouletteGame[0].getRouletteGames().get(x).getServerid() + " " + x);
                found = true;
                int whereGo = ticTacToe[0].getTicTacToeGameArrayList().get(x).move(move, e.getAuthor().getId(), e.getAuthor().getName());
                System.out.println(whereGo);
                if (whereGo == 0) {
                    if (ticTacToe[0].getTicTacToeGameArrayList().get(x).isWinnerX()) {



                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName2() + " won 50 \uD83D\uDC8E by beating " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName(),"http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                                .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                                .build()).queue();

                        int indx = bank[0].getAccountIndex(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2());
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
                        ticTacToe[0].getTicTacToeGameArrayList().remove(x);

                    } else if (ticTacToe[0].getTicTacToeGameArrayList().get(x).isWinnerY()) {
                       e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + " won 50 \uD83D\uDC8E by beating " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName(),"http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                                .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                                .build()).queue();
                        int indx = bank[0].getAccountIndex(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid());
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
                        ticTacToe[0].getTicTacToeGameArrayList().remove(x);
                    } else {


                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + "'s Tic-Tac-Toe game","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                                .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                                .build()).queue();

                    }
                    try {
                        FileOutputStream fileOut = new FileOutputStream("tictactoe.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(ticTacToe[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }


                } else if (whereGo == 3) {

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("It's a tie!","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png","http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                            .setDescription(ticTacToe[0].getTicTacToeGameArrayList().get(x).toString())
                            .build()).queue();
                    ticTacToe[0].getTicTacToeGameArrayList().remove(x);
                    try {
                        FileOutputStream fileOut = new FileOutputStream("tictactoe.ser");
                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                        out.writeObject(ticTacToe[0]);
                        out.close();
                        fileOut.close();
                    } catch (IOException ww) {
                        ww.printStackTrace();
                    }
                } else {
                    if (whereGo == 1) {

                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                                .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                                .setDescription(e.getAuthor().getName() + ", it is not your turn.")
                                .build()).queue();
                    }else {


                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                                .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                                .setDescription(move + " is already taken")
                                .build()).queue();
                    }


                }


break;

            }



            //error no running games


        }
        if(!found){


            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                    .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                    .setDescription("You are not in a game")
                    .build()).queue();
        }


    } else {

        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
                .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                .setDescription("Invalid move number, only type numbers on the board")
                .build()).queue();


    }
}catch (Exception ea){
    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Connect 4")
            .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
            .setDescription("Invalid move number, only type numbers on the board")
            .build()).queue();
}

        } else{
            if(!e.getAuthor().isBot())
                 e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Tic-Tac-Toe")
                    .setThumbnail("http://aux2.iconspalace.com/uploads/tic-tac-toe-game-icon-256.png")
                    .addField("Creating","```ytic start @user```",true)
                    .addField("Playing","```ytic <move number>```",true)
                    .addField("Board","```ytic board```",true)
                    .addField("End game","```ytic quit```",true)
                    .build()).queue();
        }
    }
}