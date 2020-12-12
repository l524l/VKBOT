package ru.mityushin.responder.commandsmodule.commands;


import ru.mityushin.responder.commandsmodule.Command;
import ru.mityushin.responder.entity.MessageNewCallback;

/**
 * @author Arthur Kupriyanov
 */
public class Unknown extends Command {

    public Unknown(String name) {
        super(name);
    }

    @Override
    public void exec(MessageNewCallback message) {
        //new VKManager().sendMessage("Неизвестная команда", message.getUserId());
    }
}
