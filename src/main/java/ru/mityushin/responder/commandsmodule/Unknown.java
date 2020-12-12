package ru.mityushin.responder.commandsmodule;


import ru.mityushin.responder.commandsmodule.Command;
import ru.mityushin.responder.dto.MessagesSendDto;
import ru.mityushin.responder.entity.MessageNewCallback;

/**
 * @author Arthur Kupriyanov
 */
public class Unknown extends Command {

    public Unknown(String name) {
        super(name);
    }

    @Override
    public String exec(String message) {
        //new VKManager().sendMessage("Неизвестная команда", message.getUserId());

        return "&#127358;";
    }
}
