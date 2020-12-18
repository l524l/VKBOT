package ru.mityushin.responder.exceptions;

public class BadParameterDetected extends Exception {
    public BadParameterDetected(){
        super("Параметры содержат ошибки");
    }
    public BadParameterDetected(String message){
        super(message);
    }
}
