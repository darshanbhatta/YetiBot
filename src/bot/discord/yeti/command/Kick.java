package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.Dic;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Kick {

    public static void run(Message msg, MessageReceivedEvent event) {


        if (msg.getContentRaw().toLowerCase().equals("ykick")) {
            msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                    .setThumbnail("https://i.imgur.com/t4yagto.png")
                    .addField("Format","```ykick @username (Optional: Reason)```",false)
                    .build()).queue();

        } else {
            if (event.getMember().hasPermission(Permission.KICK_MEMBERS)) {
                Guild guild = event.getGuild();
                ArrayList<Member> users = new ArrayList();

                StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
                guild.getMembers().forEach(m -> users.add(m));


                try {

                    String username = msg.getContentRaw().substring(msg.getContentRaw().indexOf("yban") + 5);
                    if (username.contains("<")) {
                        username = username.substring(2, username.indexOf(">"));
                    }
                    String reason = "";
                    try {
                        reason = msg.getContentRaw().substring(msg.getContentRaw().indexOf(">") + 1);
                    } catch (IndexOutOfBoundsException e) {

                    }
                    //      System.out.println(reason + " lol " + username + "  "  +users.get(1).getUser().getIdLong()+"");
                    if (username != null) {
                        final int[] count = {0};
                        if (users.size() != 0) {
                            for (int x = 0; x < users.size(); x++) {
                                System.out.println(reason + " lol " + username + "  " + users.get(x).getUser().getId() + "");
                                if (username.toLowerCase().contains(users.get(x).getUser().getId().toLowerCase())) {
                                    if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {

                                        try {
                                            guild.getController().kick(users.get(x), reason).queue();

                                                count[0]++;

                                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.GREEN).setTitle("Kicked")
                                                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                        .setDescription("Successfully kicked " + users.get(x).getUser().getName())
                                                        .build()).queue();

                                        } catch (Exception e) {


                                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                                    .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                    .setDescription("Unable to kick a member with higher or equal highest role than yourself!")
                                                    .build()).queue();

                                        }

                                    } else {
                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .setDescription(Dic.noPermission)
                                                .build()).queue();
                                    }


                                }

                            }

                        } else {



                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                                     .setDescription("Unable to kick.")
                                        .build()).queue();


                        }


                    } else {
                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                .addField("Format","```ykick @username (Optional: Reason)```",false)
                                .build()).queue();
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                            .setThumbnail("https://i.imgur.com/t4yagto.png")
                            .addField("Format","```ykick @username (Optional: Reason)```",false)
                            .build()).queue();


                }


            } else {

                msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                        .setDescription(Dic.noPermission)
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
}


