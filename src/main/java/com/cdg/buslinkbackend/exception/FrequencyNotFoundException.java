package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * This class is a custom exception that is thrown when a frequency is not found
 */
public class FrequencyNotFoundException extends RuntimeException {

    // A constructor that takes a string as a parameter and passes it to the super
    // class.
    public FrequencyNotFoundException(String message) {
        super(MessageFormat.format("Frequency not found: {0}", message));
    }
}
