package command;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext{
    private final GuildMessageReceivedEvent event;
    private final List<String> arguments;

    public CommandContext(GuildMessageReceivedEvent event, List<String> arguments) {
        this.event = event;
        this.arguments = arguments;
    }

    public Guild getGuild(){
        return this.getEvent().getGuild();
    }

    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArguments() {
        return this.arguments;
    }

    public JDA getJDA(){
        return this.getEvent().getJDA();
    }

    public TextChannel getChannel() {
        return this.getEvent().getChannel();
    }

    public Message getMessage(){
        return this.getEvent().getMessage();
    }

    public Member getMember(){
        return this.getEvent().getMember();
    }

}
