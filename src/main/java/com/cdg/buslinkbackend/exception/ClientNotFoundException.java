package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * The ClientNotFoundException class is a custom exception class that extends
 * the RuntimeException
 * class
 */
public class ClientNotFoundException extends RuntimeException {
    // A constructor that takes a string as a parameter and passes it to the super
    // class.
    public ClientNotFoundException(String message) {
        super(MessageFormat.format("Client not found: {0}", message));
    }
}
