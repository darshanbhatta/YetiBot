package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class CreateTextChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();
        if(code.equals("yaddtext")){
            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Create Text Channel")
                    .setThumbnail("https://i.imgur.com/XX5WaAY.png")
                    .addField("Format","```yaddtext (channel name)```",true)
                    .build()).queue();
          //  e.getChannel().sendMessage("").queue();
        }
        else if(e.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            System.out.println(code);
            e.getGuild().getController().createTextChannel(code.substring(code.indexOf("addtext")+8).trim()).queue();

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Created text channel \""+(code.substring(code.indexOf("addtext")+8).trim())+"\"")
                        .setThumbnail("https://i.imgur.com/XX5WaAY.png")
                        .build()).queue();




        }else{


            e.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Create Text Channel")
                    .setThumbnail("https://i.imgur.com/XX5WaAY.png")
                    .setDescription("You do not have permission.")
                    .build()).queue();

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
