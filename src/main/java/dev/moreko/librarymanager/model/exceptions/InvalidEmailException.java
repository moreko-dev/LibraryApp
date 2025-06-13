package dev.moreko.librarymanager.model.exceptions;

public class InvalidEmailException extends Exception {
    
    public InvalidEmailException() {
        super("Email is invalid.");
    }

    public InvalidEmailException(String msg) {
        super(msg);
    }

    public InvalidEmailException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
