package command.commands.lavaplyer;

import command.CommandContext;
import command.CommandInterface;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import org.w3c.dom.Text;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Command class : PlayCommand
 * validate voice state, url or song name
 */
public class PlayCommand implements CommandInterface {
    @Override
    public void handle(CommandContext context) {
        TextChannel channel = context.getChannel();
        if(context.getArguments().isEmpty()){
            channel.sendMessage("please provide a youtube link : -play <youtubelin>").queue();
            return;
        }
        Member self = context.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(!selfVoiceState.inVoiceChannel()){
            channel.sendMessage("I need to join a voice channel first to play music");
            return;
        }

        Member member = context.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();

        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("You need to join a voice channel first to play music");
            return;
        }

        if(!memberVoiceState.getChannel().equals(selfVoiceState.getChannel())){
            channel.sendMessage("You need to be in the same channel");
            return;
        }

        String link = String.join(" ", context.getArguments());

        if(!isUrl(link)){
            //tell lava player to search youtube for this
            link = "ytsearch:" + link;
        }

        PlayManager.getInstance().loadAndPlay(channel,link);
    }

    @Override
    public String getName() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "play a song\n Usage: -play <youtube link>";
    }

    public boolean isUrl(String url){
        try{
            new URI(url);
            return true;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }
}
