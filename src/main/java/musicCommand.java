import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class musicCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        String userCommand;
        System.out.printf("[%s]: %s\n", event.getAuthor().getName(), event.getMessage().getContentDisplay());

        if(event.getMessage().getContentDisplay().startsWith("-")){
            userCommand = event.getMessage().getContentDisplay().replaceAll("-","");
            //System.out.println(userCommand);
        }

        return;
    }

    public void doMusic(MessageReceivedEvent event){

    }
}
