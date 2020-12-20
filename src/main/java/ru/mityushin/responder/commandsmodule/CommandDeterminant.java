package ru.mityushin.responder.commandsmodule;

import com.vk.api.sdk.objects.messages.Message;
import ru.mityushin.responder.entity.MessageNewCallback;
import ru.mityushin.responder.exceptions.UnknownCommand;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Определяет команду
 *
 * @author Артур Куприянов
 * @version 1.1.0
 */
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
