package command.commands.lavaplyer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

/**
 * Command class : GuildMusicManger
 * Manages audio player, scheduler and send audio handler
 */
public class GuildMusicManger {
    public final AudioPlayer audioPlayer;
    public final GetSongScheduler scheduler;
    private final SendAudioPlayer sendHandler;

    //create audio player, send player to scheduler
    public GuildMusicManger(AudioPlayerManager manager){
        this.audioPlayer = manager.createPlayer();
        this.scheduler = new GetSongScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.scheduler);
        this.sendHandler = new SendAudioPlayer(this.audioPlayer);
    }

    public SendAudioPlayer getSendHandler(){
        return sendHandler;
    }
}
