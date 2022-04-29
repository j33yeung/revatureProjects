package com.revature.bankingapp.Exceptions;

public class BalanceBelowZeroException extends RuntimeException{
    public BalanceBelowZeroException() {
    }

    public BalanceBelowZeroException(String message) {
        super(message);
    }

    public BalanceBelowZeroException(String message, Throwable cause) {
        super(message, cause);
    }

    public BalanceBelowZeroException(Throwable cause) {
        super(cause);
    }

    public BalanceBelowZeroException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
