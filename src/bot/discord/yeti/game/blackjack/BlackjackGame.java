package bot.discord.yeti.game.blackjack;

import bot.discord.yeti.BlackJack;
import bot.discord.yeti.Card;
import bot.discord.yeti.currency.Bank;
import net.dv8tion.jda.core.entities.Message;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class BlackjackGame implements Serializable {
    private int total;
    private ArrayList<Card> deck;
    private ArrayList<Card> dealerhand ;
    private ArrayList<Card> hand ;
    private int bet;
    String id;
    private String name;
    private int dealerTotal;
    private int totalAceUp;
    private int cardCount;
    private int cardRequest;
    private boolean gameStart;
    private boolean placeBet;
    private int numAces;
    private int dealerAces;
    private boolean dealerHasAce;
    private boolean dealerBust;
    private String price;
    private int i;
    private String[] arg;


    public int getTotal() {
        return total;
    }
    public int getDealerTotalTotal() {
        return dealerTotal;
    }

    public BlackjackGame(int bet, String id, String name) {
        deck = new ArrayList<>();
        dealerhand = new ArrayList<>();
        hand = new ArrayList<>();
        addCards();
        this.name = name;
        this.bet = bet;
        this.id = id;
        gameStart = true;
        numAces = 0;
        placeBet = false;
        cardRequest = 0;
        shuffleDeck();
        hand.add(deck.get(0));
        hand.add(deck.get(1));
        dealerhand.add(deck.get(2));
        dealerTotal+=deck.get(2).getNumber();
        if(deck.get(2).isAce()){
            dealerAces++;
        }
        cardCount = 3;
        System.out.println(deck.get(0).getImage() + " " + deck.get(1).getImage());
        total = deck.get(0).getNumber()+deck.get(1).getNumber();
        if(deck.get(0).isAce()){
            numAces++;
        }
        if(deck.get(1).isAce()){
            numAces++;
        }
    }

    public String getName() {
        return name;
    }

    public String getId(){

        return id;
}

    public int getBet() {
        return bet;
    }

    public String getCard(){

       cardRequest = cardRequest + 1;
        System.out.println(cardRequest-1);
        return hand.get(cardRequest-1).getImage();

    }

    public String getDealerCard(int num){
if(num==-1){
    return "img/gray_back.png";
}
        return dealerhand.get(num).getImage();

    }

    public boolean hasBlackJack() {
        return ((hand.get(0).getNumber() == 11 && hand.get(1).getNumber() == 10)
                || (hand.get(0).getNumber() == 10 && hand.get(1).getNumber() == 11));
    }

    public boolean hasDealerBlackJack() {
        return ((dealerhand.get(0).getNumber() == 11 && dealerhand.get(1).getNumber() == 10)
                || (dealerhand.get(0).getNumber() == 10 && dealerhand.get(1).getNumber() == 11));
    }

    public void hit(){
        hand.add(deck.get(cardCount));
        total+=deck.get(cardCount).getNumber();
        System.out.println(numAces + " " + deck.get(cardCount).getNumber());
        if(deck.get(cardCount).isAce()){

            numAces++;

        }
        if (total > 21 && numAces > 0) {
            total -= 10;
            numAces--;
        }
        cardCount++;


    }

    public boolean isBusted(){

        if (total > 21) {
            return true;
        }else{

            return false;
        }

    }

    public boolean stand(){
        while(dealerTotal<17){
            dealerhand.add(deck.get(cardCount));
            dealerTotal+=deck.get(cardCount).getNumber();
            if(deck.get(cardCount).isAce()){

                dealerAces++;

            }
            if (total > 21 && dealerAces > 0) {
                dealerTotal -= 10;
                dealerAces--;
            }
            cardCount++;
            if(dealerTotal>21){
                return true;
            }
        }
        return false;


    }
    public ArrayList getDealer(){
       return dealerhand;


    }










    public  void addCards(){
        deck.clear();
        deck.add(new Card(11, "diamonds", "img/AD.png", true));
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

        deck.add(new Card(11, "hearts", "img/AH.png", true));
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

        deck.add(new Card(11, "spades", "img/AS.png", true));
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

        deck.add(new Card(11, "clubs", "img/AC.png", true));
        deck.add(new Card(2, "clubs", "img/2C.png", false));
        deck.add(new Card(3, "clubs", "img/3C.png", false));
        deck.add(new Card(4, "clubs", "img/4C.png", false));
        deck.add(new Card(5, "clubs", "img/5C.png", false));
        deck.add(new Card(6, "clubs", "img/6C.png", false));
        deck.add(new Card(7, "clubs", "img/7C.png", false));
        deck.add(new Card(8, "clubs", "img/8C.png", false));
        deck.add(new Card(9, "clubs", "img/9C.png", false));
        deck.add(new Card(10, "clubs", "img/10C.png", false));
        deck.add(new Card(10, "clubs", "img/JC.png", false));
        deck.add(new Card(10, "clubs", "img/QC.png", false));
        deck.add(new Card(10, "clubs", "img/KC.png", false));

    }
    //METHOD for Shuffling the Deck
    public void shuffleDeck(){
        for(int i = 0; i <= 7; i++){
            Collections.shuffle(deck);
        }
    }

}
