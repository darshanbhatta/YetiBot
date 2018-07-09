package bot.discord.yeti;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.IOException;

public class Command extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getContentRaw().startsWith("!")) {
            String[] arg = e.getMessage().getContentRaw().replaceFirst("!", "").split(" ");

            switch (arg[0]) {
                case "help":

                    try {
                        Help.run(e.getMessage());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;

                case "snap":
                    Snap.run(e.getMessage(), e);
                    break;


            }

        }


    }
}
