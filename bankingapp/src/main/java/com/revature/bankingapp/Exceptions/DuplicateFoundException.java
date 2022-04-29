package com.revature.bankingapp.Exceptions;

public class DuplicateFoundException extends RuntimeException{
    public DuplicateFoundException() {
    }

    public DuplicateFoundException(String message) {
        super(message);
    }

    public DuplicateFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateFoundException(Throwable cause) {
        super(cause);
    }

    public DuplicateFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
