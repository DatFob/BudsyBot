package command.commands;

import command.CommandContext;
import command.CommandInterface;
import command.CommandRouter;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class GetHelp implements CommandInterface {

    private final CommandRouter router;

    public GetHelp(CommandRouter router) {
        this.router = router;
    }

    @Override
    public void handle(CommandContext context) {
        List<String> args = context.getArguments();
        TextChannel channel = context.getChannel();

        if(args.isEmpty()){
            StringBuilder sb = new StringBuilder();
            sb.append("List of Commands\n");
            router.getCommands().stream().map(CommandInterface::getName).forEach(
                    (it) -> sb.append("-").append(it).append("\n")
            );
            channel.sendMessage(sb.toString()).queue();
            return;
        }

        String target = args.get(0);
        CommandInterface cmd = router.getCommand(target);

        if(cmd == null){
            channel.sendMessage("Nothing found for input:" + target).queue();
            return;
        }

        channel.sendMessage(cmd.getHelp()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "List all commands for the bot\n Invoke: -help [commandName]";
    }
}
