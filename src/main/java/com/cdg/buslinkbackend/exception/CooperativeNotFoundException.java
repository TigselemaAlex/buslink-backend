package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class CooperativeNotFoundException extends RuntimeException{

    public CooperativeNotFoundException(String message){
        super(MessageFormat.format("Cooperative not found: {0}", message));
    }
}
