package command;

import java.util.Arrays;
import java.util.List;

public interface CommandInterface {
    void handle(CommandContext context);

    String getName();

    String getHelp();

    //List.of()
    default List<String> getAliases(){return List.of();}
}
