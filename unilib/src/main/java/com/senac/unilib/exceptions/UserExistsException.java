package com.senac.unilib.exceptions;

public class UserExistsException extends RuntimeException {

    public UserExistsException() {
        super();
    }

    public UserExistsException(String msg) {
        super(msg);
    }
}
