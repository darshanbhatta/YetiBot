package bot.discord.yeti.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class CreateTextChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();
        if(code.equals("!addtext")){
            e.getChannel().sendMessage("Format: !addtext (channel name)").queue();
        }
        else if(e.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            System.out.println(code);
            e.getGuild().getController().createTextChannel(code.substring(code.indexOf("addtext")+8).trim()).queue();
            e.getChannel().sendMessage("Created text channel \""+(code.substring(code.indexOf("addtext")+8).trim())+"\"").queue(w -> {

            });


        }else{
            e.getChannel().sendMessage("You do not have permission.").queue(w -> {

            });
        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);
    }
}
