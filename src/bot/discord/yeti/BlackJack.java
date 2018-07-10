package bot.discord.yeti;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



public class BlackJack extends ListenerAdapter {

    private static ArrayList<Card> deck = new ArrayList();

    private static int total;
    private static int dealerTotal;

    private static int totalAceUp;
    private static int cardCount;
    private static boolean gameStart;

    private static boolean hasAce;
    private static boolean dealerHasAce;
    private static boolean dealerBust;
    private static Message publicmsg;


    //Main run method for Blackjack
    public static void run(Message msg) throws IOException {
        gameStart = true;
        publicmsg = msg; //Allows messages to be sent outside the 'run' method
        total = 0;
        cardCount = 0;
        hasAce = false;


        startGame();

        dealerStart();

        drawCard(2);

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

            publicmsg.getChannel().sendFile(new File(deck.get(0).getImage()), message).queue();

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
            publicmsg.getChannel().sendMessage("Dealer busted. You win.").queue();
            gameStart = false;
        }
        else if(!dealerBust && total <= 21 && !hasAce){
            publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal).queue();
            if(dealerTotal > total){
                publicmsg.getChannel().sendMessage("You lose.").queue();
            }
            else{
                publicmsg.getChannel().sendMessage("You win.").queue();
            }
            gameStart = false;
        }
        else{
            if(totalAceUp == 21 && cardCount <= 2){
                publicmsg.getChannel().sendMessage("Blackjack! You win.").queue();
                gameStart = false;
            }
            else if((totalAceUp == 21) && dealerTotal < 21){
                publicmsg.getChannel().sendMessage("Dealer had " + dealerTotal).queue();
                publicmsg.getChannel().sendMessage("Twenty-one, You win.").queue();
                gameStart = false;
            }
            else if(dealerBust && totalAceUp <= 21){
                publicmsg.getChannel().sendMessage("Dealer busted. You win.").queue();
                gameStart = false;
            }
            else if((total == dealerTotal) || (totalAceUp == dealerTotal) && dealerTotal == 21){
                publicmsg.getChannel().sendMessage("Stand-off. It's a tie. ").queue();
            }
            else {
                publicmsg.getChannel().sendMessage("Total: " + total + " or " + totalAceUp).queue();
                publicmsg.getChannel().sendMessage("Choose !hit or !stand").queue();
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
    public static void addCards(){
        deck.clear();

        deck.add(new Card(1, "diamonds", "img/AD.png", true));
        deck.add(new Card(2, "diamonds", "img/2D.png", false));
        deck.add(new Card(3, "diamonds", "img/3D.png", false));
        deck.add(new Card(4, "diamonds", "img/4D.png", false));
        deck.add(new Card(5, "diamonds", "img/5D.png", false));
        deck.add(new Card(6, "diamonds", "img/6D.png", false));
        deck.add(new Card(7, "diamonds", "img/7D.png", false));
        deck.add(new Card(8, "diamonds", "img/8D.png", false));
        deck.add(new Card(9, "diamonds", "img/9D.png", false));
        deck.add(new Card(10, "diamonds", "img/10D.png", false));
        deck.add(new Card(10, "diamonds", "img/JD.png", false));
        deck.add(new Card(10, "diamonds", "img/QD.png", false));
        deck.add(new Card(10, "diamonds", "img/KD.png", false));

        deck.add(new Card(1, "hearts", "img/AH.png", true));
        deck.add(new Card(2, "hearts", "img/2H.png", false));
        deck.add(new Card(3, "hearts", "img/3H.png", false));
        deck.add(new Card(4, "hearts", "img/4H.png", false));
        deck.add(new Card(5, "hearts", "img/5H.png", false));
        deck.add(new Card(6, "hearts", "img/6H.png", false));
        deck.add(new Card(7, "hearts", "img/7H.png", false));
        deck.add(new Card(8, "hearts", "img/8H.png", false));
        deck.add(new Card(9, "hearts", "img/9H.png", false));
        deck.add(new Card(10, "hearts", "img/10H.png", false));
        deck.add(new Card(10, "hearts", "img/JH.png", false));
        deck.add(new Card(10, "hearts", "img/QH.png", false));
        deck.add(new Card(10, "hearts", "img/KH.png", false));

        deck.add(new Card(1, "spades", "img/AS.png", true));
        deck.add(new Card(2, "spades", "img/2S.png", false));
        deck.add(new Card(3, "spades", "img/3S.png", false));
        deck.add(new Card(4, "spades", "img/4S.png", false));
        deck.add(new Card(5, "spades", "img/5S.png", false));
        deck.add(new Card(6, "spades", "img/6S.png", false));
        deck.add(new Card(7, "spades", "img/7S.png", false));
        deck.add(new Card(8, "spades", "img/8S.png", false));
        deck.add(new Card(9, "spades", "img/9S.png", false));
        deck.add(new Card(10, "spades", "img/10S.png", false));
        deck.add(new Card(10, "spades", "img/JS.png", false));
        deck.add(new Card(10, "spades", "img/QS.png", false));
        deck.add(new Card(10, "spades", "img/KS.png", false));

        deck.add(new Card(1, "clubs", "img/AC.png", true));
        deck.add(new Card(2, "clubs", "img/2C.png", false));
        deck.add(new Card(3, "clubs", "img/3C.png", false));
        deck.add(new Card(4, "clubs", "img/4C.png", false));
        deck.add(new Card(5, "clubs", "img/5C.png", false));
        deck.add(new Card(6, "clubs", "img/6C.png", false));
        deck.add(new Card(7, "clubs", "img/7C.png", false));
        deck.add(new Card(8, "clubs", "img/8C.png", false));
        deck.add(new Card(9, "clubs", "img/9C.png", false));
        deck.add(new Card(10, "clubs", "img/10C.png", false));
        deck.add(new Card(10, "clubs", "img/11C.png", false));
        deck.add(new Card(10, "clubs", "img/12C.png", false));
        deck.add(new Card(10, "clubs", "img/13C.png", false));

    }
    //METHOD for Shuffling the Deck
    public static void shuffleDeck(){
        for(int i = 0; i <= 7; i++){
            Collections.shuffle(deck);
        }
    }

}