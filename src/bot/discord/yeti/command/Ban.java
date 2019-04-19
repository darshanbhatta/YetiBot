package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.Dic;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
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
import java.util.concurrent.TimeUnit;

public class Ban {


    public static void run(Message msg, MessageReceivedEvent event, EventWaiter waiter) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();


        System.out.println(waiter.toString());

        if (event.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
            guild.getMembers().forEach(m -> users.add(m));


            try {

                String username = msg.getContentRaw().substring(msg.getContentRaw().indexOf("yban") + 6);
                System.out.println(username);
                if (username.equals("ll") || username.equals("everyone")) {
                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Ban")
                            .setThumbnail("https://i.imgur.com/t4yagto.png")
                            .setDescription("Are you sure you want to ban all members? Note this action will remove everyone with lower permission than you from the server. **Note you have 10 seconds to reply**\n  Type the following command to continue: ```Yes ban everyone```")
                            .build()).queue();
                    waiter.waitForEvent(MessageReceivedEvent.class,
                            // make sure it's by the same user, and in the same channel
                            e -> e.getAuthor().equals(event.getAuthor()) && e.getChannel().equals(event.getChannel()),
                            // respond, inserting the name they listed into the response
                            e -> {

                                if(e.getMessage().getContentRaw().toLowerCase().trim().equals("yes ban everyone")){
                                    int count =0;
                                    for (int x = 0; x < users.size(); x++) {
                                        try {
                                            guild.getController().ban(users.get(x).getUser().getId(), 0, "").queue();
                                        }catch (Exception woah){
                                            count++;
                                        }

                                    }
                                    if(count ==0){
                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Banned all users")
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .build()).queue();
                                    }else{
                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Banned all users")
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .setDescription("Failed to ban **"+count+"** users due to permission errors")
                                                .build()).queue();
                                    }





                                }else{
                                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Banning all users is canceled")
                                            .setThumbnail("https://i.imgur.com/t4yagto.png")
                                            .build()).queue();
                                }

                            },
                            // if the user takes more than a minute, time out
                            10, TimeUnit.SECONDS, () ->  {  msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Banning all users is canceled")
                                    .setThumbnail("https://i.imgur.com/t4yagto.png")
                                    .build()).queue();});
                } else {


                    if (username.contains("<")) {
                        username = username.substring(2, username.indexOf(">"));
                    }
                    String reason = "";
                    try {
                        reason = msg.getContentRaw().substring(msg.getContentRaw().indexOf(">") + 1);
                    } catch (IndexOutOfBoundsException e) {

                    }


                    if (username != null) {
                        int count = 0;
                        if (users.size() != 0) {
                            for (int x = 0; x < users.size(); x++) {

                                if (username.toLowerCase().contains(users.get(x).getUser().getId().toLowerCase())) {


                                    try {
                                        guild.getController().ban(users.get(x).getUser().getId(), 0, reason).queue();

                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Successfully banned " + users.get(x).getUser().getName())
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .build()).queue();
                                        count++;
                                    } catch (Exception e) {


                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .setDescription(Dic.ban1)
                                                .build()).queue();


                                    }


                                }

                            }
                        } else {


                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                                    .setThumbnail("https://i.imgur.com/t4yagto.png")
                                    .setDescription("Unable to ban")
                                    .build()).queue();


                        }


                    } else {
                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Ban")
                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                .setDescription(Dic.ban2)
                                .build()).queue();

                    }
                }
            } catch (StringIndexOutOfBoundsException e) {
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Ban")
                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                        .setDescription(Dic.ban2)
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


