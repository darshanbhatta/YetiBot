package bot.discord.yeti;

import bot.discord.yeti.currency.Bank;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;



public class BlackJack extends ListenerAdapter {
/*
    private static ArrayList<Card> deck = new ArrayList();


    private static int total;
    private static int dealerTotal;

    private static int totalAceUp;
    private static int cardCount;
    private static boolean gameStart;
    private static boolean placeBet;
    private static boolean hasAce;
    private static boolean dealerHasAce;
    private static boolean dealerBust;
    private static Message publicmsg;
    private static String price;
    private static Bank bank;
    private static int i;
    private static String[] arg;
    //Main run method for Blackjack

    public static void run(Message msg) throws IOException {
        gameStart = true;
        publicmsg = msg; //Allows messages to be sent outside the 'run' method
        total = 0;
        cardCount = 0;
        hasAce = false;
        placeBet = false;

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

        arg = msg.getContentRaw().split(" ");

        i = bank.getAccountIndex(msg.getAuthor().getId());

        if(arg.length == 1){
            publicmsg.getChannel().sendMessage("Blackjack:diamonds:").queue();
        }
        else{
            price = arg[1];
        }

        if(arg.length == 2) {
            if (i != -1) {

                int bal = bank.getAllBalance().get(i).getBalance();

                if (bal >= Integer.parseInt(price)) {
                    bank.getAllBalance().get(i).setBalance(bal - Integer.parseInt(price));
                    completeTransaction();
                }

                completeTransaction();

                msg.getChannel().sendMessage(":spades:Blackjack:diamonds:").queue(m -> {});
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                placeBet = true;

            } else {
                msg.getChannel().sendMessage("Error recipient does not have an account, tell them to type !bank init to make one").queue(m -> {
                });

            }
        }





        try {
            FileInputStream fileIn = new FileInputStream("bank.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            bank = (Bank) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException p) {
            p.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }



        startGame();

        dealerStart();

        drawCard(2);

    }
    public static void completeTransaction(){
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

    //METHOD for Initializing the Game
    public static void startGame(){
        BlackJack.addCards();
        BlackJack.shuffleDeck();
    }

    public static void checkTotal(){
        if(hasAce && total <= 11){
            totalAceUp = total + 10;
            publicmsg.getChannel().sendMessage("Total: " + total + " or " + totalAceUp).queue();
        }
        else{
            publicmsg.getChannel().sendMessage("Total: " + total).queue();
        }
        if(total < 21) {
            publicmsg.getChannel().sendMessage("Choose !hit or !stand").queue();
        }
        else{
            isVictory();
        }

    }

    //METHOD for Drawing a card from the deck
    public static void drawCard(int parameter) {
        for(int i = 0; i < parameter; i++) {
            Message message = new MessageBuilder().append(" ").build();

            publicmsg.getChannel().sendFile(new File(deck.get(0).getImage()),new MessageBuilder().append(" ").build()).complete();

            // publicmsg.getChannel().sendFile(new File(deck.get(0).getImage()), message).queue();

            total = total + deck.get(0).getNumber();
            if(deck.get(0).isAce()){
                hasAce = true;
            }

            deck.remove(0);
            cardCount++;
        }
        checkTotal();
    }



    //METHOD for Hit or Stand choice
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getContentRaw().startsWith("!")) {
            String[] arg = e.getMessage().getContentRaw().replaceFirst("!", "").split(" ");
            if (gameStart) {
                switch (arg[0]) {
                    case "hit":
                        drawCard(1);
                        break;

                    case "stand":
                        isVictory();
                        gameStart = false;
                        break;
                }
            } else {
                switch (arg[0]) {
                    case "hit":
                        publicmsg.getChannel().sendMessage("No game is in progress!").queue();
                        break;

                    case "stand":
                        publicmsg.getChannel().sendMessage("No game is in progress!").queue();
                        break;

                }
            }
        }
    }


    //METHOD for checking Victory status
    public static void isVictory(){
        if(total > 21){
            publicmsg.getChannel().sendMessage("Busted. You lose.").queue();
            gameStart = false;
        }
        else if(dealerBust && total <= 21){

            if(placeBet) {
                publicmsg.getChannel().sendMessage("Dealer busted. You win " + (2 * (Integer.valueOf(price))) + " :gem:").queue();
                bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                completeTransaction();
            }
            else{
                publicmsg.getChannel().sendMessage("Dealer busted. You win.").queue();
            }
            gameStart = false;
        }
        else if(!dealerBust && total <= 21 && !hasAce){
            // publicmsg.getChannel().sendMessage(").queue();
            if(dealerTotal > total){
                publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal + "\nYou lose.").queue();
            }
            else if(total > dealerTotal){

                if(placeBet) {
                    publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal + "\nYou win " + (2 * (Integer.valueOf(price))) + " :gem:").queue();
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + 2 * (Integer.valueOf(price)));
                    completeTransaction();
                }
                else{
                    publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal + "\nYou win.").queue();
                }
            }
            else{

                if(placeBet) {
                    publicmsg.getChannel().sendMessage("It's a tie. Returned " +  (Integer.valueOf(price)) + " :gem:").queue();
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + (Integer.valueOf(price)));
                    completeTransaction();
                }
                else{
                    publicmsg.getChannel().sendMessage("It's a tie.").queue();
                }
            }
            gameStart = false;
        }
        else{
            if(totalAceUp == 21 && cardCount <= 2){

                if(placeBet) {
                    publicmsg.getChannel().sendMessage("Blackjack! You win " + (int) (2.5 * (Integer.valueOf(price))) + " :gem:").queue();
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + (int) (2.5 * (Integer.valueOf(price))));
                    completeTransaction();
                }
                else{
                    publicmsg.getChannel().sendMessage("Blackjack! You win.").queue();
                }
                gameStart = false;
            }
            else if((totalAceUp == 21) && dealerTotal < 21){

                if(placeBet) {
                    publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal +"\nTwenty-one, You win " + (2 * (Integer.valueOf(price))) + " :gem:").queue();
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + (2 * (Integer.valueOf(price))));
                    completeTransaction();
                }
                else{
                    publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal +"\nTwenty-one, You win").queue();
                }
                gameStart = false;
            }
            else if(dealerBust && totalAceUp <= 21){


                if(placeBet) {
                    publicmsg.getChannel().sendMessage("Dealer busted. You win " + (int) (2 * (Integer.valueOf(price))) + " :gem:").queue();
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + (2 * (Integer.valueOf(price))));
                    completeTransaction();
                }
                else{
                    publicmsg.getChannel().sendMessage("Dealer busted. You win.").queue();
                }
                gameStart = false;
            }
            else if(!dealerBust && totalAceUp <= 21){
                if(dealerTotal > total && dealerTotal > totalAceUp){
                    publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal + "\nYou lose.").queue();
                    gameStart = false;
                }
            }
            else if((total == dealerTotal) || (totalAceUp == dealerTotal) && dealerTotal == 21){
                publicmsg.getChannel().sendMessage("Twenty-one Stand-off. It's a tie. Returned " +  (Integer.valueOf(price)) + " :gem:").queue();
                if(placeBet) {
                    bank.getAllBalance().get(i).setBalance(bank.getAllBalance().get(i).getBalance() + (Integer.valueOf(price)));
                    completeTransaction();

                }
                gameStart = false;
            }
            else {
                // publicmsg.getChannel().sendMessage().queue();
                publicmsg.getChannel().sendMessage("Total: " + total + " or " + totalAceUp + "\nChoose !hit or !stand").queue();

            }

        }
    }

    public static void dealerStart(){
        dealerTotal = 0;
        dealerHasAce = false;
        dealerBust = false;

        dealerDraw(2);

        dealerChoose();
    }

    public static void dealerDraw(int parameter){
        for(int i = 0; i < parameter; i++)
            dealerTotal = dealerTotal + deck.get(0).getNumber();
        if(deck.get(0).isAce() && dealerTotal <= 11){
            dealerTotal = dealerTotal + 10;
            dealerHasAce = true;
        }
        deck.remove(0);
    }
    public static void dealerChoose(){
        while(dealerTotal <= 16){
            dealerDraw(1);
        }
        if(dealerTotal > 21) {
            if (dealerHasAce) {
                dealerTotal = dealerTotal - 10;
            }
            else {
                dealerBust = true;
            }
        }

    }
    //METHOD for Creating the Deck
*/

}