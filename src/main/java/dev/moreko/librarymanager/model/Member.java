package dev.moreko.librarymanager.model;

import java.io.Serializable;

import dev.moreko.librarymanager.model.exceptions.InvalidEmailException;
import dev.moreko.librarymanager.model.exceptions.InvalidPhoneNumberException;
import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;

public class Member implements Serializable {
    public static enum MemberFields {
        ID (0),
        NAME (1),
        EMAIL (2),
        PHONE (3);

        private int index;
        private MemberFields(int index) {
            this.index = index;
        }
        
        public int getIndex() { return this.index; }
        public static MemberFields find(int index) {
            return switch(index) {
                case 0 -> ID;
                case 1 -> NAME;
                case 2 -> EMAIL;
                case 3 -> PHONE;
                default -> ID;
            };
        }
    }

    private String ID;
    private String name;
    private String email;
    private String phone;
    private Book borrowedBook;

    public Member(String ID, String name, String email, String phone) 
            throws InvalidEmailException, InvalidPhoneNumberException, NullOrEmptyValueException {
        this.setID(ID);
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
    }

    public void setID(String ID) throws NullOrEmptyValueException {
        Validator.checkNullability(ID);
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public void setName(String name) throws NullOrEmptyValueException {
        Validator.checkNullability(name);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setEmail(String email) throws NullOrEmptyValueException, InvalidEmailException {
        Validator.checkNullability(email);
        Validator.checkEmail(email);
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPhone(String phone) throws NullOrEmptyValueException, InvalidPhoneNumberException {
        Validator.checkNullability(phone);
        Validator.checkPhone(phone);
        this.phone = phone;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setBorrowedBook(Book book) {
        this.borrowedBook = book;
    }

    public Book getBorrowedBook() {
        return this.borrowedBook;
    }
}