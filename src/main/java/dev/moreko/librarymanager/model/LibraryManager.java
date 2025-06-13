package dev.moreko.librarymanager.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dev.moreko.librarymanager.AppConfig;
import dev.moreko.librarymanager.model.Book.BookFields;
import dev.moreko.librarymanager.model.Borrow.BorrowFields;
import dev.moreko.librarymanager.model.Borrow.BorrowStatus;
import dev.moreko.librarymanager.model.Member.MemberFields;
import dev.moreko.librarymanager.model.exceptions.ActionNotAbleException;
import dev.moreko.librarymanager.model.exceptions.DuplicateValueInListException;
import dev.moreko.librarymanager.model.exceptions.NotAvailableBookException;

public class LibraryManager {
    private List<Member> members;
    private List<Book> books;
    private List<Borrow> borrows;
    private String[] membersField = {"ID", "Name", "Email", "Phone"};
    private String[] booksField = {"ID", "Title", "Author", "Category", "Publisher", "Publish year", "Available"};
    private String[] borrowsField = {"ID", "Book ID", "Member ID", "Borrow date", "Due date", "Return date", "Status"};

    public LibraryManager() {
        members = new ArrayList<>();
        books = new ArrayList<>();
        borrows = new ArrayList<>();
    }

    public void addMember(Member member) throws DuplicateValueInListException {
        if (checkMemberInList(member)) {
            throw new DuplicateValueInListException("Duplicate member. Check ID, Phone and Email agian.");
        }
        members.add(member);
    }

    public void removeMember(Member member) throws ActionNotAbleException {
        if (member.getBorrowedBook() != null) {
            throw new ActionNotAbleException("Member has borrowed book.");
        }
        members.removeIf((item) -> item.getID().equals(member.getID()));
    }

    public void removeMember(int index) throws ActionNotAbleException {
        removeMember(members.get(index));
    }

    public void editMember(int index, Member newMember) throws DuplicateValueInListException {
        if (checkMemberInListExcept(getAllMembers().get(index))) {
            throw new DuplicateValueInListException("Duplicate member. Check ID, Phone and Email agian");
        }
        members.set(index, newMember);
    }

    public void addBook(Book book) throws DuplicateValueInListException {
        if (checkBookInList(book)) {
            throw new DuplicateValueInListException("Duplicate book. Check ID agian");
        }
        books.add(book);
    }

    public void removeBook(Book book) throws NotAvailableBookException {
        if (!book.isAvailable()) {
            throw new NotAvailableBookException("Current book is not available to borrow.");
        }
        books.removeIf((item) -> item.getID().equals(book.getID()));
    }

    public void removeBook(int index) throws NotAvailableBookException {
        removeBook(books.get(index));
    }

    public void editBook(int index, Book newBook) throws DuplicateValueInListException {
        if (checkBookInListExcept(getAllBooks().get(index))) {
            throw new DuplicateValueInListException("Duplicate book. Check ID agian");
        }
        books.set(index, newBook);
    }

    public void addBorrow(Borrow borrow) throws DuplicateValueInListException, NotAvailableBookException, ActionNotAbleException {
        if (checkBorrowInList(borrow)) {
            throw new DuplicateValueInListException("Duplicate borrow. Check ID, BookID again.");
        }
        if (!isBookAvailable(borrow.getBookID())) {
            throw new NotAvailableBookException("Current book is not available to borrow.");
        }
        if (!canMemberBorrows(borrow.getMemberID())) {
            throw new ActionNotAbleException("Member has borrowed book.");
        }
        borrows.add(borrow);
        getMemberByID(borrow.getMemberID()).setBorrowedBook(getBookByID(borrow.getBookID()));
        getBookByID(borrow.getBookID()).setAvailable(false);
    }

    public void cancelBorrow(int index) {
        borrows.get(index).cancelBorrow();
        getBookByID(borrows.get(index).getBookID()).setAvailable(true);
        getMemberByID(borrows.get(index).getMemberID()).setBorrowedBook(null);
    }

    public void returnBook(int index) {
        borrows.get(index).returnBook();
        getBookByID(borrows.get(index).getBookID()).setAvailable(true);
        getMemberByID(borrows.get(index).getMemberID()).setBorrowedBook(null);
    }

    public void bookIsLost(int index) {
        borrows.get(index).bookIsLost();
        getBookByID(borrows.get(index).getBookID()).setAvailable(false);
        getMemberByID(borrows.get(index).getMemberID()).setBorrowedBook(null);
    }

    public void editBorrow(int index, Borrow newBorrow) throws DuplicateValueInListException, ActionNotAbleException, NotAvailableBookException {
        if (checkBorrowInListExcept(getAllBorrows().get(index))) {
            throw new DuplicateValueInListException("Duplicate borrow. Check ID, BookID agian");
        }
        if (!isBookAvailable(newBorrow.getBookID())) {
            throw new NotAvailableBookException("Current book is not available to borrow.");
        }
        if (borrows.get(index).getStatus() != BorrowStatus.ACTIVE) {
            throw new ActionNotAbleException("Only active borrows are editable.");
        }
        if (!canMemberBorrows(newBorrow.getMemberID())) {
            throw new ActionNotAbleException("Member has borrowed book.");
        }
        getBookByID(getAllBorrows().get(index).getBookID()).setAvailable(true);
        getMemberByID(getAllBorrows().get(index).getMemberID()).setBorrowedBook(null);
        borrows.set(index, newBorrow);
        getBookByID(newBorrow.getBookID()).setAvailable(false);
        getMemberByID(newBorrow.getMemberID()).setBorrowedBook(getBookByID(newBorrow.getBookID()));
    }

    public String[] getMemberFields() {
        return this.membersField;
    }

    public String[] getBookField() {
        return this.booksField;
    }

    public String[] getBorrowField() {
        return this.borrowsField;
    }

    public List<Member> getAllMembers() {
        return this.members;
    }

    public List<Book> getAllBooks() {
        return this.books;
    }

    public List<Borrow> getAllBorrows() {
        return this.borrows;
    }

    public Member getMemberByID(String ID) {
        return members.stream()
        .filter(member -> member.getID().equals(ID))
        .findFirst()
        .orElse(null);
    }
    
    public Book getBookByID(String ID) {
        return books.stream()
        .filter(book -> book.getID().equals(ID))
        .findFirst()
        .orElse(null);
    }
    
    public Borrow getBorrowByID(String ID) {
        return borrows.stream()
        .filter(borrow -> borrow.getID().equals(ID))
        .findFirst()
        .orElse(null);
    }

    public List<Member> searchMemberBy(String keyword, MemberFields field) {
        return members.stream()
            .filter(member -> switch (field) {
                case ID -> member.getID().startsWith(keyword);
                case NAME -> member.getName().startsWith(keyword);
                case EMAIL -> member.getEmail().startsWith(keyword);
                case PHONE -> member.getPhone().startsWith(keyword);
            })
            .collect(Collectors.toList());
    }

    public List<Book> searchBookBy(String keyword, BookFields field) {
        return books.stream()
            .filter(book -> switch (field) {
                case ID -> book.getID().startsWith(keyword);
                case TITLE -> book.getTitle().startsWith(keyword);
                case AUTHOR -> book.getAuthor().startsWith(keyword);
                case CATEGORY -> book.getCategory().startsWith(keyword);
                case PUBLISHER -> book.getPublisher().startsWith(keyword);
                case PUBLISH_YEAR -> String.valueOf(book.getPublishYear()).startsWith(keyword);
            })
            .collect(Collectors.toList());
    }

    public List<Borrow> searchBorrowBy(String keyword, BorrowFields field) {
        return borrows.stream()
            .filter(borrow -> switch (field) {
                case ID -> borrow.getID().startsWith(keyword);
                case BOOKID -> borrow.getBookID().startsWith(keyword);
                case MEMBERID -> borrow.getMemberID().startsWith(keyword);
                case BORROWDATE -> borrow.getBorrowdDate().startsWith(keyword);
                case DUEDATE -> borrow.getDueDate().startsWith(keyword);
                case RETURNDATE -> borrow.getReturnDate().startsWith(keyword);
                case STATUS -> borrow.getStatus().getStatus().startsWith(keyword);
            })
            .collect(Collectors.toList());
    }

    public int getBooksCount() {
        return this.getAllBooks().size();
    }

    public int getmembersCount() {
        return this.getAllMembers().size();
    }

    public int getBorrowsCount() {
        return this.getAllBorrows().size();
    }

    public int getCancelledBorrowsCount() {
        return (int) this.getAllBorrows().stream()
            .filter(borrow -> borrow.getStatus() == BorrowStatus.CANCELLED)
            .count();
    }

    public int getActiveBorrowsCount() {
        return (int) this.getAllBorrows().stream()
            .filter(borrow -> borrow.getStatus() == BorrowStatus.ACTIVE)
            .count();
    }

    public int getLostBorrowsCount() {
        return (int) this.getAllBorrows().stream()
            .filter(borrow -> borrow.getStatus() == BorrowStatus.LOST)
            .count();
    }

    public int getReturnedBorrowsCount() {
        return (int) this.getAllBorrows().stream()
            .filter(borrow -> borrow.getStatus() == BorrowStatus.RETURNED)
            .count();
    }

    public boolean isBookAvailable(String bookID) {
        return getBookByID(bookID).isAvailable();
    }

    public boolean canMemberBorrows(String memberID) {
        return getMemberByID(memberID).getBorrowedBook() == null;
    }

    public boolean saveData() throws IOException {
        File backFile = new File(AppConfig.getDirectoryBase() + "back/lib.dat");

        if (!backFile.exists()) backFile.createNewFile();

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(backFile));
        oos.writeObject(books);
        oos.writeObject(members);
        oos.writeObject(borrows);
        oos.close();
        return true;
    }

    @SuppressWarnings("unchecked")
    public boolean loadData() throws IOException, ClassNotFoundException {
        File backFile = new File(AppConfig.getDirectoryBase() + "back/lib.dat");

        if (!backFile.exists()) backFile.createNewFile();
        if (backFile.length() == 0) return true;

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(backFile));
        books = (List<Book>) ois.readObject();
        members = (List<Member>) ois.readObject();
        borrows = (List<Borrow>) ois.readObject();
        ois.close();
        return true;
    }

    private boolean checkMemberInList(Member member) {
        return members.stream()
            .anyMatch(item -> 
                member.getID().equals(item.getID()) ||
                member.getPhone().equals(item.getPhone()) ||
                member.getEmail().equals(item.getEmail())
            );
    }

    private boolean checkMemberInListExcept(Member member) {
        return members.stream()
            .filter(item -> !item.equals(member))
            .anyMatch(item ->
                member.getID().equals(item.getID()) ||
                member.getPhone().equals(item.getPhone()) ||
                member.getEmail().equals(item.getEmail())
            );
    }

    private boolean checkBookInList(Book book) {
        return books.stream()
            .anyMatch(item -> book.getID().equals(item.getID())
            );
    }

    private boolean checkBookInListExcept(Book book) {
        return books.stream()
            .filter(item -> !item.equals(book))
            .anyMatch(item -> book.getID().equals(item.getID())
            );
    }

    private boolean checkBorrowInList(Borrow borrow) {
        return borrows.stream()
            .anyMatch(item -> borrow.getID().equals(item.getID())
            );
    }

    private boolean checkBorrowInListExcept(Borrow borrow) {
        return borrows.stream()
            .filter(item -> !item.equals(borrow))
            .anyMatch(item -> borrow.getID().equals(item.getID())
            );
    }
}
