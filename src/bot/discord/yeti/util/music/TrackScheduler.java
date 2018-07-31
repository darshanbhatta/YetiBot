package bot.discord.yeti.util.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
  private final AudioPlayer player;
  public final BlockingQueue<AudioTrack> queue;
  MessageReceivedEvent event;
  boolean loop = false;
  boolean repeat = false;

  public boolean isRepeat() {
    return repeat;
  }

  public void setRepeat(boolean repeat) {
    this.repeat = repeat;
  }

  ArrayList<AudioTrack> audioTrack = new ArrayList();


  public ArrayList<AudioTrack> getAudioTrack() {
    return audioTrack;
  }

  public void setAudioTrack(ArrayList<AudioTrack> audioTrack) {
    this.audioTrack = audioTrack;
  }

  /**
   * @param player The audio player this scheduler uses
   */
  public TrackScheduler(AudioPlayer player,MessageReceivedEvent g) {
    this.player = player;
    event = g;
    this.queue = new LinkedBlockingQueue<>();
  }

  public boolean isLoop() {
    return loop;
  }
  public void setLoop(boolean loop) {
    this.loop = loop;



  }
  /**
   * Add the next track to queue or play right away if nothing is in the queue.
   *
   * @param track The track to play or add to queue.
   */
  public void queue(AudioTrack track) {
    // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
    // something is playing, it returns false and does nothing. In that case the player was already playing so this
    // track goes to the queue instead.
    if (!player.startTrack(track, true)) {
      queue.offer(track);

    }

  }

  /**
   * Start the next track, stopping the current one if it is playing.
   */
  public void nextTrack() {

    // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
    // giving null to startTrack, which is a valid argument and will simply stop the player.
   player.startTrack(queue.poll(), false);



  }




  @Override
  public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {

    // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
    if(repeat){
      audioTrack.remove(0);
      audioTrack.add(0,track.makeClone());
      player.startTrack(track.makeClone(), false);
    }
    else if (endReason.mayStartNext) {
      nextTrack();
      audioTrack.remove(0);
      if(loop){
        queue.add(track.makeClone());
        audioTrack.add(track.makeClone());

      }



      }else{
      if(loop){
        queue.add(track.makeClone());
        audioTrack.add(track.makeClone());

      }
    }


    }
  }

