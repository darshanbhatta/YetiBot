package bot.discord.yeti.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CreateVoiceChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();

        if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
            System.out.println(code);
            e.getGuild().getController().createVoiceChannel(code.substring(code.indexOf("addvoice")+9).trim()).queue();
            e.getChannel().sendMessage("Created voice channel \""+(code.substring(code.indexOf("addvoice")+9).trim())+"\"").queue(w -> {

            });


        }else{
            e.getChannel().sendMessage("Error you do not have permission").queue(w -> {

            });
        }


    }
}
