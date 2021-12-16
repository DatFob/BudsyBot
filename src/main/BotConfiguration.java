package main;

import command.EventListener;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.dv8tion.jda.internal.utils.Checks;
import javax.security.auth.login.LoginException;
import java.util.EnumSet;
import java.util.function.Supplier;

/**
 * Main driver class to start the bot
 * Builds a JDA variable with proper configuration
 */
public class BotConfiguration {
    public static JDA jda;
    /**
     * Method runs as soon as bot starts
     */
    private BotConfiguration() throws LoginException {
        //JDA Version 4.2 changed way to build, old version is:
        //jda = new JDABuilder(AccountType.bot).setToken("").buildAsync();
        jda = JDABuilder.createDefault(DotenvHelper.get("TOKEN"),
                        GatewayIntent.GUILD_MEMBERS,
                        GatewayIntent.GUILD_MESSAGES,
                        GatewayIntent.GUILD_VOICE_STATES
                )                .disableCache(EnumSet.of(
                        CacheFlag.CLIENT_STATUS,
                        CacheFlag.ACTIVITY,
                        CacheFlag.EMOTE
                ))
                .enableCache(CacheFlag.VOICE_STATE)
                .addEventListeners(new EventListener())
                .setActivity(Activity.playing("Working Really Hard"))
                .build();
    }

    public static void main(String[] args) throws LoginException{

        new BotConfiguration();
    }
}
