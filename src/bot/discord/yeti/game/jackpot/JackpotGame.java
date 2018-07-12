package bot.discord.yeti.game.jackpot;

import bot.discord.yeti.game.roulette.RouletteUsers;

import java.io.Serializable;
import java.util.ArrayList;

public class JackpotGame implements Serializable {


    boolean gameInProgess;

    public ArrayList<JackpotTicket> getJackpotTicket() {
        return jackpotTicket;
    }

    public void setJackpotTicket(ArrayList<JackpotTicket> jackpotTicket) {
        this.jackpotTicket = jackpotTicket;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    String serverid;
    ArrayList<JackpotTicket> jackpotTicket = new ArrayList();
    ArrayList<JackpotUser> jackpotUsers = new ArrayList<>();

    public ArrayList<JackpotUser> getJackpotUsers() {
        return jackpotUsers;
    }

    public void setJackpotUsers(ArrayList<JackpotUser> jackpotUsers) {
        this.jackpotUsers = jackpotUsers;
    }

    int potAmount;

    public JackpotGame(String serverid, String userid,boolean gameInProgess) {
        this.serverid = serverid;
       // jackpotTicket.add(new JackpotTicket(userid));
        this.gameInProgess = gameInProgess;
    }

    public void addPot(int num){
        potAmount+=num;

    }

    public int getPot(){
        return potAmount;

    }

    public JackpotGame(boolean gameInProgess, String serverid) {
        this.gameInProgess = gameInProgess;
        this.serverid = serverid;
    }

    public boolean isGameInProgess() {
        return gameInProgess;
    }

    public void setGameInProgess(boolean gameInProgess) {
        this.gameInProgess = gameInProgess;
    }
}
