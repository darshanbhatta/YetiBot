package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.API;
import bot.discord.yeti.dictionary.Dic;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.util.broadcast.OptOutList;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

public class Broadcast {

    public static void run(MessageReceivedEvent e){
        OptOutList[] broadCast = new OptOutList[1];
        try {
            FileInputStream fileIn = new FileInputStream("broadcast.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            broadCast[0] = (OptOutList) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
        if(e.getMessage().getContentRaw().toLowerCase().contains("broadcast")){

            if(e.getAuthor().getId().equals(API.darshDiscordID)||e.getAuthor().getId().equals(API.arcticDiscordID)){



               // patch name, new features
                LocalDate currentDate = LocalDate.now();
                for (Guild g : e.getJDA().getGuilds()) {
                    if(!broadCast[0].getGuildis().contains(g.getId())){
                        try{
                            g.getDefaultChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Notification")
                                    .setThumbnail("https://i.imgur.com/xRpsqdl.png")
                                    .setDescription("If you don't want to receive messages from us, please type **yunsubscribe**\n\n"+e.getMessage().getContentRaw().substring(10)+"\n\n")
                                    .setFooter(currentDate.getMonth() + " " + currentDate.getDayOfMonth() + ", "+ currentDate.getYear(),"http://icons.iconarchive.com/icons/paomedia/small-n-flat/1024/calendar-icon.png")
                                    .build()).queue();
                            System.out.println("sent");
                        }catch (Exception eeee){
                            System.out.println("failed");
                        }




                    }


                }
        }


        } else if(e.getMessage().getContentRaw().toLowerCase().contains("unsubscribe")){
            if (e.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                broadCast[0].getGuildis().add(e.getGuild().getId());
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = new FileOutputStream("broadcast.ser");
                    ObjectOutputStream objectOutputStream = null;
                    try {
                        objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(broadCast[0]);
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Notification")
                        .setThumbnail("https://i.imgur.com/xRpsqdl.png")
                        .setDescription("You've successfully opted out of all Yeti notifications If you want to receive messages from us, please type **ysubscribe**")
                        .build()).queue();
            }else{
                e.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                        .setDescription(Dic.noPermission)
                        .build()).queue();
            }


        }else {
            if (e.getMember().hasPermission(Permission.MANAGE_SERVER)) {
                try{
                    broadCast[0].getGuildis().remove(e.getGuild().getId());
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream("broadcast.ser");
                        ObjectOutputStream objectOutputStream = null;
                        try {
                            objectOutputStream = new ObjectOutputStream(fileOutputStream);
                            objectOutputStream.writeObject(broadCast[0]);
                            objectOutputStream.close();
                            fileOutputStream.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Notification")
                            .setThumbnail("https://i.imgur.com/xRpsqdl.png")
                            .setDescription("You've successfully opted in of all Yeti notifications")
                            .build()).queue();




                }catch (Exception ea){
                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Notification")
                            .setThumbnail("https://i.imgur.com/xRpsqdl.png")
                            .setDescription("You are already subscribed to Yeti notifications")
                            .build()).queue();
                }


            }else{
                e.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Something went wrong")
                        .setThumbnail("https://i.imgur.com/t4yagto.png")
                        .setDescription(Dic.noPermission)
                        .build()).queue();
            }
        }



        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);



    }
}
