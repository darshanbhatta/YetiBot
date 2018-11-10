package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class Snap {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();
        ArrayList<Member> banned = new ArrayList<>();
        StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
        guild.getMembers().forEach(m -> users.add(m));
        Collections.shuffle(users);
        Collections.shuffle(users);
        Collections.shuffle(users);
        Collections.shuffle(users);
        System.out.println(users);
        final boolean[] noBanned = {false};
        List<Invite> invite = event.getGuild().getInvites().complete();
        if(event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            if(msg.getContentRaw().contains("ban")){

                Scanner thanosTxt = null;
                try {
                    thanosTxt = new Scanner(new File("txt/thanos.txt"));

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                int lineNumber = (int) (Math.random() * 13) + 1;
                int count = 0;
                String quote = "";
                while (thanosTxt.hasNext()) {
                    count++;
                    if (count == lineNumber) {
                        quote = thanosTxt.nextLine();
                        break;
                    } else {

                        thanosTxt.nextLine();
                    }


                }
                final int[] banCounter = {0};
                for (int x = 0; x < users.size() / 2; x++) {
                    banned.add(users.get(x));

                    String finalQuote = quote;
                    int finalX = x;
                    String finalQuote1 = quote;
                    if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {
                        banCounter[0] = banCounter[0] + 1;
                        noBanned[0] = true;
                        int finalX1 = x;
                        users.get(x).getUser().openPrivateChannel().queue((channel) ->
                        {

                            channel.sendFile(new File("img/thanos_snap.jpg"), new MessageBuilder().append(finalQuote1).build()).queue(m -> {
                                if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {

                                    guild.getController().ban(users.get(finalX).getUser().getId(), 0).queue();

                                    System.out.println(users.get(0).getUser().getName());
                                }


                            });

                        });


                    }
                }

                if(!noBanned[0]){
                    msg.getChannel().sendFile(new File("img/thanos_loses.jpeg"), new MessageBuilder().append("In time you will know what it’s like to lose.").build()).queue();

                }else{
                    String  us = "users";
                    if (banCounter[0] == 1) {
                        us = "user";
                    }
                    msg.getChannel().sendFile(new File("img/thanos_snap.jpg"), new MessageBuilder().append(quote + "\nBanned **" + banCounter[0] + "** " + us).build()).queue();
                }


            }else{

                try{
                    invite.get(0).getURL();
                    Scanner thanosTxt = null;
                    try {
                        thanosTxt = new Scanner(new File("txt/thanos.txt"));

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    int lineNumber = (int) (Math.random() * 13) + 1;
                    int count = 0;
                    String quote = "";
                    while (thanosTxt.hasNext()) {
                        count++;
                        if (count == lineNumber) {
                            quote = thanosTxt.nextLine();
                            break;
                        } else {

                            thanosTxt.nextLine();
                        }


                    }
                    final int[] banCounter = {0};
                    for (int x = 0; x < users.size() / 2; x++) {
                        banned.add(users.get(x));

                        String finalQuote = quote;
                        int finalX = x;
                        String finalQuote1 = quote;
                        if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {
                            banCounter[0] = banCounter[0] + 1;
                            noBanned[0] = true;
                            int finalX1 = x;
                            users.get(x).getUser().openPrivateChannel().queue((channel) ->
                            {

                                channel.sendFile(new File("img/thanos_snap.jpg"), new MessageBuilder().append(finalQuote1 + "\n"+  invite.get(0).getURL()).build()).queue(m ->{
                                    if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {

                                        guild.getController().kick(users.get(finalX).getUser().getId()).queue();
                                        System.out.println(banCounter[0]);


                                        System.out.println(users.get(0).getUser().getName());
                                    }


                                });

                            });


                        }
                    }
                    if(!noBanned[0]){
                        msg.getChannel().sendFile(new File("img/thanos_loses.jpeg"), new MessageBuilder().append("In time you will know what it’s like to lose.").build()).queue();

                    }else{
                        String  us = "users";
                        if (banCounter[0] == 1) {
                            us = "user";
                        }
                        msg.getChannel().sendFile(new File("img/thanos_snap.jpg"), new MessageBuilder().append(quote + "\nKicked **" + banCounter[0] + "** " + us).build()).queue();
                    }

                }catch (Exception e){
                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No permanent invite link set, do you really want to permanently ban half your users?","https://i.imgur.com/ISOFDE5.png","https://i.imgur.com/ISOFDE5.png").setDescription("Type `ysnap ban` to continue").build()).queue();


                }




            }



        }else{

            msg.getChannel().sendMessage("You do not have permission to access this command").queue();

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







