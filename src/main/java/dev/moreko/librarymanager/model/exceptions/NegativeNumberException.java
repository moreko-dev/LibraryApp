package dev.moreko.librarymanager.model.exceptions;

public class NegativeNumberException extends Exception {
    
    public NegativeNumberException() {
        super("Number is negative and invalid.");
    }

    public NegativeNumberException(String msg) {
        super(msg);
    }

    public NegativeNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
