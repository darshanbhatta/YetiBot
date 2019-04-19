package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Fortune {

    public static void run(MessageReceivedEvent event) throws IOException {


        final RandomAccessFile f = new RandomAccessFile(new File("fortune.txt"), "r");
        final long randomLocation = (long) (Math.random() * f.length());
        f.seek(randomLocation);
        f.readLine();
        String line = f.readLine();

        event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.WHITE).setTitle("Fortune Cookie")
                .setThumbnail("https://i.imgur.com/BlKIoFb.png")
                .setDescription(line)
                .build()).queue();


    }

}
