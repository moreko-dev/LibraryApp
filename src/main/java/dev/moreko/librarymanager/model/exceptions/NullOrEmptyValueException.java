package dev.moreko.librarymanager.model.exceptions;

public class NullOrEmptyValueException extends Exception {

    public NullOrEmptyValueException() {
        super("Value is invalid.");
    }

    public NullOrEmptyValueException(String msg) {
        super(msg);
    }

    public NullOrEmptyValueException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
