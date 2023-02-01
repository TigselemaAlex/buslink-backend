package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * The TicketNotFoundException class is a custom exception class that extends
 * the RuntimeException
 * class
 */
public class TicketNotFoundException extends RuntimeException {
    // A constructor that takes a string as a parameter and passes it to the super
    // class.
    public TicketNotFoundException(String message) {
        super(MessageFormat.format("Ticket not found: {0}", message));
    }
}
