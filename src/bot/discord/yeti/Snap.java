package bot.discord.yeti;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Snap {

    public static void run(Message msg, Guild guild) {
        Guild g = guild;
        ArrayList<Member> users = new ArrayList();

        StringBuilder builder = new StringBuilder(guild.getName()+" ("+guild.getId()+")\r\n---");
        guild.getMembers().forEach(m ->users.add(m));



        msg.getChannel().sendMessage().queue(m -> {
            Timer time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {
                    msg.delete().queue();

                }
            }, 5000);


        });
    }

}
