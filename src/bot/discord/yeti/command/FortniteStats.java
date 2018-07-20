package bot.discord.yeti.command;

import bot.discord.yeti.fortniteAPI.AllStats;
import bot.discord.yeti.fortniteAPI.StatsAsyncTask;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FortniteStats {
    public static void run(Message msg, MessageReceivedEvent event) throws IOException {
        if (msg.getContentRaw().toLowerCase().equals("!fortnitestats")) {
            msg.getChannel().sendMessage("Format: !fortnitestats %username% (pc/ps4/xb1) [all/solo/duo/squad]").queue();
        } else {
            try {
                String username = msg.getContentRaw().substring(msg.getContentRaw().indexOf("!fortniteStats") + 15);
                if (username.contains("(")) {
                    username = username.substring(1, username.indexOf("(") - 1);
                }
                String platform = "";
                try {
                    platform = msg.getContentRaw().substring(msg.getContentRaw().indexOf("(") + 1, msg.getContentRaw().indexOf(")"));
                } catch (IndexOutOfBoundsException e) {

                }

                String mode = "";
                try {
                    mode = msg.getContentRaw().substring(msg.getContentRaw().indexOf("[") + 1, msg.getContentRaw().indexOf("]"));
                } catch (IndexOutOfBoundsException e) {

                }
                StatsAsyncTask stats = new StatsAsyncTask(username);
                try {
                    AllStats stat = stats.getStats();


                    if (platform.toLowerCase().equals("pc")) {
                        if (!stat.getPc().hasAccount()) {
                            msg.getChannel().sendMessage("Error no PC account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(stat.getPc().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(stat.getPc().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(stat.getPc().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(stat.getPc().squadStat() + "").queue();
                            }

                        }

                    } else if (platform.toLowerCase().equals("ps4")) {
                        if (!stat.getPs4().hasAccount()) {
                            msg.getChannel().sendMessage("Error no PS4 account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(stat.getPs4().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(stat.getPs4().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(stat.getPs4().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(stat.getPs4().squadStat() + "").queue();
                            }

                        }
                    } else if (platform.toLowerCase().equals("xb1")) {
                        if (!stat.getXb1().hasAccount()) {
                            msg.getChannel().sendMessage("Error no XB1 account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(stat.getXb1().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(stat.getXb1().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(stat.getXb1().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(stat.getXb1().squadStat() + "").queue();
                            } else {
                                msg.getChannel().sendMessage("Error invalid gamemode [all/solo/duo/squad]").queue();
                            }
                        }
                    } else {
                        msg.getChannel().sendMessage("Error invalid platform (pc/ps4/xb1)").queue();
                    }
                } catch (Exception e) {
                    msg.getChannel().sendMessage("Error invalid username").queue();


                }


            } catch (Exception e) {
                msg.getChannel().sendMessage("Format !fortnitestats %username% (pc/ps4/xb1) [all/solo/duo/squad]").queue();

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
