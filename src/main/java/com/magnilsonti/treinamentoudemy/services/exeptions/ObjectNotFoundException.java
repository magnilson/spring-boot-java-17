package com.magnilsonti.treinamentoudemy.services.exeptions;

public class ObjectNotFoundException extends RuntimeException{
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
