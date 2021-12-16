package main;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * DotenvHelper class
 * Works with .env file to get value by key instead of using a config file
 */
public class DotenvHelper {

    //Load the dotenv .env file and create a map
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key){
        return dotenv.get(key);
    }
}
