package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class CreateVoiceChannel {
    public static void run(MessageReceivedEvent e){
        String code = e.getMessage().getContentRaw().toLowerCase();
        if(code.equals("yaddvoice")){
            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Create Text Channel")
                    .setThumbnail("https://i.imgur.com/XX5WaAY.png")
                    .addField("Format","```yaddvoice (channel name)```",true)
                    .build()).queue();
        }
        else if(e.getMember().hasPermission(Permission.MANAGE_CHANNEL)){
            System.out.println(code);
            e.getGuild().getController().createVoiceChannel(code.substring(code.indexOf("addvoice")+9).trim()).queue();
            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Created Voice channel \""+(code.substring(code.indexOf("addvoice")+9).trim())+"\"")
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
