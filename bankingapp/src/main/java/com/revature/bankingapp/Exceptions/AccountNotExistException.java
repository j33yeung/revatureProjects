package com.revature.bankingapp.Exceptions;

public class AccountNotExistException extends RuntimeException{

    public AccountNotExistException() {
    }

    public AccountNotExistException(String message) {
        super(message);
    }

    public AccountNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountNotExistException(Throwable cause) {
        super(cause);
    }

    public AccountNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
