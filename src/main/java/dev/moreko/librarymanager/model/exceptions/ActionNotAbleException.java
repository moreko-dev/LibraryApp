package dev.moreko.librarymanager.model.exceptions;

public class ActionNotAbleException extends Exception {
    
    public ActionNotAbleException() {
        super("Action is not able.");
    }

    public ActionNotAbleException(String msg) {
        super(msg);
    }

    public ActionNotAbleException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
