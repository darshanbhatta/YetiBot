package bot.discord.yeti.command;

import bot.discord.yeti.dictionary.API;
import bot.discord.yeti.util.music.GuildMusicManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;
import org.joda.time.DateTimeConstants;
import org.json.JSONObject;


import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static bot.discord.yeti.dictionary.API.youtubeToken;

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
        String[] code = event.getMessage().getContentRaw().replaceFirst("y", "").split(" ");
        System.out.println(Arrays.toString(code));
        if(code.length==1&&code[0].equalsIgnoreCase("music")){

            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music Commands")
                    .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .addField("Play","```yplay (url/search)```",true)
                    .addField("Pause","```ypause```",true)
                    .addField("Skip","```yskip```",true)
                    .addField("List songs in Queue","```yqueue```",true)
                    .addField("Shuffle","```yshuffle```",true)
                    .addField("Repeat Current Song","```yrepeat```",true)
                    .addField("Repeat Queue","```yqueue```",true)
                    .addField("Stop Playing","```ystop```",true)
                    .addField("Volume","```yvolume (vol %)```",true)
                    .addField("Choose Song","```ychoose (number)```",true)
                    .build()).queue();


        }else if(code[0].equalsIgnoreCase("play")){

            long guildId = Long.parseLong(event.getGuild().getId());
            if(code.length==1&&musicManagers.get(guildId)!=null&&!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){
                unpause();

            } else if(code.length>1&&event.getMember().getVoiceState().getChannel()!=null){
                String url;
                url = code[1];
                boolean vaildURL;
                URL urii = null;
                try {
                    urii = new URL(url);
                    vaildURL = true;
                } catch (MalformedURLException woa) {
                    vaildURL = false;
                }

                if(vaildURL){
                    System.out.println("checking");
                    loadAndPlay(event.getTextChannel(),url);
                }else{
                    String keyword = event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("play")+4).trim();
                    keyword = keyword.replace(" ", "+");

                    String search = "https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&part=contentDetails&order=relevance&type=video&q=" + keyword + "&key="+youtubeToken;

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


                    // System.out.println(json);
                    JSONObject jsonObject = new JSONObject(json);
                    String vid = "";
                    String[] title = new String[5];
                    if(jsonObject.getJSONArray("items").length()!=0){
                        for(int x=0;x<jsonObject.getJSONArray("items").length();x++){
                            title[x] = jsonObject.getJSONArray("items").getJSONObject(x).getJSONObject("snippet").get("title").toString();
                            String ur = jsonObject.getJSONArray("items").getJSONObject(x).getJSONObject("id").get("videoId").toString();

                            vid+= ur+",";
                        }

                        String search2  = "https://www.googleapis.com/youtube/v3/videos?id="+vid+"&part=contentDetails&key="+API.youtubeToken;
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
                        //  System.out.println(jsonn);
                        ArrayList<String> URLCoice = new ArrayList();
                        String build = " ";

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
                                if(arr.length==2)
                                    timeString   = String.format("0:%02d:%02d", Integer.parseInt(arr[0]),Integer.parseInt(arr[1]));
                                else
                                    timeString   = String.format("0:%02d:00", Integer.parseInt(arr[0]));

                            }else if(time.contains("S")){
                                timeString = String.format("0:00:%02d",Integer.parseInt(arr[0]));
                            }


                            URLCoice.add(ur);

                            build += "**"+(x+1)+")** "+title[x]+" [" +"`"+timeString+"]` \n\n";

                        }

                        ArrayList<String> choice = choiceManager.get(guildId);

                        if (choice == null) {
                            choiceManager.put(guildId, URLCoice);
                        }else{
                            choiceManager.remove(guildId);
                            choiceManager.put(guildId, URLCoice);
                        }


                        event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Choose a song from below `ychoose [song number]`")
                                .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                                .setDescription(build)
                                .build()).queue();


                    }else{

                        event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music")
                                .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                                .setDescription("No results found for " + event.getMessage().getContentRaw().substring(event.getMessage().getContentRaw().indexOf("play")+4).trim())
                                .build()).queue();
                    }

                }








            } else{

                if(code.length==1){



                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Need to search for a song or provide URL","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();

                }else



                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Join a channel first then choose a song.","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .build()).queue();

            }



        }else if(code[0].equalsIgnoreCase("skip")){
            long guildId = Long.parseLong(event.getGuild().getId());
            GuildMusicManager musicManager = musicManagers.get(guildId);
            if(musicManager.scheduler.getAudioTrack().size()<=1){


                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Queue list is empty","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .build()).queue();
            }else
                skipTrack(event.getTextChannel());

        }else if(code[0].equalsIgnoreCase("stop")){

            leaveVoiceChannel();



        }else if(code[0].equalsIgnoreCase("pause")){

            pauseVoice();


        }else if(code[0].equalsIgnoreCase("queue")){
            queueList();



        }else if(code[0].equalsIgnoreCase("volume")){
            int vol;
            try{
                vol = Integer.parseInt(code[1]);
                if(vol>=0&&vol<=100)
                    volume(vol);
                else{

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Too loud! Volume number must be between 0-100","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();
                }

            }catch (Exception e){


                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Volume number must be an integer","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .build()).queue();
            }




        }else if(code[0].equalsIgnoreCase("loop")){
            long guildId = Long.parseLong(event.getGuild().getId());
            GuildMusicManager musicManager = musicManagers.get(guildId);
            if(musicManager!=null){
                if(!musicManager.scheduler.isLoop()){

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Looping is enabled","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();
                    musicManager.scheduler.setLoop(true);
                }

                else{

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Looping is disabled","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();

                    musicManager.scheduler.setLoop(false);
                }

            }


        }else if(code[0].equalsIgnoreCase("repeat")){
            long guildId = Long.parseLong(event.getGuild().getId());
            GuildMusicManager musicManager = musicManagers.get(guildId);
            if(musicManager!=null){
                if(!musicManager.scheduler.isRepeat()){

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Repeat is on","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();
                    musicManager.scheduler.setRepeat(true);
                }

                else{

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Repeat is off","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .build()).queue();
                    musicManager.scheduler.setRepeat(false);
                }

            }


        }else if(code[0].equalsIgnoreCase("shuffle")){
            long guildId = Long.parseLong(event.getGuild().getId());
            GuildMusicManager musicManager = musicManagers.get(guildId);
            if(musicManager!=null){
                if(!musicManager.scheduler.isRepeat()){
                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Shuffling Queue","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                            .build()).queue();
                    Collections.shuffle(musicManager.scheduler.getAudioTrack());
                    musicManager.scheduler.queue.clear();
                    for(int x=0;x<musicManager.scheduler.getAudioTrack().size();x++) {
                        musicManager.scheduler.queue.add(musicManager.scheduler.getAudioTrack().get(x).makeClone());

                    }
                    if(musicManager.scheduler.getAudioTrack().size()==0){

                    }else{
                        musicManager.scheduler.nextTrack();
                        // musicManager.scheduler.getAudioTrack().remove(0);
                    }

                    System.out.println(musicManager.scheduler.getAudioTrack().toString()    );

                }



            }else{
                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No Tracks In Queue","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                        .build()).queue();

            }


        }else if(code[0].equalsIgnoreCase("current")){
            long guildId = Long.parseLong(event.getGuild().getId());
            GuildMusicManager musicManager = musicManagers.get(guildId);
            if(musicManager!=null) {
                if (musicManager.scheduler.getAudioTrack().size() != 0){

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Currently Playing")
                            .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                            .setDescription("**"+musicManager.scheduler.getAudioTrack().get(0).getInfo().title+"**")
                            .build()).queue();
                }else{


                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Not Playing Music","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                            .build()).queue();
                    musicManager.scheduler.setRepeat(false);
                    musicManager.scheduler.setRepeat(false);
                }

            }else{
                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Not Playing Music","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                        .build()).queue();
                musicManager.scheduler.setRepeat(false);
            }


        }else if(code[0].equalsIgnoreCase("choice")||code[0].equalsIgnoreCase("choose")){
            try{
                int co = Integer.parseInt(code[1]);
                long guildId = Long.parseLong(event.getGuild().getId());
                ArrayList<String> choice = choiceManager.get(guildId);
                if(co>0&&co<6){

                    if(choice!=null){
                        loadAndPlay(event.getTextChannel(),choice.get(co-1));
                        choiceManager.remove(guildId);
                    }else{


                        event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No searches present","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                                .build()).queue();
                    }

                }else{

                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Choice number has to be a number from 1-5","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                            .build()).queue();


                }

            }catch (Exception e){

                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Choice number has to be a number from 1-5","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                        .build()).queue();

            }




        }else{

            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music Commands")
                    .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .addField("Play","```yplay (url/search)```",true)
                    .addField("Pause","```ypause```",true)
                    .addField("Skip","```yskip```",true)
                    .addField("List songs in Queue","```yqueue```",true)
                    .addField("Shuffle","```yshuffle```",true)
                    .addField("Repeat Current Song","```yrepeat```",true)
                    .addField("Repeat Queue","```yqueue```",true)
                    .addField("Stop Playing","```ystop```",true)
                    .addField("Volume","```yvolume (vol %)```",true)
                    .addField("Choose Song","```ychoose (number)```",true)
                    .build()).queue();

        }



        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                event.getMessage().delete().queue();

            }
        }, 5000);



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


    private void    loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());

        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                long millis = track.getInfo().length;
                String hms = "";
                if(TimeUnit.MILLISECONDS.toHours(millis)==0){
                    hms  = String.format("%02d:%02d"    ,TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
                            millis % 1000);

                }else{
                    hms  = String.format("%02d:%02d:%02d",  TimeUnit.MILLISECONDS.toHours(millis),
                            TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                            TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1),
                            millis % 1000);

                }




                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Adding to Queue ")
                        .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .setDescription("**"+track.getInfo().title+"**" +"`["+hms+"]`")
                        .build()).queue();

                musicManager.scheduler.getAudioTrack().add(track);
                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();
                int indx =0;
                for(int x=0;x<playlist.getTracks().size();x++){
                    if(firstTrack.getInfo().title.equals(playlist.getTracks().get(x).getInfo().title)){
                        indx = x;
                        break;
                    }

                }
                System.out.println(indx);

                int size = 0;
                if(playlist.getTracks().size()-indx<100){
                    size = playlist.getTracks().size()-indx;
                    for(int x=indx;x<playlist.getTracks().size();x++){

                        play(channel.getGuild(), musicManager, playlist.getTracks().get(x));
                        musicManager.scheduler.getAudioTrack().add(playlist.getTracks().get(x));
                    }
                }else{
                    size = 100;
                    for(int x=(int)indx;x<100;x++){

                        play(channel.getGuild(), musicManager, playlist.getTracks().get(x));
                        musicManager.scheduler.getAudioTrack().add(playlist.getTracks().get(x));
                    }

                }




                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music")
                        .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .setDescription("Adding playlist to queue " + firstTrack.getInfo().title + " (first track of playlist **" + playlist.getName() + "** [`"+size+"`] )")
                        .build()).queue();

            }

            @Override
            public void noMatches() {

                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music")
                        .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .setDescription("Nothing found by " + trackUrl + " :cry:")
                        .build()).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music")
                        .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                        .setDescription("Could not play: " + exception.getMessage() + " :cry:")
                        .build()).queue();

            }

        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        connectToVoiceChannel(guild.getAudioManager());

        musicManager.scheduler.queue(track);

    }

    private void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        if(musicManager.scheduler.getAudioTrack().size()==0){
            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Music")

                    .setDescription("No next track :cry:")
                    .build()).queue();

        }else{
            musicManager.scheduler.nextTrack();
            // musicManager.scheduler.getAudioTrack().remove(0);

            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Skipped to the next track","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                    .build()).queue();
        }

    }



    private void connectToVoiceChannel(AudioManager audioManager) {

        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            int delay = 5000;   // delay for 5 sec.
            int period = 60000;  // repeat every min.
            Timer timer = new Timer();

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    if(event.getGuild().getAudioManager().isConnected()){
                        if( event.getGuild().getAudioManager().getConnectedChannel().getMembers().size()==1){
                            Thread t1 = new Thread(new Runnable() {
                                public void run()
                                {
                                    String[] ran = {"Don't Leave, this party was just getting started!","C'mon I was liking the company.","See Ya Later Alligator", "I’m off", "I gotta jet", "Bye bye!", "See you soon, racoon.", "Gotta go, buffalo.", "After a while, crocodile."};
                                    event.getGuild().getAudioManager().closeAudioConnection();

                                    event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ran[(int)(Math.random()*ran.length)],"https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                                            .build()).queue();
                                }});
                            t1.start();

                        }

                    }

                }
            }, delay, period);
            audioManager.openAudioConnection(event.getMember().getVoiceState().getChannel());




        }



    }

    private void leaveVoiceChannel() {
        if(!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("I am not in any voice channels playing music :cry:","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();
        }else{
            event.getGuild().getAudioManager().closeAudioConnection();
            String[] ran = {"Don't Leave, this party was just getting started!","C'mon I was liking the company.","See Ya Later Alligator", "I’m off", "I gotta jet", "Bye bye!", "See you soon, racoon.", "Gotta go, buffalo.", "After a while, crocodile."};
            event.getGuild().getAudioManager().closeAudioConnection();
            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor(ran[(int)(Math.random()*ran.length)],"https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")

                    .build()).queue();
            long guildId = Long.parseLong(event.getGuild().getId());
            musicManagers.remove(guildId);
        }

    }
    private void pauseVoice() {
        if(!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()){


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("I am not in any voice channels playing music :cry:","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();
        }else{
            event.getGuild().getAudioManager().closeAudioConnection();


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Paused the music, to get back in type `yplay`","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();
        }

    }
    private void unpause() {

        if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()) {

            event.getGuild().getAudioManager().openAudioConnection(event.getMember().getVoiceState().getChannel());


        }
    }
    private void queueList(){
        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        if(musicManager.scheduler.getAudioTrack().size()==0){

            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("No next track :cry:","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();



        }else{
            String build = "";
            int count =1;
            for(AudioTrack trackName:musicManager.scheduler.getAudioTrack()){

                if(count<26)
                    build+="\t **"+count +")** " + trackName.getInfo().title+"\n";
                else{
                    build+="\n...";
                }
                count++;

            }


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setTitle("Current Music Queue")
                    .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .setDescription(build)
                    .build()).queue();

        }
    }

    private void volume(int i){
        if (!event.getGuild().getAudioManager().isConnected() && !event.getGuild().getAudioManager().isAttemptingToConnect()) {


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Not playing music to any server :cry:","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();
        }else{
            GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
            musicManager.player.setVolume(i);


            event.getChannel().sendMessage(new EmbedBuilder().setColor(new Color(0x8CC8FF)).setAuthor("Changed the volume to " + i+"%","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png","https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/120/google/146/musical-note_1f3b5.png")
                    .build()).queue();

        }



    }




}




