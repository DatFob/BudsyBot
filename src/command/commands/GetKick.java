package command.commands;

import command.CommandContext;
import command.CommandInterface;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

public class GetKick implements CommandInterface {
    @Override
    public void handle(CommandContext context) {
        TextChannel channel = context.getChannel();
        Message msg = context.getMessage();
        Member member = context.getMember();
        List<String> args = context.getArguments();

        if (args.size()<2 || msg.getMentionedMembers().isEmpty()){
            channel.sendMessage("Arguments error").queue();
        }

        Member target = msg.getMentionedMembers().get(0);

        if (!member.canInteract(target) || !member.hasPermission(Permission.KICK_MEMBERS)){
            channel.sendMessage("You do not have the permission to kick such user").queue();
            return;
        }

        Member self = context.getGuild().getSelfMember();
        if (!self.canInteract(target) || !self.hasPermission(Permission.KICK_MEMBERS)){
            channel.sendMessage("I do not have the permission to kick such user").queue();
            return;
        }

        String reason = String.join(" ", args.subList(1,args.size()));

        context.getGuild().kick(target,reason).reason(reason).queue(
                (__) -> channel.sendMessage("Successfully kicked the user").queue(),
                (error) -> channel.sendMessageFormat("Could not kick %s", error.getMessage()).queue()
        );

    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getHelp() {
        return "kick a user out of the server.\n Usage: -kick <@user> <reason>";
    }
}
