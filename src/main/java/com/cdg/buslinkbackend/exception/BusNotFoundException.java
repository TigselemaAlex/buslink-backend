package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * This class is a custom exception that is thrown when a bus is not found.
 */
public class BusNotFoundException extends RuntimeException {
    // A constructor that takes a string as a parameter and passes it to the super
    // class.
    public BusNotFoundException(String message) {
        super(MessageFormat.format("Bus not found: {0}", message));
    }
}
