package bot.discord.yeti.game.numguesser;

import java.io.Serializable;

public class NumGuessUser implements Serializable {
    private String user;
    private String name;
    private int bet;
    private int number;
    private int distance;



    public NumGuessUser(String user, String name, int bet, int number) {
        this.user = user;
        this.name = name;
        this.bet = bet;
        this.number = number;

    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBet() {
        return bet;
    }

    public int getNumber() {
        return number;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
