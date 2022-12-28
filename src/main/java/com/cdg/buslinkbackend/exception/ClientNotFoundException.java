package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class ClientNotFoundException extends RuntimeException{
    public ClientNotFoundException(String message){
        super(MessageFormat.format("Client not found: {0}", message));
    }
}
