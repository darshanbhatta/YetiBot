package bot.discord.yeti.command;

import bot.discord.yeti.fortniteAPI.AllStats;
import bot.discord.yeti.fortniteAPI.StatsAsyncTask;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
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
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("pc"));

            }else if(msg.getContentRaw().toLowerCase().contains("ps4")){
                plat = "ps4";
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("ps4"));

            }else if(msg.getContentRaw().toLowerCase().contains("xb1")){
                plat = "xb1";
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("xb1"));

            }

            if(msg.getContentRaw().toLowerCase().contains("all")){
                mode = "all";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("all"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("solo")){
                mode = "solo";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("solo"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("duo")){
                mode = "duo";
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("duo"));


                }
            }else if(msg.getContentRaw().toLowerCase().contains("squad")){
                if(username.equals("")){
                    username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7,msg.getContentRaw().toLowerCase().indexOf("squad"));


                }
                mode = "squad";

            }

            if(username.equals("")){
                username = msg.getContentRaw().substring(msg.getContentRaw().toLowerCase().indexOf("fstats") + 7);


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


                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .setDescription("No PC account found for this username")
                                        .build()).queue();

                            } else {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getPc().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getPc().life1053(),true)
                                        .addField("Top 25/12/6",stat.getPc().life25126(),true)
                                        .addField("Kills",stat.getPc().lifeKills(),true)
                                        .addField("K/D",stat.getPc().lifekd(),true)
                                        .addField("K/match",stat.getPc().lifekMatch(),true)
                                        .addField("Matches",stat.getPc().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getPc().getWp_pc_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();



                            }

                        } else if (maxPlat.toLowerCase().equals("ps4")) {
                            if (!stat.getPs4().hasAccount()) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .setDescription("No PS4 account found for this username")
                                        .build()).queue();
                            } else {

                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getPs4().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getPs4().life1053(),true)
                                        .addField("Top 25/12/6",stat.getPs4().life25126(),true)
                                        .addField("Kills",stat.getPs4().lifeKills(),true)
                                        .addField("K/D",stat.getPs4().lifekd(),true)
                                        .addField("K/match",stat.getPs4().lifekMatch(),true)
                                        .addField("Matches",stat.getPs4().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getPs4().getWp_ps4_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();




                            }
                        } else if (maxPlat.toLowerCase().equals("xb1")) {
                            if (!stat.getXb1().hasAccount()) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .setDescription("No PS4 account found for this username")
                                        .build()).queue();
                            } else {

                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getXb1().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getXb1().life1053(),true)
                                        .addField("Top 25/12/6",stat.getXb1().life25126(),true)
                                        .addField("Kills",stat.getXb1().lifeKills(),true)
                                        .addField("K/D",stat.getXb1().lifekd(),true)
                                        .addField("K/match",stat.getXb1().lifekMatch(),true)
                                        .addField("Matches",stat.getXb1().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getXb1().getWp_xb1_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();

                            }
                    } else {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("Cannot find account: make sure your account is linked with Epic Games and you are searching using your Epic Games' username")
                                    .build()).queue();

                    }
                } catch (Exception e) {

                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                            .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                            .setDescription("Cannot find account")
                            .build()).queue();


                }


            }else if(mode.equals("")) {
                StatsAsyncTask stats = new StatsAsyncTask(username);
                try {
                    AllStats stat = stats.getStats();
                    if (plat.toLowerCase().equals("pc")) {
                        if (!stat.getPc().hasAccount()) {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No PC account found for this username")
                                    .build()).queue();

                        } else {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .addField("Wins",stat.getPc().lifeWins(),true)
                                    .addField("Top 10/5/3",stat.getPc().life1053(),true)
                                    .addField("Top 25/12/6",stat.getPc().life25126(),true)
                                    .addField("Kills",stat.getPc().lifeKills(),true)
                                    .addField("K/D",stat.getPc().lifekd(),true)
                                    .addField("K/match",stat.getPc().lifekMatch(),true)
                                    .addField("Matches",stat.getPc().lifeMatch(),true)
                                    .addField("Win %",Math.round(stat.getPc().getWp_pc_all()*100 * 100.0) / 100.0+"",true)
                                    .build()).queue();

                        }

                    } else if (plat.toLowerCase().equals("ps4")) {
                        if (!stat.getPs4().hasAccount()) {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No PS4 account found for this username")
                                    .build()).queue();
                        } else {

                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .addField("Wins",stat.getPs4().lifeWins(),true)
                                    .addField("Top 10/5/3",stat.getPs4().life1053(),true)
                                    .addField("Top 25/12/6",stat.getPs4().life25126(),true)
                                    .addField("Kills",stat.getPs4().lifeKills(),true)
                                    .addField("K/D",stat.getPs4().lifekd(),true)
                                    .addField("K/match",stat.getPs4().lifekMatch(),true)
                                    .addField("Matches",stat.getPs4().lifeMatch(),true)
                                    .addField("Win %",Math.round(stat.getPs4().getWp_ps4_all()*100 * 100.0) / 100.0+"",true)
                                    .build()).queue();


                        }
                    } else if (plat.toLowerCase().equals("xb1")) {
                        if (!stat.getXb1().hasAccount()) {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No PS4 account found for this username")
                                    .build()).queue();
                        } else {

                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .addField("Wins",stat.getXb1().lifeWins(),true)
                                    .addField("Top 10/5/3",stat.getXb1().life1053(),true)
                                    .addField("Top 25/12/6",stat.getXb1().life25126(),true)
                                    .addField("Kills",stat.getXb1().lifeKills(),true)
                                    .addField("K/D",stat.getXb1().lifekd(),true)
                                    .addField("K/match",stat.getXb1().lifekMatch(),true)
                                    .addField("Matches",stat.getXb1().lifeMatch(),true)
                                    .addField("Win %",Math.round(stat.getXb1().getWp_xb1_all()*100 * 100.0) / 100.0+"",true)
                                    .build()).queue();


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
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No PC account found for this username")
                                    .build()).queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getPc().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getPc().life1053(),true)
                                        .addField("Top 25/12/6",stat.getPc().life25126(),true)
                                        .addField("Kills",stat.getPc().lifeKills(),true)
                                        .addField("K/D",stat.getPc().lifekd(),true)
                                        .addField("K/match",stat.getPc().lifekMatch(),true)
                                        .addField("Matches",stat.getPc().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getPc().getWp_pc_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {

                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Solo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPc().getBr_placetop1_pc_m0_p2()+"",true)
                                        .addField("Top 10",(int)stat.getPc().getBr_placetop10_pc_m0_p2()+"",true)
                                        .addField("Top 25",(int)stat.getPc().getBr_placetop25_pc_m0_p2()+"",true)
                                        .addField("Kills",(int)stat.getPc().getBr_kills_pc_m0_p2()+"",true)
                                        .addField("K/D",Math.round(stat.getPc().getKd_pc_p2() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPc().getKm_pc_p2() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPc().getBr_matchesplayed_pc_m0_p2()+"",true)
                                        .addField("Win %",Math.round(stat.getPc().getWp_pc_p2()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPc().getBr_placetop1_pc_m0_p10()+"",true)
                                        .addField("Top 5",(int)stat.getPc().getBr_placetop5_pc_m0_p10()+"",true)
                                        .addField("Top 12",(int)stat.getPc().getBr_placetop12_pc_m0_p10()+"",true)
                                        .addField("Kills",(int)stat.getPc().getBr_kills_pc_m0_p10()+"",true)
                                        .addField("K/D",Math.round(stat.getPc().getKd_pc_p10() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPc().getKm_pc_p10() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPc().getBr_matchesplayed_pc_m0_p10()+"",true)
                                        .addField("Win %",Math.round(stat.getPc().getWp_pc_p10()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPc().getBr_placetop1_pc_m0_p9()+"",true)
                                        .addField("Top 3",(int)stat.getPc().getBr_placetop3_pc_m0_p9()+"",true)
                                        .addField("Top 6",(int)stat.getPc().getBr_placetop6_pc_m0_p9()+"",true)
                                        .addField("Kills",(int)stat.getPc().getBr_kills_pc_m0_p9()+"",true)
                                        .addField("K/D",Math.round(stat.getPc().getKd_pc_p9() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPc().getKm_pc_p9() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPc().getBr_matchesplayed_pc_m0_p9()+"",true)
                                        .addField("Win %",Math.round(stat.getPc().getWp_pc_p9()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();

                            }

                        }

                    } else if (maxPlat.toLowerCase().equals("ps4")) {
                        if (!stat.getPs4().hasAccount()) {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No PS4 account found for this username")
                                    .build()).queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getPs4().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getPs4().life1053(),true)
                                        .addField("Top 25/12/6",stat.getPs4().life25126(),true)
                                        .addField("Kills",stat.getPs4().lifeKills(),true)
                                        .addField("K/D",stat.getPs4().lifekd(),true)
                                        .addField("K/match",stat.getPs4().lifekMatch(),true)
                                        .addField("Matches",stat.getPs4().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getPs4().getWp_ps4_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {

                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Solo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPs4().getBr_placetop1_ps4_m0_p2()+"",true)
                                        .addField("Top 10",(int)stat.getPs4().getBr_placetop10_ps4_m0_p2()+"",true)
                                        .addField("Top 25",(int)stat.getPs4().getBr_placetop25_ps4_m0_p2()+"",true)
                                        .addField("Kills",(int)stat.getPs4().getBr_kills_ps4_m0_p2()+"",true)
                                        .addField("K/D",Math.round(stat.getPs4().getKd_ps4_p2() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPs4().getKm_ps4_p2() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPs4().getBr_matchesplayed_ps4_m0_p2()+"",true)
                                        .addField("Win %",Math.round(stat.getPs4().getWp_ps4_p2()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPs4().getBr_placetop1_ps4_m0_p10()+"",true)
                                        .addField("Top 5",(int)stat.getPs4().getBr_placetop5_ps4_m0_p10()+"",true)
                                        .addField("Top 12",(int)stat.getPs4().getBr_placetop12_ps4_m0_p10()+"",true)
                                        .addField("Kills",(int)stat.getPs4().getBr_kills_ps4_m0_p10()+"",true)
                                        .addField("K/D",Math.round(stat.getPs4().getKd_ps4_p10() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPs4().getKm_ps4_p10() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPs4().getBr_matchesplayed_ps4_m0_p10()+"",true)
                                        .addField("Win %",Math.round(stat.getPs4().getWp_ps4_p10()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getPs4().getBr_placetop1_ps4_m0_p9()+"",true)
                                        .addField("Top 3",(int)stat.getPs4().getBr_placetop3_ps4_m0_p9()+"",true)
                                        .addField("Top 6",(int)stat.getPs4().getBr_placetop6_ps4_m0_p9()+"",true)
                                        .addField("Kills",(int)stat.getPs4().getBr_kills_ps4_m0_p9()+"",true)
                                        .addField("K/D",Math.round(stat.getPs4().getKd_ps4_p9() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getPs4().getKm_ps4_p9() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getPs4().getBr_matchesplayed_ps4_m0_p9()+"",true)
                                        .addField("Win %",Math.round(stat.getPs4().getWp_ps4_p9()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();

                            }

                        }
                    } else if (maxPlat.toLowerCase().equals("xb1")) {
                        if (!stat.getXb1().hasAccount()) {
                            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                    .setDescription("No XB1 account found for this username")
                                    .build()).queue();
                        } else {
                            if (mode.toLowerCase().equals("all")) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Lifetime Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",stat.getXb1().lifeWins(),true)
                                        .addField("Top 10/5/3",stat.getXb1().life1053(),true)
                                        .addField("Top 25/12/6",stat.getXb1().life25126(),true)
                                        .addField("Kills",stat.getXb1().lifeKills(),true)
                                        .addField("K/D",stat.getXb1().lifekd(),true)
                                        .addField("K/match",stat.getXb1().lifekMatch(),true)
                                        .addField("Matches",stat.getXb1().lifeMatch(),true)
                                        .addField("Win %",Math.round(stat.getXb1().getWp_xb1_all()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("solo"))) {

                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Solo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getXb1().getBr_placetop1_xb1_m0_p2()+"",true)
                                        .addField("Top 10",(int)stat.getXb1().getBr_placetop10_xb1_m0_p2()+"",true)
                                        .addField("Top 25",(int)stat.getXb1().getBr_placetop25_xb1_m0_p2()+"",true)
                                        .addField("Kills",(int)stat.getXb1().getBr_kills_xb1_m0_p2()+"",true)
                                        .addField("K/D",Math.round(stat.getXb1().getKd_xb1_p2() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getXb1().getKm_xb1_p2() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getXb1().getBr_matchesplayed_xb1_m0_p2()+"",true)
                                        .addField("Win %",Math.round(stat.getXb1().getWp_xb1_p2()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("duo"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getXb1().getBr_placetop1_xb1_m0_p10()+"",true)
                                        .addField("Top 5",(int)stat.getXb1().getBr_placetop5_xb1_m0_p10()+"",true)
                                        .addField("Top 12",(int)stat.getXb1().getBr_placetop12_xb1_m0_p10()+"",true)
                                        .addField("Kills",(int)stat.getXb1().getBr_kills_xb1_m0_p10()+"",true)
                                        .addField("K/D",Math.round(stat.getXb1().getKd_xb1_p10() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getXb1().getKm_xb1_p10() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getXb1().getBr_matchesplayed_xb1_m0_p10()+"",true)
                                        .addField("Win %",Math.round(stat.getXb1().getWp_xb1_p10()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();
                            } else if ((mode.toLowerCase().equals("squad"))) {
                                msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle(username+"'s Duo Stats")
                                        .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                                        .addField("Wins",(int)stat.getXb1().getBr_placetop1_xb1_m0_p9()+"",true)
                                        .addField("Top 3",(int)stat.getXb1().getBr_placetop3_xb1_m0_p9()+"",true)
                                        .addField("Top 6",(int)stat.getXb1().getBr_placetop6_xb1_m0_p9()+"",true)
                                        .addField("Kills",(int)stat.getXb1().getBr_kills_xb1_m0_p9()+"",true)
                                        .addField("K/D",Math.round(stat.getXb1().getKd_xb1_p9() * 100.0) / 100.0+"",true)
                                        .addField("K/match",Math.round(stat.getXb1().getKm_xb1_p9() * 100.0) / 100.0+"",true)
                                        .addField("Matches",(int)stat.getXb1().getBr_matchesplayed_xb1_m0_p9()+"",true)
                                        .addField("Win %",Math.round(stat.getXb1().getWp_xb1_p9()*100 * 100.0) / 100.0+"",true)
                                        .build()).queue();

                            }
                        }
                    } else {


                    }
                }catch (Exception e){
                    msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0xa442f4)).setTitle("Fortnite Stats")
                            .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                            .setDescription("Cannot find account")
                            .build()).queue();


                }
            }




                    }else{

            msg.getChannel().sendMessage("Format: ").queue();

            msg.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Create Text Channel")
                    .setThumbnail("http://www.stickpng.com/assets/images/5b43b818e99939b4572e32ab.png")
                    .addField("Format","```yfstats username [optional: pc, ps4, xb1]  [optional: all ,solo, duo, squad]```",true)
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
