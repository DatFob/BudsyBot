package command.commands;

import command.CommandContext;
import command.CommandInterface;
import command.EventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.RestAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GetPing implements CommandInterface {
    private static final Logger log = LoggerFactory.getLogger(GetPing.class);
    @Override
    public void handle(CommandContext context) {
        log.info("ping handling ...");
        JDA jda = context.getJDA();
        jda.getRestPing().queue(
                (ping) -> context.getChannel().sendMessageFormat("Reset ping is %sms",ping).queue()
        );
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getHelp() {
        return "Return the ping between a user's device and the discord server";
    }

}
