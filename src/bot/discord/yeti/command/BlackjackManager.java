package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.Dic;
import bot.discord.yeti.game.blackjack.BlackjackGame;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;


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
        if(code.length == 1){
            e.getChannel().sendMessage("Format: !blackjack start <Optional: bet amount>").queue();
        }
        if ((code.length == 2 ||code.length == 3)  && code[1].equals("start")) {
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

                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s :spades: Blackjack Game :spades: \n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size() - 1).getTotal()+"\n!blackjack stand/stay or !blackjack hit").build()).queue();
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

                                e.getChannel().sendMessage("You are already in a game. Format: !blackjack [hit,stand]").queue();
                                //error already in game

                            }

                        }else{
                            e.getChannel().sendMessage(Dic.noMoney).queue();
                            //error no money

                        }

                    }else{
                        e.getChannel().sendMessage(Dic.postiveNum).queue();
                        //erorr only postive num

                    }




                }catch (Exception woah){
                    e.getChannel().sendMessage(Dic.postiveNum).queue();
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

                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size()-1).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s :spades: Blackjack Game :spades: \n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(blackjackGameHolder[0].getBlackjackGames().size() - 1).getTotal()+"\n!blackjack stand/stay or !blackjack hit").build()).queue();
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

                                e.getChannel().sendMessage("You are already in a game. Format: !blackjack [hit,stand]").queue();
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

                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getCard())), new MessageBuilder().append(e.getAuthor().getName() + "'s :spades:  Blackjack Game :spades: \n" + "Total " + blackjackGameHolder[0].getBlackjackGames().get(indx).getTotal()+"\n!blackjack stand/stay or !blackjack hit").build()).queue();
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

                e.getChannel().sendMessage("You are not in a game. See !blackjack for commands.").queue();
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
                            if(x==1)
                            e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(x))),new MessageBuilder().append("Dealer's remaining hand").build()).queue();
else{
                                e.getChannel().sendFile((new File(blackjackGameHolder[0].getBlackjackGames().get(indx).getDealerCard(x)))).queue();
                            }


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

                e.getChannel().sendMessage("You are not in a game. See !blackjack for commands.").queue();

            }

        }else if(code.length==2&&code[1].equals("quit")){
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
                e.getChannel().sendMessage("Successfully ended blackjack game!").queue();

            }else{

                e.getChannel().sendMessage("You are not in a game. See !blackjack for commands.").queue();

            }




        }
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);

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



