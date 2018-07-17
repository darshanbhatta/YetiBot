package bot.discord.yeti.game.numguesser;

import java.io.Serializable;
import java.util.ArrayList;

public class NumGuessGame implements Serializable {
    ArrayList<NumGuessUser> numGuessUsers = new ArrayList();
    String serverID;
    int potSize;
    int betAmount;

    public NumGuessGame(String serverID, int betAmount) {
        this.serverID = serverID;
        this.betAmount = betAmount;
    }

    public ArrayList<NumGuessUser> getNumGuessUsers() {
        return numGuessUsers;
    }

    public void setNumGuessUsers(ArrayList<NumGuessUser> numGuessUsers) {
        this.numGuessUsers = numGuessUsers;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public int getPotSize() {
        return potSize;
    }

    public void setPotSize(int potSize) {
        this.potSize = potSize;
    }

    public int getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
