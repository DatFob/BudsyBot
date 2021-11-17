package main;

import command.CommandInterface;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandRouter {
    private final List<CommandInterface> commands = new ArrayList<>();

    private void addCommand(CommandInterface newCommand){
        //if(this.commands.stream().anyMatch())
        boolean name = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(newCommand.getName()));

        if(name){
            throw new IllegalArgumentException("We cannot have two command with the same name");
        }
        commands.add(newCommand);
    }

    //Nullable Reference Intellij:
    //https://www.jetbrains.com/help/idea/nullable-and-notnull-annotations.html
    @Nullable
    private CommandInterface getCommand(String target){
        for (CommandInterface command : this.commands){
            if(command.getName().equals(target.toLowerCase()) || command.getAliases().contains(target.toLowerCase())){
                return command;
            }
        }
        return null;
    }

    void handle(GuildMessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().replaceFirst("(?i)" + Pattern.quote("prefix"), "").split("\\s+");
    }
}
