package dev.moreko.librarymanager.model.exceptions;

public class InvalidPhoneNumberException extends Exception {

    public InvalidPhoneNumberException() {
        super("Phone number is invalid.");
    }

    public InvalidPhoneNumberException(String msg) {
        super(msg);
    }

    public InvalidPhoneNumberException(String msg, Throwable cause) {
        super(msg, cause);
    }
    
}
