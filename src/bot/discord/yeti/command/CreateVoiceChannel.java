package bot.discord.yeti.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class CreateVoiceChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();
        if(code.equals("y!addvoice")){
            e.getChannel().sendMessage("Format: y!addvoice (channel name)").queue();
        }
        else if(e.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            System.out.println(code);
            e.getGuild().getController().createVoiceChannel(code.substring(code.indexOf("addvoice")+9).trim()).queue();
            e.getChannel().sendMessage("Created voice channel \""+(code.substring(code.indexOf("addvoice")+9).trim())+"\"").queue(w -> {

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
