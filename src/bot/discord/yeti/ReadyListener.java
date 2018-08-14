package bot.discord.yeti;


import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.discordbots.api.client.DiscordBotListAPI;

import java.util.ArrayList;

public class ReadyListener extends ListenerAdapter {
    Guild guild;
    private String allGuilds;
ArrayList<String> w = new ArrayList();

    public void onReady(ReadyEvent e) {
        int count =0;
        String out = "\nThis bot is running on\n";
        for (Guild g : e.getJDA().getGuilds()) {
            out += g.getName() + "\n";
            if(g.getMembers().size()!=2)
            System.out.println(g.getName()+ " " + g.getMembers().size());


        }

        allGuilds = out;
        System.out.println(w.size());

    }











}
