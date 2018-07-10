package bot.discord.yeti;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Unban {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<User> users = new ArrayList();

        StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
        List<Guild.Ban> bans = guild.getBanList().complete();

        for (int x = 0; x < bans.size(); x++) {
            users.add(bans.get(x).getUser());
        }
        try {
            String command = msg.getContentRaw().substring(msg.getContentRaw().indexOf("!unban") + 7);
            if (command.toLowerCase().equals("all")) {
                if (users.size() != 0) {
                    for (int x = 0; x < users.size(); x++) {
                        guild.getController().unban(users.get(x).getId()).queue();


                    }
                    msg.getChannel().sendMessage("Successfully unbanned all users").queue();
                } else {
                    msg.getChannel().sendMessage("Error no users in ban list").queue();
                }


            } else if (command != null) {
                int count = 0;
                if (users.size() != 0) {
                    for (int x = 0; x < users.size(); x++) {
                        if (users.get(x).getName().toLowerCase().equals(command.toLowerCase())) {
                            guild.getController().unban(users.get(x).getId()).queue();
                            msg.getChannel().sendMessage("Successfully unbanned " + users.get(x).getName()).queue(w -> {


                            });
                            count++;
                        }

                    }
                    if (count == 0) {
                        msg.getChannel().sendMessage("Error cannot find user in ban list").queue(w -> {


                        });
                    }
                } else {

                    msg.getChannel().sendMessage("Error no users in ban list").queue(w -> {


                    });
                }


            } else {
                msg.getChannel().sendMessage("Error incorrect format. !unban [username]").queue();
            }
        } catch (StringIndexOutOfBoundsException e) {
            msg.getChannel().sendMessage("Error incorrect format. !unban [username]").queue(w -> {


            });


        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                msg.delete().queue();

            }
        }, 5000);
    }


}


