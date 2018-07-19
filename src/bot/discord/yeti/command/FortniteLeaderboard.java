package bot.discord.yeti.command;

import bot.discord.yeti.fortniteAPI.AllStats;
import bot.discord.yeti.fortniteAPI.LeaderFetch;
import bot.discord.yeti.fortniteAPI.LeaderItem;
import bot.discord.yeti.fortniteAPI.StatsAsyncTask;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FortniteLeaderboard {
    public static void run(Message msg, MessageReceivedEvent event) throws IOException {
        String platform = "";
        String[] code = msg.getContentRaw().split(" ");
        System.setProperty("http.agent", "Chrome");
        try {
            platform = code[1];

            String mode = "";
            try {
                mode = code[2];
                int who = 01;
                if (platform.toLowerCase().equals("pc")) {
                    if (mode.toLowerCase().equals("solo")) {
                        who = 11;
                    } else if (mode.toLowerCase().equals("duo")) {
                        who = 21;

                    } else if (mode.toLowerCase().equals("squad")) {
                        who = 31;

                    }

                } else if (platform.toLowerCase().equals("ps4")) {
                    if (mode.toLowerCase().equals("solo")) {
                        who = 12;
                    } else if (mode.toLowerCase().equals("duo")) {
                        who = 22;

                    } else if (mode.toLowerCase().equals("squad")) {
                        who = 32;

                    }

                } else if (platform.toLowerCase().equals("xb1")) {
                    if (mode.toLowerCase().equals("solo")) {
                        who = 13;
                    } else if (mode.toLowerCase().equals("duo")) {
                        who = 23;

                    } else if (mode.toLowerCase().equals("squad")) {
                        who = 33;

                    }

                }
                System.out.println(who);
                LeaderFetch stats = new LeaderFetch(who);


                ArrayList<LeaderItem> leaderBoard = stats.leaderFetch();
                StringBuilder leaderList = new StringBuilder();
                leaderList.append("Fortnite Leaderboard\n\n");
                for (int x = 0; x < 25; x++) {
                    leaderList.append(x + 1 + "  " + leaderBoard.get(x).getName() + "  " + leaderBoard.get(x).getWins() + " Wins  " + leaderBoard.get(x).getUserData().toUpperCase() + "\n");


                }


                msg.getChannel().sendMessage(leaderList.toString()).queue(m->{

                    Timer time = new Timer();
                    time.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            msg.delete().queue();

                        }
                    }, 5000);



                });

            } catch (Exception e) {
                System.out.println(e.toString());
                msg.getChannel().sendMessage("Error invalid format !fortniteLeaderboard %pc/ps4/xb1% %solo/duo/squad%").queue();
            }


        } catch (IndexOutOfBoundsException e) {
            msg.getChannel().sendMessage("Error invalid format !fortniteLeaderboard %pc/ps4/xb1% %solo/duo/squad%").queue();
        }


    }
}
