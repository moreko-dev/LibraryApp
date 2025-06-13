package dev.moreko.librarymanager.model.exceptions;

public class DuplicateValueInListException extends Exception {

    public DuplicateValueInListException() {
        super("Duplicate value in the list.");
    }
    
    public DuplicateValueInListException(String msg) {
        super(msg);
    }
    
    public DuplicateValueInListException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
