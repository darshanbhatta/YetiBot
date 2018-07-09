package bot.discord.yeti;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class Snap {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();

        StringBuilder builder = new StringBuilder(guild.getName()+" ("+guild.getId()+")\r\n---");
        guild.getMembers().forEach(m ->users.add(m));
        Collections.shuffle(users);

        for(int x =0; x<users.size()/2;x++){

            if(!users.get(x).isOwner()) {
                guild.getController().ban(users.get(x).getUser().getId(), 0).queue();
                System.out.println(users.get(x).getUser().getName());
            }



        }



        msg.getChannel().sendMessage(users.toString()).queue(m -> {
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
