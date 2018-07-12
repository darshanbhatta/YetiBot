package bot.discord.yeti.game.jackpot;

import java.io.Serializable;

public class JackpotUser implements Serializable {
    String id;
    String name;
    int ticket;

    public JackpotUser(String id, String name, int ticket) {
        this.id = id;
        this.name = name;
        this.ticket = ticket;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }
}
