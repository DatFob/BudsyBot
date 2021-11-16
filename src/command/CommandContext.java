package command;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext implements CommandInfo{
    private final GuildMessageReceivedEvent event;
    private final List<String> arguments;


    public CommandContext(GuildMessageReceivedEvent event, List<String> arguments){
        this.event = event;
        this.arguments = arguments;
    }

    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    public List<String> getArguments() {
        return arguments;
    }

    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }


}
