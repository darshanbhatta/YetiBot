package bot.discord.yeti;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Help {

    public static void run(Message msg) throws IOException {


        msg.getChannel().sendFile(new File("img/5C.png"),new MessageBuilder().append(" ").build()).queue(m -> {
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
