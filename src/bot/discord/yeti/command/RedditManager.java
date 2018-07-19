package bot.discord.yeti.command;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class RedditManager {
    public static void run(MessageReceivedEvent event) throws IOException {
        //!reddit funny
        String[] code = event.getMessage().getContentRaw().split(" ");
        if(code.length==2){
            String subReddit = code[1];
            String search = "https://www.reddit.com/r/"+subReddit+"/hot.json?limit=100";

            int ran = (int)(Math.random()*100)+1;
            URL url = null;
            try {
                url = new URL(search);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            BufferedReader br = null;
            URLConnection hc = null;
                hc = url.openConnection();

            hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
            //    br = new BufferedReader(new InputStreamReader());
            final InputStream in = new BufferedInputStream(hc.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            JSONObject arr = new JSONObject(reader.readLine());
            try{
                String title = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("title").toString();
                String imgSrc = arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("url").toString();
                String linkSrc = "https://www.reddit.com"+arr.getJSONObject("data").getJSONArray("children").getJSONObject(ran).getJSONObject("data").get("permalink").toString();
                in.close();
                EmbedBuilder eb = new EmbedBuilder();
                eb.setTitle(title,linkSrc);
                eb.setImage(imgSrc);
                eb.setColor(new Color(255, 102, 102));
                event.getChannel().sendMessage(eb.build()).queue();

            }catch (Exception ea){

                event.getChannel().sendMessage("Error subreddit does not exit").queue();

            }





        }else{



        }




    }
}
