package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.Dic;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Lmgtfy {

    public static void run(MessageReceivedEvent event) {

        String[] content = event.getMessage().getContentRaw().split(" ");

        String link = "http://lmgtfy.com/?q=";
        if(content.length==1){
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Let me google that for you")
                    .setThumbnail("https://lmgtfy.com/assets/logo-color-small-70dbef413f591a3fdfcfac7b273791039c8fd2a5329e97c4bfd8188f69f0da34.png")
                    .setDescription("```ylmgtfy [search query]```")
                    .build()).queue();
        }else{
            if(content.length>2){
                for(int x =1; x<content.length;x++){
                    if(x==content.length-1){
                        link+=content[x];
                    }else
                        link+=content[x]+"+";
                }
            }else{
                link+=content[1];
            }
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Let me google that for you")
                    .setThumbnail("https://lmgtfy.com/assets/logo-color-small-70dbef413f591a3fdfcfac7b273791039c8fd2a5329e97c4bfd8188f69f0da34.png")
                    .setDescription(link)
                    .build()).queue();
        }





    }

}
