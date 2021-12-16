package command;

import main.DotenvHelper;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.LoggerFactory;

/**
 * EventListener class extends ListenerAdapter from JDA library
 * Actively listens to new commands and handle them
 */
public class EventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);
    private final CommandRouter router = new CommandRouter();
    @Override
    public void onReady(ReadyEvent event) {
        log.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {

        //generate user object from Event.getAuthor()
        User user = event.getAuthor();

        //We only respond to user messages but not to bot or webhookmessage
        if (user.isBot() || event.isWebhookMessage()){
            return;
        }

        //client side's message
        String raw = event.getMessage().getContentRaw();

        //Only I can shut down the bot
        if (raw.equalsIgnoreCase("-shutdown") && user.getId().equals(DotenvHelper.get("USERID"))){
            log.info("Shutting down the bot");
            event.getJDA().shutdown();
            event.getJDA().getHttpClient().connectionPool().evictAll();
            event.getJDA().getHttpClient().dispatcher().executorService().shutdown();
            return;
        }

        //any other commands, we handle such command
        if(raw.startsWith("-")){
            log.info("raw message is" + raw);
            router.handle(event);
        }

    }
}
