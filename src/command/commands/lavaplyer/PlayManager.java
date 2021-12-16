package command.commands.lavaplyer;

import com.iwebpp.crypto.TweetNaclFast;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.tools.PlayerLibrary;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayManager {
    private static PlayManager singleton;

    private final Map<Long, GuildMusicManger> musicManager;
    private final AudioPlayerManager audioPlayer;

    public PlayManager(){
        this.musicManager = new HashMap<>();
        this.audioPlayer = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(this.audioPlayer);
        AudioSourceManagers.registerLocalSource(this.audioPlayer);
    }

    public GuildMusicManger getMusic(Guild guild){
        return this.musicManager.computeIfAbsent(guild.getIdLong(), (guildID) -> {
            GuildMusicManger guildMusicManger = new GuildMusicManger(this.audioPlayer);
            guild.getAudioManager().setSendingHandler(guildMusicManger.getSendHandler());
            return guildMusicManger;
        });
    }

    public void loadAndPlay(TextChannel channel, String trackURL){
        GuildMusicManger musicManger = this.getMusic(channel.getGuild());

        this.audioPlayer.loadItemOrdered(musicManger, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                musicManger.scheduler.queue(audioTrack);
                channel.sendMessage("Adding song to queue:")
                        .append(audioTrack.getInfo().title)
                        .append("by: ")
                        .append(audioTrack.getInfo().author)
                        .queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                List<AudioTrack> tracks = audioPlaylist.getTracks();
                channel.sendMessage("Adding song to queue:")
                        .append("Tracks from playList")
                        .append(audioPlaylist.getName())
                        .queue();

                for(final AudioTrack track: tracks){
                    musicManger.scheduler.queue(track);
                }

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException e) {

            }
        });
    }

    public static PlayManager getInstance(){
        if(singleton == null){
            singleton = new PlayManager();
        }

        return singleton;
    }
}
