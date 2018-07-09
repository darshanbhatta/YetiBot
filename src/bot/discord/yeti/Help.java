package bot.discord.yeti;

import net.dv8tion.jda.core.entities.Message;

import java.util.Timer;
import java.util.TimerTask;

public class Help {

    public static void run(Message msg) {

        msg.getChannel().sendMessage("test").queue(m -> {
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
