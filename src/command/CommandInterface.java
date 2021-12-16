package command;
import java.util.List;

/**
 * Interface for all command classes
 */
public interface CommandInterface {

    //handle a command
    void handle(CommandContext context);

    //returns the name of the command
    String getName();

    //returns usage of a command
    String getHelp();

    //returns list of Aliases
    default List<String> getAliases(){return List.of();}
}
