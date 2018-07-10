package bot.discord.yeti;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Help {

    public static void run(Message msg, MessageReceivedEvent e) throws IOException {


        msg.getChannel().sendMessage("yo").queue(m -> {


            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                   m.editMessage("hehehe").queue();
                }
            }, 500);

        });
    }
}
