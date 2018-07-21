package bot.discord.yeti.command;

import bot.discord.yeti.util.music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import org.json.JSONObject;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class MusicManager {
    private final AudioPlayerManager playerManager;
    private final Map<Long, GuildMusicManager> musicManagers;
    private final Map<Long, ArrayList<String>> choiceManager;
private MessageReceivedEvent event;
    public MusicManager(){
        this.musicManagers = new HashMap<>();
        this.choiceManager = new HashMap<>();
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);

    }

    public void run(MessageReceivedEvent event) throws IOException {
        this.event = event;
String[] code = event.getMessage().getContentRaw().split(" ");
if(code.length==1){




}else if(code[1].equals("play")){

    long guildId = Long.parseLong(event.getGuild().getId());
    if(code.length==2&&musicManagers.get(guildId)!=null&&!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){
        unpause();

    } else if(event.getMember().getVoiceState().getChannel()!=null){
        String url;
        url = code[2];
        boolean vaildURL;
            URL urii = null;
            try {
                urii = new URL(url);
                vaildURL = true;
            } catch (MalformedURLException woa) {
                vaildURL = false;
            }

            if(vaildURL){
                loadAndPlay(event.getTextChannel(),url);
            }else{
                String keyword = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("play")+4).trim();
                keyword = keyword.replace(" ", "+");

                String search = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&part=contentDetails&order=relevance&type=video&q=" + keyword + "&key=AIzaSyDNZ8_KqjQ7L09kGS5NzCwIqqgWNiDJ5fg";

                URL uri = null;
                try {
                    uri = new URL(search);
                } catch (MalformedURLException wa) {
                    wa.printStackTrace();
                }
                BufferedReader br = null;
                URLConnection hc = null;
                hc = uri.openConnection();

                hc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                //    br = new BufferedReader(new InputStreamReader());
                final InputStream in = new BufferedInputStream(hc.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String json="";
                String line;

                while ((line = reader.readLine()) != null) {
                    json+=line;
                }


                System.out.println(json);
                JSONObject jsonObject = new JSONObject(json);
                String vid = "";
                String[] title = new String[5];
                if(jsonObject.getJSONArray("items").length()!=0){
                    for(int x=0;x<jsonObject.getJSONArray("items").length();x++){
                        title[x] = jsonObject.getJSONArray("items").getJSONObject(x).getJSONObject("snippet").get("title").toString();
                        String ur = jsonObject.getJSONArray("items").getJSONObject(x).getJSONObject("id").get("videoId").toString();

                        vid+= ur+",";
                    }

                    String search2  = "https://www.googleapis.com/youtube/v3/videos?id="+vid+"&part=contentDetails&key=AIzaSyDNZ8_KqjQ7L09kGS5NzCwIqqgWNiDJ5fg";
                    URL url1 = null;
                    try {
                        url1 = new URL(search2);
                    } catch (MalformedURLException wa) {
                        wa.printStackTrace();
                    }
                    URLConnection hcc = null;
                    hcc = url1.openConnection();

                    hcc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
                    //    br = new BufferedReader(new InputStreamReader());
                    final InputStream inn = new BufferedInputStream(hcc.getInputStream());
                    BufferedReader readerr = new BufferedReader(new InputStreamReader(inn));
                    String jsonn="";
                    String linee;

                    while ((linee = readerr.readLine()) != null) {
                        jsonn+=linee;
                    }
                    reader.close();
                    JSONObject jsonObjec = new JSONObject(jsonn);
                    System.out.println(jsonn);
                    ArrayList<String> URLCoice = new ArrayList();
                    String build = "Choose a song from below !music choose [song number]\n\n";

                    for(int x=0;x<jsonObjec.getJSONArray("items").length();x++){
                        // String title = jsonObject.getJSONArray("items").getJSONObject(x).getJSONObject("snippet").get("title").toString();
                        String ur = "https://www.youtube.com/watch?v="+jsonObjec.getJSONArray("items").getJSONObject(x).get("id").toString();
                        String time = jsonObjec.getJSONArray("items").getJSONObject(x).getJSONObject("contentDetails").get("duration").toString();
                        String ytdate = time;
                        String result = ytdate.replace("PT","").replace("H",":").replace("M",":").replace("S","");
                        String arr[]=result.split(":");
                        String timeString ="";
                        if(time.contains("H")){
                            if(arr.length==3)
                            timeString   = String.format("%d:%02d:%02d", Integer.parseInt(arr[0]), Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
                            else if(arr.length==2)
                                timeString   = String.format("%d:%02d:00", Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                            else
                                timeString   = String.format("%d:00:00", Integer.parseInt(arr[0]));
                        }else if(time.contains("M")){
                            timeString   = String.format("0:%02d:%02d", Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));

                        }else if(time.contains("S")){
                            timeString = String.format("0:00:%02d",Integer.parseInt(arr[0]));
                        }


                        URLCoice.add(ur);

                        build += (x+1)+") "+title[x]+" [" +timeString+"] \n\n";

                    }

                    ArrayList<String> choice = choiceManager.get(guildId);

                    if (choice == null) {
                        choiceManager.put(guildId, URLCoice);
                    }else{
                        choiceManager.remove(guildId);
                        choiceManager.put(guildId, URLCoice);
                    }
                    event.getChannel().sendMessage(build).queue();


                }else{
                    event.getChannel().sendMessage("No results found for " + event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("play")+4).trim()).queue();
                }

            }








    } else{

        event.getChannel().sendMessage("Join a channel first then choose a song.").queue();

    }



}else if(code[1].equals("skip")){
    long guildId = Long.parseLong(event.getGuild().getId());
    GuildMusicManager musicManager = musicManagers.get(guildId);
    if(musicManager.getTrackList().size()<=1){
        event.getChannel().sendMessage("No next song").queue();
    }else
    skipTrack(event.getTextChannel());

}else if(code[1].equals("stop")){
   leaveVoiceChannel();


}else if(code[1].equals("pause")){

    pauseVoice();


    }else if(code[1].equals("queue")){
queueList();



}else if(code[1].equals("volume")){
    int vol;
    try{
        vol = Integer.parseInt(code[2]);
        volume(vol);
    }catch (Exception e){
        event.getChannel().sendMessage("Volume number must be an integer").queue();
    }




}else if(code[1].equals("choice")||code[1].equals("choose")){
    long guildId = Long.parseLong(event.getGuild().getId());
    ArrayList<String> choice = choiceManager.get(guildId);
    if(choice!=null){
        loadAndPlay(event.getTextChannel(),choice.get(Integer.parseInt(code[2])-1));
        choiceManager.remove(guildId);

    }else{

        event.getChannel().sendMessage("Error no choices").queue();

    }


}else{

    event.getChannel().sendMessage("Error invalid command").queue();

}







    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager,event);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }


    private void loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding to queue " + track.getInfo().title).queue();
                musicManager.getTrackList().add(track.getInfo().title);
                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                channel.sendMessage("Could not play: " + exception.getMessage()).queue();
            }

        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);

    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
       if(musicManager.getTrackList().size()==0){
           channel.sendMessage("No next track").queue();
       }else{
           musicManager.scheduler.nextTrack();
           musicManager.getTrackList().remove(0);
           channel.sendMessage("Skipped to next track.").queue();
       }

    }



    private void connectToVoiceChannel(AudioManager audioManager) {

        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            int delay = 5000;   // delay for 5 sec.
            int period = 60000;  // repeat every min.
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if( event.getGuild().getAudioManager().getConnectedChannel().getMembers().size()==1){
                        Thread t1 = new Thread(new Runnable() {
                            public void run()
                            {
                                event.getGuild().getAudioManager().closeAudioConnection();
                                event.getChannel().sendMessage("No users in voice channel").queue();
                            }});
                        t1.start();

                    }
                }
            }, delay, period);
               audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());




        }



    }

    private void leaveVoiceChannel() {
            if(!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){
                event.getChannel().sendMessage("I am not in any voice channels playing music").queue();
            }else{
                event.getGuild().getAudioManager().closeAudioConnection();
                event.getChannel().sendMessage("Left voice channel and stopped playing music").queue();
                long guildId = Long.parseLong(event.getGuild().getId());
                musicManagers.remove(guildId);
            }

        }
    private void pauseVoice() {
        if(!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){
            event.getChannel().sendMessage("I am not in any voice channels playing music").queue();
        }else{
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getChannel().sendMessage("Paused the music, to get back in type !music play").queue();
        }

    }
    private void unpause() {

        if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()) {

            event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());


        }
    }
        private void queueList(){
            GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

            if(musicManager.getTrackList().size()==0){
                event.getChannel().sendMessage("No next track").queue();


            }else{
                String build = "Current queue:\n";
                for(String trackName:musicManager.getTrackList()){

                    build+="\t"+trackName+"\n";


                }

                event.getChannel().sendMessage(build).queue();

            }
        }

        private void volume(int i){
            if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()) {
                event.getChannel().sendMessage("Not playing music to any server").queue();
            }else{
                GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
                musicManager.player.setVolume(i);
                event.getChannel().sendMessage("Changed the volume to " + i+"%").queue();
            }



        }




    }




