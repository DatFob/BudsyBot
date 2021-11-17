package main;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.LoggerFactory;

/**
 * JDA4 Documentation & functionalities
 * https://ci.dv8tion.net/job/JDA/javadoc/net/dv8tion/jda/api/hooks/ListenerAdapter.html
 */
public class EventListener extends ListenerAdapter {

    private static final Logger log = LoggerFactory.getLogger(EventListener.class);

    @Override
    public void onReady(ReadyEvent event) {
        log.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String raw = event.getMessage().getContentRaw();
        if (raw.equalsIgnoreCase("-shutdown") && event.getAuthor().getId().equals(DotenvHelper.get("USERID"))){
            log.info("Shutting down the bot");
            event.getJDA().shutdown();
        }
    }
}
