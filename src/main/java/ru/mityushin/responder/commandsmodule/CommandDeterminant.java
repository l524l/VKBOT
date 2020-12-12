package ru.mityushin.responder.commandsmodule;

import ru.mityushin.responder.entity.MessageNewCallback;

import java.util.Collection;

/**
 * Определяет команду
 *
 * @author Артур Куприянов
 * @version 1.1.0
 */
public class CommandDeterminant {


    public static Command getCommand(Collection<Command> commands, MessageNewCallback message) {
        String body = message.getText();

        for (Command command : commands
        ) {
                String[] s = body.split(" ");
                if (command.name.equals(s[0])) {
                    if (s.length > 1){
                        command.setParam(s[1]);
                    }
                    return command;
                }
        }

        return new Unknown("unknown");
    }

}
