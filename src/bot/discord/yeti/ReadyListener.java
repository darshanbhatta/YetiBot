package bot.discord.yeti;


import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {
    Guild guild;

    public void onReady(ReadyEvent e) {
        String out = "\nThis bot is running on";
        for (Guild g : e.getJDA().getGuilds()) {
            out += g.getName() + "\n";

        }
        System.out.println(out);


    }






}
