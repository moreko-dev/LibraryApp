package dev.moreko.librarymanager.model.exceptions;

public class NotAvailableBookException extends Exception {
    
    public NotAvailableBookException() {
        super("Not available book.");
    }

    public NotAvailableBookException(String msg) {
        super(msg);
    }

    public NotAvailableBookException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
