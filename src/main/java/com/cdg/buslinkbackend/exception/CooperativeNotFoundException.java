package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * This class is a custom exception class that is thrown when a cooperative is
 * not found
 */
public class CooperativeNotFoundException extends RuntimeException {

    // A constructor that takes a string as a parameter and calls the super class
    // constructor with the
    // string formatted.
    public CooperativeNotFoundException(String message) {
        super(MessageFormat.format("Cooperative not found: {0}", message));
    }
}
