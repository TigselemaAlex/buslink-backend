package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(MessageFormat.format("User not found: {0}", message));
    }
}
