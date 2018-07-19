package bot.discord.yeti.command;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Ban {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();

        StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
        guild.getMembers().forEach(m -> users.add(m));


        try {

            String username = msg.getContentRaw().substring(msg.getContentRaw().indexOf("!ban") + 5);
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
                int count = 0;
                if (users.size() != 0) {
                    for (int x = 0; x < users.size(); x++) {
                        System.out.println(reason + " lol " + username + "  "  +users.get(x).getUser().getId()+"");
                        if (username.toLowerCase().contains(users.get(x).getUser().getId().toLowerCase())) {
                            if(event.getMember().hasPermission(Permission.ADMINISTRATOR)) {

                                try{
                                    guild.getController().ban(users.get(x).getUser().getId(), 0, reason).queue();
                                    msg.getChannel().sendMessage("Successfully banned " + users.get(x).getUser().getName()).queue(w -> {


                                    });
                                    count++;
                                }catch (Exception e){
                                    msg.getChannel().sendMessage("Error can't ban a member with higher or equal highest role than yourself!").queue(w -> {

                                    });

                                }

                            }else{
                                msg.getChannel().sendMessage("Error you do not have permission").queue(w -> {

                                });

                            }


                        }

                    }
                } else {

                    msg.getChannel().sendMessage("Error cannot ban").queue(w -> {


                    });
                }


            } else {
                msg.getChannel().sendMessage("Error incorrect format. !ban @username %optional reason%").queue();
            }
        } catch (StringIndexOutOfBoundsException e) {
            msg.getChannel().sendMessage("Error incorrect format. !ban @username %optional reason%").queue(w -> {


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


