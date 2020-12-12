package ru.mityushin.responder.commandsmodule;

import ru.mityushin.responder.entity.MessageNewCallback;

public class Commander {

    /**
     * Обработка сообщений, получаемых через сервис Вконтакте. Имеет ряд дополнительной информации.
     * @param message сообщение (запрос) пользователя
     */
    public static void execute(MessageNewCallback message){
        CommandDeterminant.getCommand(CommandManager.getCommands(), message).exec(message);
    }

}
