package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;


import bot.discord.yeti.game.tictactoe.TicTacToeGame;
import bot.discord.yeti.game.tictactoe.TicTacToeHolder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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

                    e.getChannel().sendMessage(e.getAuthor().getName()+"'s Tic-Tac-Toe game\n" +ticTacToe[0].getTicTacToeGameArrayList().get(indx).toString()).queue(m -> {

                    });




                } else {
                    e.getChannel().sendMessage("You already are in a game, type y!tic play %move number% or y!tic quit").queue();

                }





            }else{

                e.getChannel().sendMessage("You cannot play Tic-Tac-Toe with yourself.").queue();

            }



            //!tic quit
            //!tic board
        } else if(code.length == 2 && code[1].equals("quit")){
            boolean hasGame = false;
            for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid()) || user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {

                    e.getChannel().sendMessage("Successfully ended " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName()+ "'s Tic-Tac-Toe game").queue();
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

                e.getChannel().sendMessage("You are not in a game").queue();
            }


            } else if(code.length == 2 && code[1].equals("board")){
            boolean hasGame = false;
            for (int x = 0; x < ticTacToe[0].getTicTacToeGameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid()) || user.equals(ticTacToe[0].getTicTacToeGameArrayList().get(x).getUserid2())) {

                    e.getChannel().sendMessage(e.getAuthor().getName()+"'s Tic-Tac-Toe game\n" +ticTacToe[0].getTicTacToeGameArrayList().get(x).toString()).queue();
                    hasGame= true;

                    user.equals(ticTacToe[0].getTicTacToeGameArrayList().remove(x));


                    break;
                }

            } if(!hasGame){

                e.getChannel().sendMessage("You are not in a game").queue();
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

                        e.getChannel().sendMessage(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName2() + " won 50 \uD83D\uDC8E by beating " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + "\n" + ticTacToe[0].getTicTacToeGameArrayList().get(x).toString()).queue();
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
                        e.getChannel().sendMessage(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + " won 50 \uD83D\uDC8E by beating " + ticTacToe[0].getTicTacToeGameArrayList().get(x).getName2() + "\n" + ticTacToe[0].getTicTacToeGameArrayList().get(x).toString()).queue();
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

                        e.getChannel().sendMessage(ticTacToe[0].getTicTacToeGameArrayList().get(x).getName() + "'s Tic-Tac-Toe game\n" + ticTacToe[0].getTicTacToeGameArrayList().get(x).toString()).queue();


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
                    e.getChannel().sendMessage("It's a tie!\n" + ticTacToe[0].getTicTacToeGameArrayList().get(x).toString()).queue();
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
                        e.getChannel().sendMessage(e.getAuthor().getName() + ", it is not your turn.").queue();
                    }else {

                        e.getChannel().sendMessage(move + " is already taken").queue();
                    }


                }


break;

            }



            //error no running games


        }
        if(!found){

            e.getChannel().sendMessage("You are not in a game").queue();

        }


    } else {

        e.getChannel().sendMessage("Invalid move number, only type numbers on the board").queue();


    }
}catch (Exception ea){

    e.getChannel().sendMessage("Invalid move number, only type numbers on the board").queue();

}

        } else{
            if(!e.getAuthor().isBot())
                e.getChannel().sendMessage("y!tic start @user - Start a match\ny!tic <move number> to move\ny!tic board - Print out your current match board\ny!tic quit - End any unfinished games").queue();

        }
    }
}