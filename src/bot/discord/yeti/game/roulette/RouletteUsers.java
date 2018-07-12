package bot.discord.yeti.game.roulette;

import java.io.Serializable;

public class RouletteUsers implements Serializable {
    String userid;
    String bet;
    int betAmount;

    public RouletteUsers(String userid, String bet, int betAmount) {
        this.userid = userid;
        this.bet = bet;
        this.betAmount = betAmount;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBet() {
        return bet;
    }

    public void setBet(String bet) {
        this.bet = bet;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
