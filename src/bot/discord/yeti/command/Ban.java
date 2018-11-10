package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.API;
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

public class Ban {


    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();

        if(event.getMember().hasPermission(Permission.BAN_MEMBERS)){
            StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
            guild.getMembers().forEach(m -> users.add(m));


            try {

                String username = msg.getContentRaw().substring(msg.getContentRaw().indexOf("yban") + 6);
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


                                    try{
                                        guild.getController().ban(users.get(x).getUser().getId(), 0, reason).queue();

                                        msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Successfully banned " + users.get(x).getUser().getName())
                                                .setThumbnail("https://i.imgur.com/t4yagto.png")
                                                .build()).queue();
                                        count++;
                                    }catch (Exception e){


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
            } catch (StringIndexOutOfBoundsException e) {
                msg.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Ban")
                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                        .setDescription(Dic.ban2)
                        .build()).queue();


            }


        }else{

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


