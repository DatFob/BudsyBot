package command;

import command.CommandContext;
import command.CommandInterface;
import command.commands.GetHelp;
import command.commands.GetKick;
import command.commands.GetPing;
import command.commands.lavaplyer.JoinCommand;
import command.commands.lavaplyer.PlayCommand;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.dv8tion.jda.api.JDA;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class CommandRouter {
    private static final Logger log = LoggerFactory.getLogger(CommandRouter.class);
    private final List<CommandInterface> commands = new ArrayList<>();

    public CommandRouter(){
        addCommand(new GetPing());
        addCommand(new GetHelp(this));
        addCommand(new GetKick());
        addCommand(new PlayCommand());
        addCommand(new JoinCommand());
    }

    private void addCommand(CommandInterface newCommand){
        //if(this.commands.stream().anyMatch())
        boolean name = this.commands.stream().anyMatch((it) -> it.getName().equalsIgnoreCase(newCommand.getName()));

        if(name){
            throw new IllegalArgumentException("We cannot have two command with the same name");
        }
        commands.add(newCommand);
    }

    public List<CommandInterface> getCommands(){
        return commands;
    }

    public CommandInterface getCommand(String target){
        for (CommandInterface command : this.commands){
            if(command.getName().equals(target.toLowerCase()) || command.getAliases().contains(target.toLowerCase())){
                return command;
            }
        }
        return null;
    }

    //By not having accesser modifier, I'm making sure this method is visible only in its package
    void handle(GuildMessageReceivedEvent event){
        String[] message = event.getMessage().getContentRaw().replaceFirst("(?i)" + "-", "").split("\\s+");

        String key = message[0].toLowerCase();

        CommandInterface command = this.getCommand(key);

        if(command != null){
            log.info("Command is not null, it's:"+key);
            event.getChannel().sendTyping().queue();
            //take remaining of the message and use it to create a new
            List<String> arguments = Arrays.asList(message).subList(1, message.length);

            CommandContext content = new CommandContext(event,arguments);

            command.handle(content);
        }else{
            log.info("Command is null");
            TextChannel tc = event.getGuild().getTextChannelsByName("general",true).get(0);
            tc.sendMessage("Command is invalid please try again.").queue();
        }
    }
}
