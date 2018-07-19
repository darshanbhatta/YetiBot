package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.game.blackjack.BlackjackGame;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;


public class BlackjackManager{

    //!blackjack
    public static void run(MessageReceivedEvent e) {
        String[] code = e.getMessage().getContentRaw().split(" ");
        BlackjackGameHolder[] blackjackGameHolder = new BlackjackGameHolder[1];
        Bank[] bank = new Bank[1];
        try {
            FileInputStream fileIn = new FileInputStream("blackjack.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            blackjackGameHolder[0] = (BlackjackGameHolder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

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

//!blackjack init 500
        if ((code.length == 2 ||code.length == 3)  && code[1].equals("init")) {
            String bet = "";
            int betAmount =0;
            if(code.length==3){
             bet = code[2];

                try{

                    betAmount = Integer.parseInt(bet);
                    if(betAmount>0){

                        int bankindx = bank[0].getAccountIndex(e.getAuthor().getId());
                        int bankBalance = bank[0].getAllBalance().get(bankindx).getBalance();
                        if(bankBalance>=betAmount){
                            bank[0].getAllBalance().get(bankindx).setBalance(bankBalance - betAmount);
                            completeTransaction(bank[0]);
                            boolean hasGame = false;
                            int indx = 0;
                            for (int x = 0; x < blackjackGameHolder[0].getBlackjackGames().size(); x++) {

                                if (e.getAuthor().getId().equals(blackjackGameHolder[0].getBlackjackGames().get(x).getId())) {
                                    hasGame = true;
                                    indx = x;
                                    break;
                                }


                            }

                            if (!hasGame) {
                                blackjackGameHolder[0].getBlackjackGames().add(new BlackjackGame(betAmount, e.getAuthor().getId(), e.getAuthor().getName()));

                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s Blackjack Game\n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size() - 1).getTotal()).build()).queue();
                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard()))).queue();
                                if(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).hasBlackJack()){
                                    e.getChannel().sendMessage("Blackjack! You win " + ((int) (2.5 * betAmount)) + " :gem:").queue();
                                    bank[0].getAllBalance().get(indx).setBalance(bank[0].getAllBalance().get(indx).getBalance() + (int) (2.5 * betAmount));
                                    completeTransaction(bank[0]);
                                    blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                    try {
                                        FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                        out.writeObject(blackjackGameHolder[0]);
                                        out.close();
                                        fileOut.close();
                                    } catch (IOException ww) {
                                        ww.printStackTrace();
                                    }
                                }else{
                                    e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getDealerCard(0))), new MessageBuilder().append("\nDealer's hand").build()).queue();
                                    e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getDealerCard(-1)))).queue();


                                }
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            } else {

                                e.getChannel().sendMessage("Error you are already in a game").queue();
                                //error already in game

                            }

                        }else{

                            //error no money

                        }

                    }else{

                        //erorr only postive num

                    }




                }catch (Exception woah){

                    //error only bet ints

                }
            }else{

                            boolean hasGame = false;
                            int indx = 0;
                            for (int x = 0; x < blackjackGameHolder[0].getBlackjackGames().size(); x++) {

                                if (e.getAuthor().getId().equals(blackjackGameHolder[0].getBlackjackGames().get(x).getId())) {
                                    hasGame = true;
                                    indx = x;
                                    break;
                                }


                            }

                            if (!hasGame) {
                                blackjackGameHolder[0].getBlackjackGames().add(new BlackjackGame(betAmount, e.getAuthor().getId(), e.getAuthor().getName()));

                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s Blackjack Game\n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size() - 1).getTotal()).build()).queue();
                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard()))).queue();
                                if(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).hasBlackJack()){
                                    e.getChannel().sendMessage("Blackjack! You win!").queue();
                                    blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                    try {
                                        FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                        ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                        out.writeObject(blackjackGameHolder[0]);
                                        out.close();
                                        fileOut.close();
                                    } catch (IOException ww) {
                                        ww.printStackTrace();
                                    }
                                }else{
                                    e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getDealerCard(0))), new MessageBuilder().append("\nDealer's hand").build()).queue();
                                    e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getDealerCard(-1)))).queue();


                                }
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            } else {

                                e.getChannel().sendMessage("Error you are already in a game").queue();
                                //error already in game

                            }









            }





        }else if(code.length==2&&code[1].equals("hit")){

            boolean hasGame = false;
            int indx = 0;
            for (int x = 0; x < blackjackGameHolder[0].getBlackjackGames().size(); x++) {

                if (e.getAuthor().getId().equals(blackjackGameHolder[0].getBlackjackGames().get(x).getId())) {
                    hasGame = true;
                    indx = x;
                    break;
                }


            }

            if (hasGame) {
                blackjackGameHolder[0].getBlackjackGames().get(indx).hit();

                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s Blackjack Game\n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).build()).queue();
                try {
                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(blackjackGameHolder[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }
                if(blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()>21){
    e.getChannel().sendMessage("You busted!").queue();
    blackjackGameHolder[0].getBlackjackGames().remove(indx);
    try {
        FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(blackjackGameHolder[0]);
        out.close();
        fileOut.close();
    } catch (IOException ww) {
        ww.printStackTrace();
    }
}

            }else{

                e.getChannel().sendMessage("Error you are not in a game, type !blackjack %bet% to start").queue();
            }





        }else if(code.length==2&&(code[1].equals("stay")||code[1].equals("stand"))){

            boolean hasGame = false;
int indx = 0;
            for (int x = 0; x < blackjackGameHolder[0].getBlackjackGames().size(); x++) {

                if (e.getAuthor().getId().equals(blackjackGameHolder[0].getBlackjackGames().get(x).getId())) {
                    hasGame = true;
                    indx = x;
                    break;
                }


            }

            if (hasGame) {

                if(blackjackGameHolder[0].getBlackjackGames().get(indx).getBet()==0){
                    boolean dealerBust = blackjackGameHolder[0].getBlackjackGames().get(indx).stand();
                    if(blackjackGameHolder[0].getBlackjackGames().get(indx).hasDealerBlackJack()){

                        e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(1))),new MessageBuilder().append("Dealer blackjack!").build()).queue();

                    }else{

                        for(int x=1;x<blackjackGameHolder[0].getBlackjackGames().get(indx).getDealer().size();x++){
                            e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(x)))).queue();



                        }
                        if(dealerBust){

                            e.getChannel().sendMessage("Dealer busted! You win!").queue();
                            blackjackGameHolder[0].getBlackjackGames().remove(indx);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(blackjackGameHolder[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }


                        }else{
                            if(blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()>blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal()){
                                e.getChannel().sendMessage("You win!\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }

                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }else if(blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()==blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal()){
                                e.getChannel().sendMessage("It's a tie\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }else{

                                e.getChannel().sendMessage("You lose!\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }



                        }




                    }


                }else{

                    int bankindx = bank[0].getAccountIndex(e.getAuthor().getId());
                    boolean dealerBust = blackjackGameHolder[0].getBlackjackGames().get(indx).stand();
                    if(blackjackGameHolder[0].getBlackjackGames().get(indx).hasDealerBlackJack()){

                        e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(1))),new MessageBuilder().append("Dealer blackjack!").build()).queue();

                    }else{

                        for(int x=1;x<blackjackGameHolder[0].getBlackjackGames().get(indx).getDealer().size();x++){
                            e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(x)))).queue();



                        }
                        if(dealerBust){

                            e.getChannel().sendMessage("Dealer busted! You win " + ((int) (2 * blackjackGameHolder[0].getBlackjackGames().get(indx).getBet())) + " :gem:").queue();
                            bank[0].getAllBalance().get(indx).setBalance(bank[0].getAllBalance().get(bankindx).getBalance() + (int) (2 * blackjackGameHolder[0].getBlackjackGames().get(indx).getBet()));
                            completeTransaction(bank[0]);
                            blackjackGameHolder[0].getBlackjackGames().remove(indx);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(blackjackGameHolder[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }


                        }else{
                            if(blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()>blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal()){
                                e.getChannel().sendMessage("You win!\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                bank[0].getAllBalance().get(indx).setBalance(bank[0].getAllBalance().get(bankindx).getBalance() + (int) (2 * blackjackGameHolder[0].getBlackjackGames().get(indx).getBet()));
                                completeTransaction(bank[0]);
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }

                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }else if(blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()==blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal()){
                                e.getChannel().sendMessage("It's a tie\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }else{

                                e.getChannel().sendMessage("You lose!\nDealer had "+blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerTotalTotal() + " and " + blackjackGameHolder[0].getBlackjackGames().get(indx).getName() + " had "+ blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()).queue();
                                blackjackGameHolder[0].getBlackjackGames().remove(indx);
                                try {
                                    FileOutputStream fileOut = new FileOutputStream("blackjack.ser");
                                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                    out.writeObject(blackjackGameHolder[0]);
                                    out.close();
                                    fileOut.close();
                                } catch (IOException ww) {
                                    ww.printStackTrace();
                                }
                            }



                        }




                    }

                }


            }else{

                e.getChannel().sendMessage("Error you are not in a game, type !blackjack %bet% to start").queue();

            }

        }else{



        }


    }
    public static void completeTransaction(Bank bank){
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



