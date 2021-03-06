package bot.discord.yeti.command;

import bot.discord.yeti.game.jackpot.JackpotGame;
import bot.discord.yeti.game.jackpot.JackpotGameHolder;
import bot.discord.yeti.util.poll.Poll;
import bot.discord.yeti.util.poll.PollHolder;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.Inet4Address;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class PollManager {

    public static void run(MessageReceivedEvent event){
        //!poll (title) [opinions separated by commas)
        String[] code = event.getMessage().getContentRaw().split(" ");
        final PollHolder[] pollHolders = new PollHolder[1];
        try {
            FileInputStream fileIn = new FileInputStream("poll.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            pollHolders[0] = (PollHolder) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException i) {
            i.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }

        if (event.getMessage().getContentRaw().contains("(")&&event.getMessage().getContentRaw().contains("[")&&event.getMessage().getContentRaw().contains("<")) {
            String test = event.getMessage().getContentRaw();

            String title = test.substring(test.indexOf("(")+1,test.indexOf(")"));
            System.out.println(title);
            String choices = test.substring(test.indexOf("[")+1,test.indexOf("]"));
            String second = test.substring(test.indexOf("<")+1,test.indexOf(">"));
            choices = choices.replace(",","");
            String[] choi = choices.split(" ");
            System.out.println(Arrays.toString(choi));
            String serverid = event.getGuild().getId();
            boolean gameInProg = false;
            for (int x = 0; x < pollHolders[0].getPolls().size(); x++) {


                if (serverid.equals(pollHolders[0].getPolls().get(x).getServerID())) {

                    gameInProg = true;


                }
            }
            if(!gameInProg){

                pollHolders[0].getPolls().add(new Poll(event.getGuild().getId()));
                pollHolders[0].getPolls().get(pollHolders[0].getPolls().size()-1).setUpChoices(choi.length);
                pollHolders[0].getPolls().get(pollHolders[0].getPolls().size()-1).setQuestion(title);
                String build = "\n\t";
             for(int x=0;x<choi.length;x++){
                 build+="**"+(x+1)+")** " + choi[x]+"\n\t";
                 pollHolders[0].getPolls().get(pollHolders[0].getPolls().size()-1).getChoi().add(choi[x]);

             }

                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("[Poll] " + title+" \n"+second +" seconds to vote!","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                        .setDescription(build)
                        .build()).queue();
                try {
                    FileOutputStream fileOut = new FileOutputStream("poll.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(pollHolders[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }

                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            FileInputStream fileIn = new FileInputStream("poll.ser");
                            ObjectInputStream in = new ObjectInputStream(fileIn);
                            pollHolders[0] = (PollHolder) in.readObject();
                            in.close();
                            fileIn.close();
                        } catch (IOException i) {
                            i.printStackTrace();
                            return;
                        } catch (ClassNotFoundException c) {
                            c.printStackTrace();
                            return;
                        }

                        int ind = 0;
                        String server = event.getGuild().getId();
                        for (int x = 0; x < pollHolders[0].getPolls().size(); x++) {


                            if (server.equals(pollHolders[0].getPolls().get(x).getServerID())) {

                                ind = x;
                                break;


                            }
                        }

                        if(pollHolders[0].getPolls().get(ind).getUsers().size()!=0){

                            int total = 0;
                            for(int x=0;x<pollHolders[0].getPolls().get(ind).getChoices().length;x++){
                                total+=pollHolders[0].getPolls().get(ind).getChoices()[x];
                            }
String build = "";
                            for(int x=0;x<pollHolders[0].getPolls().get(ind).getChoices().length;x++){
                                BigDecimal a = new BigDecimal((((double)pollHolders[0].getPolls().get(ind).getChoices()[x]/total*100)));
                                BigDecimal b = a.setScale(2, RoundingMode.DOWN);
                                build+=pollHolders[0].getPolls().get(ind).getChoi().get(x) + " | " + (b +"%\n\t");
                            }


                            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("[Poll Results] " + pollHolders[0].getPolls().get(ind).getQuestion(),"https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                                    .setDescription(build)
                                    .build()).queue();
                            pollHolders[0].getPolls().remove(ind);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("poll.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(pollHolders[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }

                        }else{
                            pollHolders[0].getPolls().remove(ind);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("poll.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(pollHolders[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }

                            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No one voted!","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")

                                    .build()).queue();
                            //no one voted

                        }


                    }
                },Integer.parseInt(second)*1000);




            }else{

                //error game in prog
                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("A poll is already in progress.","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")

                        .build()).queue();
            }



        }else if(code.length==2&&code[1].equals("end")){

            String server = event.getGuild().getId();
            int ind = -1;


            if(ind!=-1) {
                for (int w = 0; w < pollHolders[0].getPolls().size(); w++) {


                    if (server.equals(pollHolders[0].getPolls().get(w).getServerID())) {

                        ind = w;
                        if(pollHolders[0].getPolls().get(ind).getUsers().size()!=0){

                            int total = 0;
                            for(int x=0;x<pollHolders[0].getPolls().get(ind).getChoices().length;x++){
                                total+=pollHolders[0].getPolls().get(ind).getChoices()[x];
                            }
                            String build = "";
                            for(int x=0;x<pollHolders[0].getPolls().get(ind).getChoices().length;x++){
                                BigDecimal a = new BigDecimal((((double)pollHolders[0].getPolls().get(ind).getChoices()[x]/total*100)));
                                BigDecimal b = a.setScale(2, RoundingMode.DOWN);
                                build+=pollHolders[0].getPolls().get(ind).getChoi().get(x) + " | **" + (b +"%**\n\t");
                            }




                            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("[Poll Results] " + pollHolders[0].getPolls().get(ind).getQuestion(),"https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                                   .setDescription(build)
                                    .build()).queue();

                            pollHolders[0].getPolls().remove(ind);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("poll.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(pollHolders[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }

                        }else{
                            pollHolders[0].getPolls().remove(ind);
                            try {
                                FileOutputStream fileOut = new FileOutputStream("poll.ser");
                                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                                out.writeObject(pollHolders[0]);
                                out.close();
                                fileOut.close();
                            } catch (IOException ww) {
                                ww.printStackTrace();
                            }

                            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No one voted!","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                                    .build()).queue();
                            //no one voted

                        }
                        break;


                    }
                }

            }else{

                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No active polls.","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                        .addField("Creating a poll","```ypoll (title) [list of options] <time limit (sec)>```",false)
                        .build()).queue();
                try {
                    FileOutputStream fileOut = new FileOutputStream("poll.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(pollHolders[0]);
                    out.close();
                    fileOut.close();
                } catch (IOException ww) {
                    ww.printStackTrace();
                }
            }



        }else if(code.length == 2){

            try{
                int num = Integer.parseInt(code[1]);
                int ind = -1;
                String server = event.getGuild().getId();
                for (int x = 0; x < pollHolders[0].getPolls().size(); x++) {


                    if (server.equals(pollHolders[0].getPolls().get(x).getServerID())) {

                        ind = x;
                        break;


                    }
                }

                if(ind!=-1){
                    if(pollHolders[0].getPolls().get(ind).getUsers().contains(event.getAuthor().getId())){



                        event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You cannot vote more than once","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                                .build()).queue();

                    }else{

                        pollHolders[0].getPolls().get(ind).getUsers().add(event.getAuthor().getId());
                        pollHolders[0].getPolls().get(ind).getChoices()[num-1] =pollHolders[0].getPolls().get(ind).getChoices()[num-1]+1;
                        try {
                            FileOutputStream fileOut = new FileOutputStream("poll.ser");
                            ObjectOutputStream out = new ObjectOutputStream(fileOut);
                            out.writeObject(pollHolders[0]);
                            out.close();
                            fileOut.close();
                        } catch (IOException ww) {
                            ww.printStackTrace();
                        }

                    }




                }else{


                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No active polls.","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")
                            .addField("Creating a poll","```ypoll (title) [list of options] <time limit (sec)>```",false)
                            .build()).queue();

                }


            }catch (Exception e){


                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Vote has to be an integer","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png","https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png")

                        .build()).queue();
            }



        }else{
            if(!event.getAuthor().isBot()){
                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Poll").setThumbnail("https://images.g2crowd.com/uploads/product/image/large_detail/large_detail_1519671338/poll-everywhere.png").addField(
                        "Creating","```ypoll (title) [list of options separated by commas] <time limit (sec)>```",true)
                        .addField("Example","```ypoll (Do you enjoy using YetiBot?) [Yes, Sure, Absolutely] <20>```",true)
                        .addField("Voting","```ypoll <vote number>```",true)
                        .addField("Ending","```ypoll end```",true)
                        .build()).queue();
            }



        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                event.getMessage().delete().queue();

            }
        }, 5000);

    }
}
