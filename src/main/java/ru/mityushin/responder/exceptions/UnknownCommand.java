package ru.mityushin.responder.exceptions;

public class UnknownCommand extends Exception {
    public UnknownCommand(){
        super("unknown");
    }
    public UnknownCommand(String message){
        super(message);
    }
}
