package dev.moreko.librarymanager.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dev.moreko.librarymanager.model.Book;
import dev.moreko.librarymanager.model.LibraryManager;
import dev.moreko.librarymanager.model.Book.BookFields;
import dev.moreko.librarymanager.model.exceptions.DuplicateValueInListException;
import dev.moreko.librarymanager.model.exceptions.NegativeNumberException;
import dev.moreko.librarymanager.model.exceptions.NotAvailableBookException;
import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;
import dev.moreko.librarymanager.utils.GraphicUtils;
import dev.moreko.librarymanager.view.BookFormView;
import dev.moreko.librarymanager.view.BooksView;
import dev.moreko.librarymanager.view.MainView;

public class BookController extends MouseAdapter implements KeyListener, ListSelectionListener {
    private MainView mainView;
    private BooksView bookView;
    private BookFormView bookFormView;
    private DefaultTableModel model;
    private LibraryManager lib;

    public BookController(MainView mainv, BooksView bookv, BookFormView bookFormv, LibraryManager lib) {
        this.mainView = mainv;
        this.bookView = bookv;
        this.bookFormView = bookFormv;
        this.lib = lib;
        this.model = bookView.getTableModel();
        bookView.getAddBookButton().addMouseListener(this);
        bookView.getEditBookButton().addMouseListener(this);
        bookView.getDeleteBookButton().addMouseListener(this);
        bookView.getSearchField().addKeyListener(this);
        bookView.getBooksTable().getSelectionModel().addListSelectionListener(this);
        bookFormView.getBackButton().addMouseListener(this);
        bookFormView.getSubmitButton().addMouseListener(this);

        for (String item : lib.getBookField()) {
            if (!item.equals("Available")) {
                bookView.getBookSearchType().addItem(item);
            }
        }

        model = new DefaultTableModel(bookList2Array(lib.getAllBooks()), lib.getBookField());
        tableReset();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Object obj = me.getSource();
        if (obj == bookView.getAddBookButton()) {

            sendUserToForm("Add");

        } else if (obj == bookView.getEditBookButton()) {

            if (bookView.getEditBookButton().isEnabled()) {
                sendUserToForm("Edit");

                int index = bookView.getBooksTable().getSelectedRow();
                Book selectedBook = lib.getAllBooks().get(index);
                bookFormView.getIDField().setText(selectedBook.getID());
                bookFormView.getTitleField().setText(selectedBook.getTitle());
                bookFormView.getAuthorField().setText(selectedBook.getAuthor());
                bookFormView.getCategoryField().setText(selectedBook.getCategory());
                bookFormView.getPublisherField().setText(selectedBook.getPublisher());
                bookFormView.getPublisherYearField().setValue(selectedBook.getPublishYear());
                bookFormView.repaint();
            }

        } else if (obj == bookFormView.getBackButton()) {

            mainView.getAddressLabel().setText("Library App > Books");
            mainView.getMainLayout().show(mainView.getMainPanel(), "books");

        } else if (obj == bookFormView.getSubmitButton()) {

            int index = bookView.getBooksTable().getSelectedRow();
            try {
                String id = bookFormView.getIDField().getText();
                String title = bookFormView.getTitleField().getText();
                String author = bookFormView.getAuthorField().getText();
                String category = bookFormView.getCategoryField().getText();
                String publisher = bookFormView.getPublisherField().getText();
                int publishYear = (int) bookFormView.getPublisherYearField().getValue();

                Book newBook = new Book(id, title, author, category, publisher, publishYear);

                if (bookFormView.getCallType().equals("Add")) {
                    lib.addBook(newBook);
                } else {
                    lib.editBook(index, newBook);
                }

                GraphicUtils.successMessage(bookFormView, "Book " + ((bookFormView.getCallType().equals("Add")) ? "added" : "edited") + " successfuly.");

                mainView.getMainLayout().show(mainView.getMainPanel(), "books");
                resetFormView();
                if (bookFormView.getCallType().equals("Add")) {
                    model.addRow(book2Array(newBook));
                } else {
                    model.setValueAt(newBook.getID(), index, 0);
                    model.setValueAt(newBook.getTitle(), index, 1);
                    model.setValueAt(newBook.getAuthor(), index, 2);
                    model.setValueAt(newBook.getCategory(), index, 3);
                    model.setValueAt(newBook.getPublisher(), index, 4);
                    model.setValueAt(newBook.getPublishYear(), index, 5);
                    model.setValueAt(newBook.isAvailable() ? "Available" : "Not available", index, 6);
                }
                tableReset();
            } catch (NullOrEmptyValueException noeve) {
                GraphicUtils.errorMessage(bookFormView, "Some values are null or empty.");
            } catch (NegativeNumberException nne) {
                GraphicUtils.errorMessage(bookFormView, "Seems like publish year is negative or not set.");
            } catch (DuplicateValueInListException dvile) {
                GraphicUtils.errorMessage(bookFormView, "Duplicate book ID.");
            } catch (Exception e) {
                GraphicUtils.errorMessage(bookFormView, "Error: " + e.getMessage());
            }

        } else if (obj == bookView.getDeleteBookButton()) {
           
            if (bookView.getDeleteBookButton().isEnabled()) {
                try {
                    int index = bookView.getBooksTable().getSelectedRow();
                    int result = GraphicUtils.confirm(bookView, "Are you sure to delete this record?");
                    if (result == 0) {
                        lib.removeBook(index);
                        model.removeRow(index);
                        tableReset();
                        GraphicUtils.successMessage(bookView, "Book deleted successfuly.");
                    }
                } catch (NotAvailableBookException nabe) {
                    GraphicUtils.errorMessage(bookView, "Non-existence book cannot be deleted.");
                } catch (Exception e) {
                    GraphicUtils.errorMessage(bookView, "Error: " + e.getMessage());
                }
            }
    
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {
        String keyword = bookView.getSearchField().getText();
        BookFields type = BookFields.find(bookView.getBookSearchType().getSelectedIndex());
        if (keyword.isEmpty()) {
            model = new DefaultTableModel(bookList2Array(lib.getAllBooks()), lib.getBookField());
        } else {
            List<Book> foundBooks = lib.searchBookBy(keyword, type);
            model = new DefaultTableModel(bookList2Array(foundBooks), lib.getBookField());
        }
        tableReset();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        setEditButtonsEnabled(bookView.getBooksTable().getSelectedRow() != -1);
    }
    
    public String[][] bookList2Array(List<Book> list) {
        String[][] arr = new String[list.size()][7];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = list.get(i).getID();
            arr[i][1] = list.get(i).getTitle();
            arr[i][2] = list.get(i).getAuthor();
            arr[i][3] = list.get(i).getCategory();
            arr[i][4] = list.get(i).getPublisher();
            arr[i][5] = String.valueOf(list.get(i).getPublishYear());
            arr[i][6] = list.get(i).isAvailable() ? "Available" : "Not available";
        }
        return arr;
    }

    public String[] book2Array(Book book) {
        String[] arr = new String[lib.getBookField().length];
        arr[0] = book.getID();
        arr[1] = book.getTitle();
        arr[2] = book.getAuthor();
        arr[3] = book.getCategory();
        arr[4] = book.getPublisher();
        arr[5] = String.valueOf(book.getPublishYear());
        arr[6] = book.isAvailable() ? "Available" : "Not available";
        return arr;
    }

    public void tableReset() {
        bookView.getBooksTable().setModel(model);
        bookView.getBooksTable().repaint();
    }

    public void setEditButtonsEnabled(boolean enabled) {
        bookView.getEditBookButton().setEnabled(enabled);
        bookView.getDeleteBookButton().setEnabled(enabled);
    }

    public void sendUserToForm(String callType) {
        mainView.getAddressLabel().setText("Library App > Books > " + callType + " book");
        bookFormView.setCallType(callType);
        mainView.getMainLayout().show(mainView.getMainPanel(), "book_form");
    }

    public void resetFormView() {
        bookFormView.getIDField().setText("");
        bookFormView.getTitleField().setText("");
        bookFormView.getAuthorField().setText("");
        bookFormView.getCategoryField().setText("");
        bookFormView.getPublisherField().setText("");
        bookFormView.getPublisherYearField().setValue(0);
        bookFormView.repaint();
    }
}