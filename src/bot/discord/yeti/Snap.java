package bot.discord.yeti;

import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Snap {

    public static void run(Message msg, MessageReceivedEvent event) {
        Guild guild = event.getGuild();
        ArrayList<Member> users = new ArrayList();
        ArrayList<Member> banned = new ArrayList<>();
        StringBuilder builder = new StringBuilder(guild.getName() + " (" + guild.getId() + ")\r\n---");
        guild.getMembers().forEach(m -> users.add(m));
        Collections.shuffle(users);
        List<Invite> invite = event.getGuild().getInvites().complete();
        invite.get(0).getURL();
        System.out.println(users.get(0).toString());
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

        for (int x = 0; x < users.size() / 2; x++) {
            banned.add(users.get(x));
            String finalQuote = quote;
            int finalX = x;
            String finalQuote1 = quote;
            if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {
                users.get(x).getUser().openPrivateChannel().queue((channel) ->
                {

                    channel.sendMessage(finalQuote + " " + invite.get(0).getURL()).queue(m -> {
                        if (!users.get(finalX).isOwner() && !users.get(finalX).getUser().isBot()) {
                            guild.getController().ban(users.get(finalX).getUser().getId(), 0).queue();
                            // System.out.println(users.get(x).getUser().getName());
                        }


                        msg.getChannel().sendFile(new File("img/thanos_snap.jpg"), new MessageBuilder().append(" ").build()).queue();
                        msg.getChannel().sendMessage(finalQuote1).queue(w -> {

                            Timer time = new Timer();
                            time.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    msg.delete().queue();

                                }
                            }, 5000);

                        });


                    });

                });
            }


        }
    }
}







