package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class TicketNotFoundException extends RuntimeException{
    public TicketNotFoundException(String message){
        super(MessageFormat.format("Ticket not found: {0}", message));
    }
}
