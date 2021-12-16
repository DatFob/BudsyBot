package main;

import io.github.cdimascio.dotenv.Dotenv;

public class DotenvHelper {

    //Load the dotenv .env file and create a map
    private static final Dotenv dotenv = Dotenv.load();

    public static String get(String key){
        return dotenv.get(key);
    }
}
