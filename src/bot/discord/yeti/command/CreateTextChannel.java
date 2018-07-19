package bot.discord.yeti.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CreateTextChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();

        if(e.getMember().hasPermission(Permission.ADMINISTRATOR)){
            System.out.println(code);
            e.getGuild().getController().createTextChannel(code.substring(code.indexOf("addtext")+9).trim()).queue();
            e.getChannel().sendMessage("Created text channel \""+(code.substring(code.indexOf("addtext")+9).trim())+"\"").queue(w -> {

            });


        }else{
            e.getChannel().sendMessage("Error you do not have permission").queue(w -> {

            });
        }


    }
}
