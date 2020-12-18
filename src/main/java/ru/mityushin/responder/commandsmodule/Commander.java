package ru.mityushin.responder.commandsmodule;

import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mityushin.responder.exceptions.UnknownCommand;

@Component
public class Commander {

    /**
     * Обработка сообщений, получаемых через сервис Вконтакте. Имеет ряд дополнительной информации.
     * @param message сообщение (запрос) пользователя
     */
    private final CommandManager commander;

    @Autowired
    public Commander(CommandManager commander) {
        this.commander = commander;
    }

    public void execute(Message message){
        try {
            CommandDeterminant.getCommand(commander.getCommands(), message).exec(message);
        } catch (UnknownCommand unknownCommand) {
            unknownCommand.getMessage();
        }
    }

}
