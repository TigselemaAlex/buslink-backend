package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

/**
 * The RoleNotFoundException class extends the RuntimeException class and is
 * used to throw an exception
 * when a role is not found
 */
public class RoleNotFoundException extends RuntimeException {
    // Calling the super class constructor with the message.
    public RoleNotFoundException(String message) {
        super(MessageFormat.format("Role not found: {0}", message));
    }
}
