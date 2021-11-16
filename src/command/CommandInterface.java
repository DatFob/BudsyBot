package command;

import java.util.Arrays;
import java.util.List;

public interface CommandInterface {
    void handle(CommandContext context);

    String getName();

    default List<String> getAliases(){return Arrays.asList();}
}
