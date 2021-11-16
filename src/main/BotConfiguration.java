package main;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class BotConfiguration {
    public static JDA jda;
    public static String prefix = "-";
    /**
     * Method runs as soon as bot starts
     */
    public static void main(String[] args) throws LoginException{
        //JDA Version 4.2 changed way to build, old version is:
        //jda = new JDABuilder(AccountType.bot).setToken("").buildAsync();
        jda = JDABuilder.createDefault(DiscordInfo.TOKEN).build();
        jda.getPresence().setActivity(Activity.playing("Working Really Hard"));

        jda.addEventListener(new musicCommand());
    }
}
