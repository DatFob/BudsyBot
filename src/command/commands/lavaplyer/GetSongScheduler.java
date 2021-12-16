package command.commands.lavaplyer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Get SOng Scheduler Event
 * Uses Lava Player library
 */
public class GetSongScheduler extends AudioEventAdapter {
    final private AudioPlayer player;
    final private LinkedBlockingQueue<AudioTrack> queue;

    public GetSongScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if(endReason.mayStartNext){
            nextTrack();
        }
    }

    public void queue(AudioTrack track){
        //if a track is playing, start track will not interrupt this track
        if(!this.player.startTrack(track,true)){
            this.queue.offer(track);
        }
    }

    //use queue poll() to grab the next song
    public void nextTrack(){
          this.player.startTrack(this.queue.poll(),false);
    }
}
