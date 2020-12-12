package ru.mityushin.responder.commandsmodule;

import java.util.HashSet;

/**
 * @author Arthur Kupriyanov
 */
public class CommandManager {
    private static HashSet<Command> commands = new HashSet<>();

    static {
        commands.add(new Unknown("unknown"));
        commands.add(new Play(".play"));
        commands.add(new Move(".move"));
        commands.add(new Remove(".remove"));
        commands.add(new Reset(".reset"));
        commands.add(new Help(".help"));

    }

    public static HashSet<Command> getCommands(){
        return commands;
    }
    public static void addCommand(Command command) { commands.add(command);}
}
