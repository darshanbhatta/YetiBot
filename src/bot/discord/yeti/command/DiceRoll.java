package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiceRoll {

    public static void run(MessageReceivedEvent event) throws IOException {

        String[] content = event.getMessage().getContentRaw().split(" ");


        if(content.length==1){
            int num = (int)(Math.random()*6)+1;

            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Dice Roll Format")
                    .setThumbnail("https://i.imgur.com/zxB0E1C.png")
                    .setDescription("**"+num+"**")
                    .build()).queue();
        }else if(content.length==2){

            try{
                int num = (int)(Math.random()*Integer.parseInt(content[1]))+1;

                event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Dice Roll Format")
                        .setThumbnail("https://i.imgur.com/zxB0E1C.png")
                        .setDescription("**"+num+"**")
                        .build()).queue();
            }
            catch (Exception a){
                event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Dice Roll Format")
                        .setThumbnail("https://i.imgur.com/zxB0E1C.png")
                        .setDescription("```ydice [optional number (default is 6)]```\nExample: ```ydice 10```")
                        .build()).queue();
            }

        }else{
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Dice Roll Format")
                    .setThumbnail("https://i.imgur.com/zxB0E1C.png")
                    .setDescription("```ydice [optional number (default is 6)]\nExample: ydice 10")
                    .build()).queue();

        }

        final RandomAccessFile f = new RandomAccessFile(new File("fortune.txt"), "r");
        final long randomLocation = (long) (Math.random() * f.length());
        f.seek(randomLocation);
        f.readLine();
        String line = f.readLine();



    }

}
