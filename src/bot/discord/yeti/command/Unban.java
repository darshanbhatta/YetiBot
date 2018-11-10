package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.Dic;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Unban {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<User> users = new ArrayList();
        if(event.getMember().hasPermission(Permission.BAN_MEMBERS)){
        StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
        List<Guild.Ban> bans = guild.getBanList().complete();

        for (int x = 0; x < bans.size(); x++) {
            users.add(bans.get(x).getUser());
        }
        try {
            String command = msg.getContentRaw().substring(msg.getContentRaw().indexOf("yunban") + 7);
            if (command.toLowerCase().equals("all")) {
                if (users.size() != 0) {
                    for (int x = 0; x < users.size(); x++) {
                        guild.getController().unban(users.get(x).getId()).queue();


                    }

                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Successfully unbanned all users","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png").build()).queue();

                } else {

                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No users in ban list.","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png").build()).queue();

                }


            } else if (command != null) {
                int count = 0;
                if (users.size() != 0) {
                    for (int x = 0; x < users.size(); x++) {
                        if (command.toLowerCase().contains(users.get(x).getId().toLowerCase())) {
                            guild.getController().unban(users.get(x).getId()).queue();

                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Successfully unbanned " + users.get(x).getName(),"http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png").build()).queue();

                            count++;
                            break;
                        }

                    }
                    if (count == 0) {

                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Unable to find user in ban list.","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png").build()).queue();

                    }
                } else {


                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No users in ban list.","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png").build()).queue();

                }


            } else {
                msg.getChannel().sendMessage("Format: ").queue();
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Unban Format","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png")
                        .setDescription("```yunban @username/all```")
                        .build()).queue();

            }
        } catch (StringIndexOutOfBoundsException e) {
            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Unban Format","http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png")
                    .setDescription("```yunban @username/all```")
                    .build()).queue();


        }

    }else{

            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("[Unban] "+Dic.noPermission,"http://icons-for-free.com/free-icons/png/512/2537322.png","http://icons-for-free.com/free-icons/png/512/2537322.png")
                    .build()).queue();
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


