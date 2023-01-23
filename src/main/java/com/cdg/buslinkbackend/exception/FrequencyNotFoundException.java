package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class FrequencyNotFoundException extends RuntimeException{

    public FrequencyNotFoundException(String message){
        super(MessageFormat.format("Frequency not found: {0}", message));
    }
}
