package command.commands.lavaplyer;

import command.CommandContext;
import command.CommandInterface;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements CommandInterface {
    @Override
    public void handle(CommandContext context) {
        TextChannel channel = context.getChannel();
        Member self = context.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();

        if(selfVoiceState.inVoiceChannel()){
            channel.sendMessage("The bot is already in a voice channel").queue();
            return;
        }

        Member member = context.getMember();
        GuildVoiceState memberVoiceState = member.getVoiceState();
        if(!memberVoiceState.inVoiceChannel()){
            channel.sendMessage("You need to be in a voice channel").queue();
            return;
        }
        AudioManager audioManager = context.getGuild().getAudioManager();
        VoiceChannel memberChannel = memberVoiceState.getChannel();
        audioManager.openAudioConnection(memberChannel);
        channel.sendMessage("Connecting to voice channel").queue();
    }

    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Ask the bot to join a voice channel";
    }
}
