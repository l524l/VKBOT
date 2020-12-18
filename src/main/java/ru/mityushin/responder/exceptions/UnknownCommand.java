package ru.mityushin.responder.exceptions;

public class UnknownCommand extends Exception {
    public UnknownCommand(){
        super("Unknown command");
    }
}
