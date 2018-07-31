package bot.discord.yeti.util.broadcast;

import net.dv8tion.jda.core.entities.Guild;

import java.io.Serializable;
import java.util.ArrayList;

public class OptOutList implements Serializable {
    ArrayList<String> guildis = new ArrayList();

    public ArrayList<String> getGuildis() {
        return guildis;
    }

    public void setGuildis(ArrayList<String> guildis) {
        this.guildis = guildis;
    }
}
