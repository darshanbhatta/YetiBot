package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Role {
    public static void run(MessageReceivedEvent e) {
        String code = e.getMessage().getContentRaw().toLowerCase();
        String[] co = code.split(" ");

        //!role add name <> []

        if(co.length == 1){


            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Role Commands","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")
                    .addField("Create a role","```yrole create (name) <Opt: Permission> [Opt: Hex Color]```",false)
                    .addField("Add user to a role","```yrole add @username [role names separated by commas]```",false)
                    .addField("Remove user from a role","```yrole remove @username [role names separated by commas]```",false)
                    .build()).queue();
        }
        if (co.length >= 3 && co[1].equals("create") && !code.contains("<") && !code.contains(">") && !code.contains("[") && !code.contains("]")) {
            if (e.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                System.out.println(code);

                e.getGuild().getController().createRole().setName(e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13)).queue();

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Created role \"" + (e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13).trim()) + "\"","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                            .build()).queue();



            } else {

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You do not have permission.","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();
            }


        } else if (co.length >= 3 && co[1].equals("create") && code.contains("<") && code.contains(">") && code.contains("[") && code.contains("]")) {

            if (e.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                System.out.println(code);
                String allPerm = code.substring(code.indexOf("<") + 1, code.indexOf(">"));
                String colorID = code.substring(code.indexOf("[") + 1, code.indexOf("]"));
                colorID = colorID.replace("#", "").trim();
                allPerm = allPerm.replace(" ", "");
                String allPer[] = allPerm.split(",");
                int[] allPerNum = new int[allPer.length];
                boolean isVaild = true;
                for (int x = 0; x < allPer.length; x++) {
                    try {

                        allPerNum[x] = Integer.parseInt(allPer[x]);

                    } catch (Exception woah) {
                        isVaild = false;
                        break;
                    }

                }
                if (isVaild) {
                    Permission[] permissions = new Permission[allPerNum.length];
                    for (int x = 0; x < permissions.length; x++) {
                        permissions[x] = Permission.getFromOffset(allPerNum[x]);
                    }
                    try {
                        int[] rgb = getRGB(colorID);
                        e.getGuild().getController().createRole().setName(e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("<"))).setPermissions(permissions).setColor(new Color(rgb[0], rgb[1], rgb[2])).queue();

                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Created role \"" + (e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("<")).trim()) + "\"","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                                .build()).queue();
                    } catch (Exception aa) {



                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Invalid hex color: e.g. #fffff or ffffff","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                                .build()).queue();

                    }


                } else {

                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Invalid permission code, try yrole perm to see all permission codes","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                            .build()).queue();



                }


            } else {
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You do not have permission.","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();
            }


        } else if (co.length >= 3 && co[1].equals("create") && code.contains("<") && code.contains(">")) {
            if (e.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                System.out.println(code);
                String allPerm = code.substring(code.indexOf("<") + 1, code.indexOf(">"));
                allPerm = allPerm.replace(" ", "");
                String allPer[] = allPerm.split(",");
                int[] allPerNum = new int[allPer.length];
                boolean isVaild = true;
                for (int x = 0; x < allPer.length; x++) {
                    try {

                        allPerNum[x] = Integer.parseInt(allPer[x]);

                    } catch (Exception woah) {
                        isVaild = false;
                        break;
                    }

                }
                if (isVaild) {
                    Permission[] permissions = new Permission[allPerNum.length];
                    for (int x = 0; x < permissions.length; x++) {
                        permissions[x] = Permission.getFromOffset(allPerNum[x]);
                    }

                    e.getGuild().getController().createRole().setName(e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("<"))).setPermissions(permissions).queue();


                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Created role \"" + (e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("<")).trim()) + "\"","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                            .build()).queue();

                } else {


                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Invalid permission code, try yrole perm to see all permission codes","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                            .build()).queue();


                }


            } else {
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You do not have permission.","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();
            }


        } else if (co.length >= 3 && co[1].equals("create") && code.contains("[") && code.contains("]")) {

            if (e.getMember().hasPermission(Permission.MANAGE_ROLES)) {
                System.out.println(code);
                String colorID = code.substring(code.indexOf("[") + 1, code.indexOf("]"));
                colorID = colorID.replace("#", "").trim();


                try {
                    int[] rgb = getRGB(colorID);
                    e.getGuild().getController().createRole().setName(e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("["))).setColor(new Color(rgb[0], rgb[1], rgb[2])).queue();

                        e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Created role \"" + (e.getMessage().getContentRaw().substring(code.indexOf("yrole create ") + 13,code.indexOf("[")).trim()) + "\"","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                                .build()).queue();

                } catch (Exception aa) {


                    e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Invalid hex color: e.g. #fffff or ffffff","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                            .build()).queue();

                }


        } else {


                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("You do not have permission.","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();

        }



        }else if(co.length==2&&co[1].equals("perm")){
            String permission = "0  - \"Create Instant Invite\"\n" +
                    "1  - \"Kick Members\"\n" +
                    "2  - \"Ban Members\"\n" +
                    "3  - \"Administrator\"\n" +
                    "4  - \"Manage Channels\"\n" +
                    "5  - \"Manage Server\"\n" +
                    "6  - \"Add Reactions\"\n" +
                    "7  - \"View Audit Logs\"\n" +
                    "10 - \"Read Text Channels & See Voice Channels\"\n" +
                    "11 - \"Send Messages\"\n" +
                    "12 - \"Send TTS Messages\"\n" +
                    "13 - \"Manage Messages\"\n" +
                    "14 - \"Embed Links\"\n" +
                    "15 - \"Attach Files\"\n" +
                    "16 - \"Read History\"\n" +
                    "17 - \"Mention Everyone\"\n" +
                    "18 - \"Use External Emojis\"\n" +
                    "20 - \"Connect\"\n" +
                    "21 - \"Speak\"\n" +
                    "22 - \"Mute Members\"\n" +
                    "23 - \"Deafen Members\"\n" +
                    "24 - \"Move Members\"\n" +
                    "25 - \"Use Voice Activity\"\n" +
                    "26 - \"Change Nickname\"\n" +
                    "27 - \"Manage Nicknames\"\n" +
                    "28 - \"Manage Roles\"\n" +
                    "29 - \"Manage Webhooks\"\n" +
                    "30 - \"Manage Emojis\"";


            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Type the number corresponding to the permission when adding permissions for roles","https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")
.setDescription(permission)
                    .build()).queue();

        }else if(co.length >= 3 && co[1].equals("add") && code.contains("<") && code.contains(">")){
            ArrayList<Member> users = new ArrayList();
            e.getGuild().getMembers().forEach(m -> users.add(m));
            ArrayList<net.dv8tion.jda.core.entities.Role> roles = new ArrayList();
            String allPerm = e.getMessage().getContentRaw().substring(code.indexOf("[")+1,code.indexOf("]"));
            String username = code.substring(code.indexOf("<")+2,code.indexOf(">"));
            allPerm = allPerm.replace(", ", " ");
            String allPer[] = allPerm.split(",");
            String builder = "";
            System.out.println(allPerm.length());
            for(int x=0;x<allPer.length;x++){
                    builder+=allPer[x]+" ";
                roles.add(e.getJDA().getRolesByName(allPer[x], true).get(0));

            }

            int ind = -1;
            for(int x=0;x<users.size();x++){
                if (username.toLowerCase().contains(users.get(x).getUser().getId().toLowerCase())) {
                    ind = x;
                    break;
                }

            }
            if(ind!=-1){
try{
    e.getGuild().getController().addRolesToMember(users.get(ind),roles ).queue();
    e.getChannel().sendMessage("Successfully added " +users.get(ind).getUser().getName() +  " to "+builder ).queue();
}catch (Exception eae){
    e.getChannel().sendMessage("Error: Bot can't modify a role with higher or equal highest role than itself!" ).queue();

}



            }else{
                e.getChannel().sendMessage("Username not found.").queue();

            }





        }else if(co.length >= 3 && co[1].equals("remove") && code.contains("<") && code.contains(">")){
        ArrayList<Member> users = new ArrayList();
        e.getGuild().getMembers().forEach(m -> users.add(m));
        ArrayList<net.dv8tion.jda.core.entities.Role> roles = new ArrayList();
        String allPerm = e.getMessage().getContentRaw().substring(code.indexOf("[")+1,code.indexOf("]"));
        String username = code.substring(code.indexOf("<")+2,code.indexOf(">"));
        allPerm = allPerm.replace(", ", " ");
        String allPer[] = allPerm.split(",");
        String builder = "";
        for(int x=0;x<allPer.length;x++){
            builder+=allPer[x]+" ";
            roles.add(e.getJDA().getRolesByName(allPer[x], true).get(0));

        }
        int ind = -1;
        for(int x=0;x<users.size();x++){
            if (username.toLowerCase().contains(users.get(x).getUser().getId().toLowerCase())) {
                ind = x;
                break;
            }

        }
        if(ind!=-1){
            try{
                e.getGuild().getController().removeRolesFromMember(users.get(ind),roles ).queue();
                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Successfully removed " +users.get(ind).getUser().getName() +  " from "+builder ,"https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();
            }catch (Exception eae){

                e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Bot can't modify a role with higher or equal highest role than itself!" ,"https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                        .build()).queue();
            }



        }else{
            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("User not found." ,"https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                    .build()).queue();


        }





    }else{
            if(co.length >= 2)

            e.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Invalid Command. See yrole for commands." ,"https://melbournechapter.net/images/responsibility-clipart-role-10.png","https://melbournechapter.net/images/responsibility-clipart-role-10.png")

                    .build()).queue();
        }

        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                e.getMessage().delete().queue();

            }
        }, 5000);


    }
    public static int[] getRGB(final String rgb)
    {
        final int[] ret = new int[3];
        for (int i = 0; i < 3; i++)
        {
            ret[i] = Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
        }
        return ret;
    }
}
