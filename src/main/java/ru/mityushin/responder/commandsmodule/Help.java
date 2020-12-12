package ru.mityushin.responder.commandsmodule;

import ru.mityushin.responder.commandsmodule.Command;
import ru.mityushin.responder.dto.MessagesSendDto;
import ru.mityushin.responder.entity.MessageNewCallback;
import ru.mityushin.responder.service.VkMessageSenderService;

/**
 * @author Arthur Kupriyanov
 */
public class Help extends Command {

    public Help(String name) {
        super(name);
    }

    @Override
    public String exec(String message) {
        String s = "Список команд:\n" +
                ".move XYtoXY - перемещает шашку в соответствии с заданными координатами\n" +
                ".reset - cmoбрасывает игру\n" +
                ".remove XY - удаляет побитые шашки может принимать несколько пармаетров(Remove XY;XY;XY ...)\n" +
                ".help - вызвать это меню";
        return s;
    }
}
