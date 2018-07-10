package bot.discord.yeti.currency;


import net.dv8tion.jda.core.entities.User;

import java.io.Serializable;

public class Jewels implements Serializable {
    private  String playerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    private int balance;

    public Jewels(String playerId, String name, int balance) {
        this.playerId = playerId;
        this.name = name;
        this.balance = balance;
    }

    public String  getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }


    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

}
