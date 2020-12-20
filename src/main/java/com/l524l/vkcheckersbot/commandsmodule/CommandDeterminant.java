package com.l524l.vkcheckersbot.commandsmodule;

import com.vk.api.sdk.objects.messages.Message;
import com.l524l.vkcheckersbot.commandsmodule.commands.Command;
import com.l524l.vkcheckersbot.exceptions.UnknownCommand;
import java.util.Collection;

public class CommandDeterminant {

    public static Command getCommand(Collection<Command> commands, Message message) throws UnknownCommand {
        String body = message.getText();
        if (!isCommand(body)) throw new UnknownCommand();
        String m = body.substring(1);
        String[] s = m.split(" ");
        for (Command command : commands
        ) {
                if (command.name.equals(s[0])) {
                    if (s.length > 1){
                        command.setParam(s[1]);
                    }
                    return command;
                }
        }
        throw new UnknownCommand();
    }
    public static boolean isCommand(String command){
        if (command.matches("((([.]|[!]|[/]|[\\\\])\\S+\\s\\S+\\z)|(([.]|[!]|[/]|[\\\\])\\S+\\z))"))return true;
        else return false;
    }
}
