package bot.discord.yeti.command;

import bot.discord.yeti.game.connect4.Connect4Game;
import bot.discord.yeti.game.connect4.Connect4Holder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

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
                    e.getChannel().sendMessage(e.getAuthor().getName()+"'s Connect 4 game\n" +connect4[0].getConnect4GameArrayList().get(indx).toString()).queue(m -> {

                    });




                } else {
                    e.getChannel().sendMessage("Error you already are in a game, type !c4 play %move number% or !c4 quit").queue();

                }





            }else{

                e.getChannel().sendMessage("Error cannot start game with yourself").queue();

            }


        } else if (code.length == 2) {
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
                                        e.getChannel().sendMessage(connect4[0].getConnect4GameArrayList().get(x).getName() + " beat " + connect4[0].getConnect4GameArrayList().get(x).getName2() + "\n" + connect4[0].getConnect4GameArrayList().get(x).toString()).queue();
                                        connect4[0].getConnect4GameArrayList().remove(x);
                                    } else {
                                        e.getChannel().sendMessage(connect4[0].getConnect4GameArrayList().get(x).getName2() + " beat " + connect4[0].getConnect4GameArrayList().get(x).getName() + "\n" + connect4[0].getConnect4GameArrayList().get(x).toString()).queue();
                                        connect4[0].getConnect4GameArrayList().remove(x);
                                    }

                                } else {

                                    e.getChannel().sendMessage(connect4[0].getConnect4GameArrayList().get(x).getName() + "'s Connect 4 game\n" + connect4[0].getConnect4GameArrayList().get(x).toString()).queue();


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
                                e.getChannel().sendMessage("It's a tie!\n" + connect4[0].getConnect4GameArrayList().get(x).toString()).queue();
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
                                    e.getChannel().sendMessage(e.getAuthor().getName() + ", its not your turn.").queue();
                                } else {

                                    e.getChannel().sendMessage("Error column full").queue();
                                }


                            }

                            found = true;


                        }


                        //error no running games


                    }


                } else {

                    e.getChannel().sendMessage("Error invalid move number, only type numbers on the board").queue();


                }

            }catch (Exception w){

                e.getChannel().sendMessage("Error invalid move number, only type numbers on the board").queue();


            }


            //!tic quit
            //!tic board
        } else if(code.length == 2 && code[1].equals("quit")){
            boolean hasGame = false;
            for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid()) || user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {

                    e.getChannel().sendMessage("Successfully ended " + connect4[0].getConnect4GameArrayList().get(x).getName()+ "'s Connect 4 game").queue();
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

                e.getChannel().sendMessage("Error you are not in a game").queue();
            }


            } else if(code.length == 2 && code[1].equals("board")){
            boolean hasGame = false;
            for (int x = 0; x < connect4[0].getConnect4GameArrayList().size(); x++) {
                String user = e.getAuthor().getId();
                if (user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid()) || user.equals(connect4[0].getConnect4GameArrayList().get(x).getUserid2())) {

                    e.getChannel().sendMessage(e.getAuthor().getName()+"'s Connect 4 Game\n" +connect4[0].getConnect4GameArrayList().get(x).toString()).queue();
                    hasGame= true;

                    user.equals(connect4[0].getConnect4GameArrayList().remove(x));


                    break;
                }

            } if(!hasGame){

                e.getChannel().sendMessage("Error you are not in a game").queue();
            }

    }
        else{

            e.getChannel().sendMessage("Error invalid command.\n\nCommand List\n\t!c4 start @user to start a match\n\t!c4 play %move number% to move\n\t!c4 board to print our your current match board\n\t!c4 quit to end any unfinished games").queue();

        }
    }
}