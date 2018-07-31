package bot.discord.yeti.command;

import bot.discord.yeti.currency.Bank;
import bot.discord.yeti.dictionary.API;
import bot.discord.yeti.game.blackjack.BlackjackGameHolder;
import bot.discord.yeti.util.broadcast.OptOutList;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;
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



                for (Guild g : e.getJDA().getGuilds()) {
                    if(!broadCast[0].getGuildis().contains(g.getId())){

                        g.getDefaultChannel().sendMessage("If you don't want to receive messages from us, please type **!unsubscribe**\n\n"+e.getMessage().getContentRaw().substring(11)).queue();


                    }


                }
        }


        }else if(e.getMessage().getContentRaw().toLowerCase().contains("unsubscribe")){
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


            e.getChannel().sendMessage("You've successfully opted out of all Yeti notifications If you want to receive messages from us, please type **!subscribe**").queue();
        }else{
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


                e.getChannel().sendMessage("You've successfully opted in of all Yeti notifications").queue();



            }catch (Exception ea){
                e.getChannel().sendMessage("You are already subscribed to Yeti notifications").queue();

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
