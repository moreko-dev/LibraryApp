package dev.moreko.librarymanager.model;

import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;
import dev.moreko.librarymanager.model.exceptions.InvalidEmailException;
import dev.moreko.librarymanager.model.exceptions.InvalidPhoneNumberException;
import dev.moreko.librarymanager.model.exceptions.NegativeNumberException;
import dev.moreko.librarymanager.model.exceptions.NullObjectPassedException;

public class Validator {
    
    public static boolean checkNullability(String value) throws NullOrEmptyValueException {
        if (value == null || value.trim().isEmpty()) {
            throw new NullOrEmptyValueException("Invalid value: " + value + ". Value is null or empty.");
        } 
        return true;
    }

    public static boolean checkNegativeNumber(double value) throws NegativeNumberException {
        if (value < 0) {
            throw new NegativeNumberException("Negative number: " + value + ". Value is lower than 0.");
        }
        return true;
    }

    public static boolean checkEmail(String value) throws NullOrEmptyValueException, InvalidEmailException {
        checkNullability(value);
        if (!value.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new InvalidEmailException("Invalid email: " + value + ". Email is invalid");
        }
        return true;
    }

    public static boolean checkPhone(String value) throws NullOrEmptyValueException, InvalidPhoneNumberException {
        checkNullability(value);
        if (!(value.startsWith("09") && value.length() == 11)) {
            throw new InvalidPhoneNumberException("Invalid phone: " + value + ". Phone number is invalid");
        }
        return true;
    }

    public static boolean checlObjNullability(Object obj) throws NullObjectPassedException {
        if (obj != null) {
            throw new NullObjectPassedException("Invalid vlaue: " + obj + ". Object is null.");
        }
        return true;
    }
}
