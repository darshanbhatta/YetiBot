package bot.discord.yeti.game.roulette;

import java.io.Serializable;
import java.util.ArrayList;

public class RouletteGame implements Serializable {


    boolean gameInProgess;

    public ArrayList<RouletteUsers> getRouletteUsers() {
        return rouletteUsers;
    }

    public void setRouletteUsers(ArrayList<RouletteUsers> rouletteUsers) {
        this.rouletteUsers = rouletteUsers;
    }

    public String getServerid() {
        return serverid;
    }

    public void setServerid(String serverid) {
        this.serverid = serverid;
    }

    String serverid;
    ArrayList<RouletteUsers> rouletteUsers = new ArrayList();

    public RouletteGame(String serverid,String userid, String bet, int betAmount, boolean gameInProgess) {
        this.serverid = serverid;
        rouletteUsers.add(new RouletteUsers(userid,bet,betAmount));
        this.gameInProgess = gameInProgess;
    }

    public RouletteGame(boolean gameInProgess, String serverid) {
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
