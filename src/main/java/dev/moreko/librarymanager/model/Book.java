package dev.moreko.librarymanager.model;

import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;

import java.io.Serializable;

import dev.moreko.librarymanager.model.exceptions.NegativeNumberException;

public class Book implements Serializable {
    public static enum BookFields {
        ID (0),
        TITLE (1),
        AUTHOR (2),
        CATEGORY (3),
        PUBLISHER (4),
        PUBLISH_YEAR (5);

        private int index;
        private BookFields(int index) {
            this.index = index;
        }
        public int getIndex() { return this.index; }
        public static BookFields find(int index) {
            return switch(index) {
                case 0 -> ID;
                case 1 -> TITLE;
                case 2 -> AUTHOR;
                case 3 -> CATEGORY;
                case 4 -> PUBLISHER;
                case 5 -> PUBLISH_YEAR;
                default -> ID;
            };
        }
    }

    private String ID;
    private String title;
    private String author;
    private String category;
    private String publisher;
    private int publishYear;
    private boolean isAvailable;

    public Book(String ID, String title, String author, String category, String publisher, int publishYear)
            throws NullOrEmptyValueException, NegativeNumberException {
        this.setID(ID);
        this.setTitle(title);
        this.setAuthor(author);
        this.setCategory(category);
        this.setPublisher(publisher);
        this.setPublishYear(publishYear);
        this.setAvailable(true);
    }

    public void setID(String ID) throws NullOrEmptyValueException {
        Validator.checkNullability(ID);
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public void setTitle(String title) throws NullOrEmptyValueException {
        Validator.checkNullability(title);
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setAuthor(String author) throws NullOrEmptyValueException {
        Validator.checkNullability(author);
        this.author = author;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setCategory(String category) throws NullOrEmptyValueException {
        Validator.checkNullability(category);
        this.category = category;
    }

    public String getCategory() {
        return this.category;
    }

    public void setPublisher(String publisher) throws NullOrEmptyValueException {
        Validator.checkNullability(publisher);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public void setPublishYear(int publishYear) throws NegativeNumberException {
        Validator.checkNegativeNumber(publishYear);
        this.publishYear = publishYear;
    }

    public int getPublishYear() {
        return this.publishYear;
    }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    public boolean isAvailable() {
        return this.isAvailable;
    }
}
