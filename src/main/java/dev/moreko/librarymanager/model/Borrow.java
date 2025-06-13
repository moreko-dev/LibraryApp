package dev.moreko.librarymanager.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;

public class Borrow implements Serializable {
    public static enum BorrowStatus {
        ACTIVE ("Active"),
        RETURNED ("Returned"),
        LOST ("Lost"),
        CANCELLED ("Cancelled");

        private String status;

        private BorrowStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }

    public static enum BorrowFields {
        ID (0),
        BOOKID (1),
        MEMBERID (2),
        BORROWDATE (3),
        DUEDATE (4),
        RETURNDATE (5),
        STATUS (6);

        private int index;
        private BorrowFields(int index) {
            this.index = index;
        }

        public int getIndex() { return this.index; }
        public static BorrowFields find(int index) {
            return switch(index) {
                case 0 -> ID;
                case 1 -> BOOKID;
                case 2 -> MEMBERID;
                case 3 -> BORROWDATE;
                case 4 -> DUEDATE;
                case 5 -> RETURNDATE;
                case 6 -> STATUS;
                default -> ID;
            };
        }
    }

    private String ID;
    private String bookID;
    private String memberID;
    private String borrowDate;
    private String dueDate;
    private String returnDate;
    private BorrowStatus status;
    private transient DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String nullDate = "0000-00-00";

    public Borrow(String ID, String memberID, String bookID) throws NullOrEmptyValueException {
        this.setID(ID);
        this.setMemberID(memberID);
        this.setBookID(bookID);
        this.setStatus(BorrowStatus.ACTIVE);
        this.borrowDate = LocalDateTime.now().format(formatter);
        this.dueDate = LocalDateTime.now().plusDays(14).format(formatter);
        this.returnDate = nullDate;
    }

    public void setID(String ID) throws NullOrEmptyValueException {
        Validator.checkNullability(ID);
        this.ID = ID;
    }

    public String getID() {
        return this.ID;
    }

    public void setBookID(String ID) throws NullOrEmptyValueException {
        Validator.checkNullability(ID);
        this.bookID = ID;
    }

    public String getBookID() {
        return this.bookID;
    }

    public void setMemberID(String ID) throws NullOrEmptyValueException {
        Validator.checkNullability(ID);
        this.memberID = ID;
    }

    public String getMemberID() {
        return this.memberID;
    }

    public void setStatus(BorrowStatus status) {
        if (status != null) this.status = status;
    }

    public BorrowStatus getStatus() {
        return this.status;
    }

    public String getBorrowdDate() {
        return this.borrowDate;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public String getReturnDate() {
        return this.returnDate;
    }

    public void returnBook() {
        this.returnDate = LocalDateTime.now().format(formatter);
        this.setStatus(BorrowStatus.RETURNED);
    }

    public void bookIsLost() {
        this.returnDate = nullDate;
        this.setStatus(BorrowStatus.LOST);
    }

    public void cancelBorrow() {
        this.returnDate = nullDate;
        this.setStatus(BorrowStatus.CANCELLED);
    }
}
