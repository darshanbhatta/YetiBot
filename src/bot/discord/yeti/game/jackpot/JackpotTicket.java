package bot.discord.yeti.game.jackpot;

import java.io.Serializable;

public class JackpotTicket implements Serializable {
    String userid;

    public String getName() {
        return name;
    }

    String name;

    public JackpotTicket(String userid,String name) {
        this.userid = userid;
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
