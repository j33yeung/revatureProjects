package com.revature.bankingapp.Exceptions;

public class ApplicationNotSentException extends RuntimeException{
    public ApplicationNotSentException() {
    }

    public ApplicationNotSentException(String message) {
        super(message);
    }

    public ApplicationNotSentException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotSentException(Throwable cause) {
        super(cause);
    }

    public ApplicationNotSentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
