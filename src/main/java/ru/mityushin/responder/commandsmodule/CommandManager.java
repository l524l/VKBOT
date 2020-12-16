package ru.mityushin.responder.commandsmodule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.mityushin.responder.service.VkMessageSenderService;

import java.util.HashSet;

/**
 * @author Arthur Kupriyanov
 */
@Component
public class CommandManager {
    private HashSet<Command> commands = new HashSet<>();
    private final VkMessageSenderService messageSenderService;

    @Autowired
    public CommandManager(VkMessageSenderService messageSenderService) {
        this.messageSenderService = messageSenderService;
        commands.add(new Unknown(messageSenderService,"unknown"));
        commands.add(new Play(messageSenderService,".play"));
        commands.add(new Move(messageSenderService,".move"));
        commands.add(new Remove(messageSenderService,".remove"));
        commands.add(new Reset(messageSenderService,".reset"));
        commands.add(new Help(messageSenderService,".help"));
    }

    public HashSet<Command> getCommands(){
        return commands;
    }
    public void addCommand(Command command) { commands.add(command);}
}
