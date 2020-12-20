package com.l524l.vkcheckersbot.commandsmodule;

import com.vk.api.sdk.objects.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.l524l.vkcheckersbot.exceptions.UnknownCommand;

@Component
public class Commander {

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
