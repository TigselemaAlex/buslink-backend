package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class BusNotFoundException extends RuntimeException{
    public BusNotFoundException(String message){
        super(MessageFormat.format("Bus not found: {0}", message));
    }
}
