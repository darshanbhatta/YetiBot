package bot.discord.yeti;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class Command extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMessage().getContent().startsWith("!")) {
            String[] arg = e.getMessage().getContent().replaceFirst("!", "").split(" ");

            switch (arg[0]) {
                case "help":

                    Help.run(e.getMessage());
                    break;

                case "snap":
                    Snap.run(e.getMessage(), e.getGuild());
                    break;


            }

        }


    }
}
