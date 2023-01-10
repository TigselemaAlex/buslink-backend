package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String message) {
        super(MessageFormat.format("Role not found: {0}", message));
    }
}
