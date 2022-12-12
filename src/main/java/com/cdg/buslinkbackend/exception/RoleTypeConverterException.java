package com.cdg.buslinkbackend.exception;

import java.text.MessageFormat;

public class RoleTypeConverterException extends RuntimeException{
    public RoleTypeConverterException(){
        super("The Role can't be converted");
    }
}
