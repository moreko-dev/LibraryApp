package dev.moreko.librarymanager.model.exceptions;

public class NullObjectPassedException extends Exception {
    
    public NullObjectPassedException() {
        super("Null object.");
    }

    public NullObjectPassedException(String msg) {
        super(msg);
    }

    public NullObjectPassedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
