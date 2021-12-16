package command.commands.lavaplyer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

/**
 * Command class : SendAudioPlayer
 * Implements jda library Audiosendhandler
 * sends the audio in byte form from running machine (the machine runs the program)
 */
public class SendAudioPlayer implements AudioSendHandler {
    private final AudioPlayer audioPlayer;
    //store bytes and send to discord
    private final ByteBuffer buffer;
    private final MutableAudioFrame frame;

    public SendAudioPlayer(AudioPlayer audioPlayer){
        this.audioPlayer = audioPlayer;
        this.buffer = ByteBuffer.allocate(1024);
        this.frame = new MutableAudioFrame();
        this.frame.setBuffer(buffer);
    }

    //write to audio frame n write to byte buffer
    @Override
    public boolean canProvide() {
        return this.audioPlayer.provide(this.frame);
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        //flip the buffer set position to 0 and start from beginning
        return this.buffer.flip();
    }

    //lava player always return opus unless we configure it differently
    @Override
    public boolean isOpus() {
        return true;
    }
}
