package com.senac.unilib.exceptions;

public class IsbnExistsException extends RuntimeException {

    public IsbnExistsException() {
        super();
    }

    public IsbnExistsException(String msg) {
        super(msg);
    }
}
