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
        String[] code = msg.getContentRaw().toLowerCase().split(" ");
        if (code.length>1) {


            String username = "";
            String plat = "";
            String mode = "";


            if(msg.getContentRaw().toLowerCase().contains("pc")){
                plat = "pc";
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("pc"));

            }else if(msg.getContentRaw().toLowerCase().contains("ps4")){
                plat = "ps4";
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("ps4"));

            }else if(msg.getContentRaw().toLowerCase().contains("xb1")){
                plat = "xb1";
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("xb1"));

            }

            if(msg.getContentRaw().toLowerCase().contains("all")){
                mode = "all";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("all"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("solo")){
                mode = "solo";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("solo"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("duo")){
                mode = "duo";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("duo"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("squad")){
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15,msg.getContentRaw().toLowerCase().indexOf("squad"));


                }
                mode = "squad";

            }

            if(username.equals("")){
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("!fortnitestats") + 15);


            }

            if(mode.equals("")&&plat.equals("")){
                StatsAsyncTask stats = new StatsAsyncTask(username);
                try {
                    AllStats stat = stats.getStats();
                    int pc = (int)stat.getPc().getBr_score_pc_m0_p2();
                    int ps4 = (int)stat.getPs4().getBr_score_ps4_m0_p2();
                    int xb1 = (int)stat.getXb1().getBr_score_xb1_m0_p2();
                    int max =  Math.max(Math.max(pc,ps4),xb1);
                    String maxPlat = "PC";
                    if(max==ps4){
                        maxPlat = "PS4";
                    }else if(max==xb1){
                        maxPlat = "XB1";

                    }

                        if (maxPlat.toLowerCase().equals("pc")) {
                            if (!stat.getPc().hasAccount()) {
                                msg.getChannel().sendMessage("No PC account found for this username").queue();
                            } else {

                                    msg.getChannel().sendMessage(username+"'s Lifetime Stats\n"+stat.getPc().lifeTime() + "").queue();


                            }

                        } else if (maxPlat.toLowerCase().equals("ps4")) {
                            if (!stat.getPs4().hasAccount()) {
                                msg.getChannel().sendMessage("No PS4 account found for this username").queue();
                            } else {

                                    msg.getChannel().sendMessage(username+"'s Lifetime Stats\n"+stat.getPs4().lifeTime() + "").queue();


                            }
                        } else if (maxPlat.toLowerCase().equals("xb1")) {
                            if (!stat.getXb1().hasAccount()) {
                                msg.getChannel().sendMessage("No XB1 account found for this username").queue();
                            } else {

                                    msg.getChannel().sendMessage(username+"'s Lifetime Stats\n"+stat.getXb1().lifeTime() + "").queue();

                            }
                    } else {
                        msg.getChannel().sendMessage("Cannot find account: make sure your account is linked with Epic Games and you are searching using your Epic Games' username").queue();
                    }
                } catch (Exception e) {
                    msg.getChannel().sendMessage("Cannot find account").queue();


                }


            }else if(mode.equals("")) {
                StatsAsyncTask stats = new StatsAsyncTask(username);
                try {
                    AllStats stat = stats.getStats();
                    if (plat.toLowerCase().equals("pc")) {
                        if (!stat.getPc().hasAccount()) {
                            msg.getChannel().sendMessage("No PC account found for this username").queue();
                        } else {
                            msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getPc().lifeTime() + "").queue();


                        }

                    } else if (plat.toLowerCase().equals("ps4")) {
                        if (!stat.getPs4().hasAccount()) {
                            msg.getChannel().sendMessage("No PS4 account found for this username").queue();
                        } else {
                            msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getPs4().lifeTime() + "").queue();


                        }
                    } else if (plat.toLowerCase().equals("xb1")) {
                        if (!stat.getXb1().hasAccount()) {
                            msg.getChannel().sendMessage("No XB1 account found for this username").queue();
                        } else {

                            msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getXb1().lifeTime() + "").queue();

                        }


                    }
                        } catch (Exception e) {
                            msg.getChannel().sendMessage("Cannot find account").queue();


                        }


                    } else if (plat.equals("")) {

                StatsAsyncTask stats = new StatsAsyncTask(username);
                try {
                    AllStats stat = stats.getStats();
                    int pc = (int) stat.getPc().getBr_score_pc_m0_p2();
                    int ps4 = (int) stat.getPs4().getBr_score_ps4_m0_p2();
                    int xb1 = (int) stat.getXb1().getBr_score_xb1_m0_p2();
                    int max = Math.max(Math.max(pc, ps4), xb1);
                    String maxPlat = "PC";
                    if (max == ps4) {
                        maxPlat = "PS4";
                    } else if (max == xb1) {
                        maxPlat = "XB1";

                    }
                    if (maxPlat.toLowerCase().equals("pc")) {
                        if (!stat.getPc().hasAccount()) {
                            msg.getChannel().sendMessage("No PC account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getPc().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(username + "'s Solo Stats\n" + stat.getPc().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(username + "'s Duo Stats\n" + stat.getPc().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(username + "'s Squad Stats\n" + stat.getPc().squadStat() + "").queue();
                            }

                        }

                    } else if (maxPlat.toLowerCase().equals("ps4")) {
                        if (!stat.getPs4().hasAccount()) {
                            msg.getChannel().sendMessage("No PS4 account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getPs4().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(username + "'s Solo Stats\n" + stat.getPs4().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(username + "'s Duo Stats\n" + stat.getPs4().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(username + "'s Squad Stats\n" + stat.getPs4().squadStat() + "").queue();
                            }

                        }
                    } else if (maxPlat.toLowerCase().equals("xb1")) {
                        if (!stat.getXb1().hasAccount()) {
                            msg.getChannel().sendMessage("No XB1 account found for this username").queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(username + "'s Lifetime Stats\n" + stat.getXb1().lifeTime() + "").queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {
                                msg.getChannel().sendMessage(username + "'s Solo Stats\n" + stat.getXb1().soloStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(username + "'s Duo Stats\n" + stat.getXb1().duoStat() + "").queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(username + "'s Squad Stats\n" + stat.getXb1().squadStat() + "").queue();
                            }
                        }
                    } else {


                    }
                }catch (Exception e){
                    msg.getChannel().sendMessage("Cannot find account").queue();

                }
            }




                    }else{

            msg.getChannel().sendMessage("Format: y!fortnitestats username [optional: pc, ps4, xb1]  [optional: all ,solo, duo, squad]").queue();

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
