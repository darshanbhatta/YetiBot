package bot.discord.yeti;


public class Card {

    private int number;
    private String suit;
    private String image;
    private boolean ace;

    public Card(int n, String s, String i, boolean a){
        number = n;
        suit = s;
        image = i;
        ace = a;
    }

    public int getNumber() {
        return number;
    }

    public String getImage() {
        return image;
    }

    public boolean isAce() {
        return ace;
    }
}
