package dev.moreko.librarymanager.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dev.moreko.librarymanager.model.Borrow;
import dev.moreko.librarymanager.model.LibraryManager;
import dev.moreko.librarymanager.model.Borrow.BorrowFields;
import dev.moreko.librarymanager.model.Borrow.BorrowStatus;
import dev.moreko.librarymanager.model.exceptions.DuplicateValueInListException;
import dev.moreko.librarymanager.model.exceptions.NotAvailableBookException;
import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;
import dev.moreko.librarymanager.utils.GraphicUtils;
import dev.moreko.librarymanager.view.BorrowFormView;
import dev.moreko.librarymanager.view.BorrowsView;
import dev.moreko.librarymanager.view.MainView;

public class BorrowController extends MouseAdapter implements KeyListener, ListSelectionListener {
    private MainView mainView;
    private BorrowsView borrowsView;
    private BorrowFormView borrowFormView;
    private DefaultTableModel model;
    private LibraryManager lib;

    public BorrowController(MainView mainv, BorrowsView borrowv, BorrowFormView borrowFormv, LibraryManager lib) {
        this.mainView = mainv;
        this.borrowsView = borrowv;
        this.borrowFormView = borrowFormv;
        this.lib = lib;
        this.model = borrowsView.getTableModel();
        borrowsView.getAddBorrowButton().addMouseListener(this);
        borrowsView.getEditBorrowButton().addMouseListener(this);
        borrowsView.getSearchField().addKeyListener(this);
        borrowsView.getBorrowsTable().getSelectionModel().addListSelectionListener(this);
        borrowFormView.getBackButton().addMouseListener(this);
        borrowFormView.getSubmitButton().addMouseListener(this);

        for (String item : lib.getBorrowField()) {
            borrowsView.getBorrowSearchType().addItem(item);
        }

        model = new DefaultTableModel(borrowList2Array(lib.getAllBorrows()), lib.getBorrowField());
        tableReset();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Object obj = me.getSource();
        if (obj == borrowsView.getAddBorrowButton()) {

            sendUserToForm("Add");

        } else if (obj == borrowsView.getEditBorrowButton()) {

            if (borrowsView.getEditBorrowButton().isEnabled()) {
                int index = borrowsView.getBorrowsTable().getSelectedRow();
                if (lib.getAllBorrows().get(index).getStatus() == BorrowStatus.ACTIVE) {

                    sendUserToForm("Edit");

                    Borrow selectedMember = lib.getAllBorrows().get(index);
                    borrowFormView.getIDField().setText(selectedMember.getID());
                    borrowFormView.getBookIDField().setText(selectedMember.getBookID());
                    borrowFormView.getMemberIDField().setText(selectedMember.getMemberID());
                    borrowFormView.repaint();
                } else {
                    GraphicUtils.errorMessage(borrowsView, "Non-active borrows are not editable.");
                }
            }

        } else if (obj == borrowFormView.getBackButton()) {

            mainView.getAddressLabel().setText("Library App > Borrows");
            mainView.getMainLayout().show(mainView.getMainPanel(), "borrows");

        } else if (obj == borrowFormView.getSubmitButton()) {

            int index = borrowsView.getBorrowsTable().getSelectedRow();
            try {
                String id = borrowFormView.getIDField().getText();
                String bookID = borrowFormView.getBookIDField().getText();
                String memberID = borrowFormView.getMemberIDField().getText();

                Borrow newBorrow = new Borrow(id, memberID, bookID);

                if (borrowFormView.getCallType().equals("Add")) {
                    lib.addBorrow(newBorrow);
                } else {
                    lib.editBorrow(index, newBorrow);
                }

                GraphicUtils.successMessage(borrowFormView, "Borrow " + ((borrowFormView.getCallType().equals("add")) ? "added" : "edited") + " successfuly.");

                mainView.getMainLayout().show(mainView.getMainPanel(), "borrows");
                resetFormView();
                if (borrowFormView.getCallType().equals("Add")) {
                    model.addRow(borrow2Array(newBorrow));
                } else {
                    model.setValueAt(newBorrow.getID(), index, 0);
                    model.setValueAt(newBorrow.getBookID(), index, 1);
                    model.setValueAt(newBorrow.getMemberID(), index, 2);
                }
                tableReset();
            } catch (NullOrEmptyValueException noeve) {
                GraphicUtils.errorMessage(borrowFormView, "Some values are null or empty.");
            } catch (DuplicateValueInListException dvile) {
                GraphicUtils.errorMessage(borrowFormView, "Duplicate borrow ID.");
            } catch (NotAvailableBookException nabe) {
                GraphicUtils.errorMessage(borrowFormView, "The selected book is borrowed or not available.");
            } catch (Exception e) {
                GraphicUtils.errorMessage(borrowFormView, "Error: " + e.getMessage());
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void keyReleased(KeyEvent ke) {
        String keyword = borrowsView.getSearchField().getText();
        BorrowFields type = BorrowFields.find(borrowsView.getBorrowSearchType().getSelectedIndex());
        if (keyword.isEmpty()) {
            model = new DefaultTableModel(borrowList2Array(lib.getAllBorrows()), lib.getBorrowField());
        } else {
            List<Borrow> foundBorrow = lib.searchBorrowBy(keyword, type);
            model = new DefaultTableModel(borrowList2Array(foundBorrow), lib.getBorrowField());
        }
        tableReset();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        setEditButtonsEnabled(borrowsView.getBorrowsTable().getSelectedRow() == -1);
    }

    public String[][] borrowList2Array(List<Borrow> list) {
        String[][] arr = new String[list.size()][7];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = list.get(i).getID();
            arr[i][1] = list.get(i).getBookID();
            arr[i][2] = list.get(i).getMemberID();
            arr[i][3] = list.get(i).getBorrowdDate();
            arr[i][4] = list.get(i).getDueDate();
            arr[i][5] = list.get(i).getReturnDate();
            arr[i][6] = list.get(i).getStatus().getStatus();
        }
        return arr;
    }

    public String[] borrow2Array(Borrow borrow) {
        String[] arr = new String[lib.getBorrowField().length];
        arr[0] = borrow.getID();
        arr[1] = borrow.getBookID();
        arr[2] = borrow.getMemberID();
        arr[3] = borrow.getBorrowdDate();
        arr[4] = borrow.getDueDate();
        arr[5] = borrow.getReturnDate();
        arr[6] = borrow.getStatus().getStatus();
        return arr;
    }

    public void tableReset() {
        borrowsView.getBorrowsTable().setModel(model);
        borrowsView.getBorrowsTable().repaint();
    }

    public void setEditButtonsEnabled(boolean enabled) {
        borrowsView.getEditBorrowButton().setEnabled(enabled);
    }

    public void sendUserToForm(String callType) {
        mainView.getAddressLabel().setText("Library App > Borrows > " + callType + " borrow");
        borrowFormView.setCallType(callType);
        mainView.getMainLayout().show(mainView.getMainPanel(), "borrow_form");
    }

    public void resetFormView() {
        borrowFormView.getIDField().setText("");
        borrowFormView.getBookIDField().setText("");
        borrowFormView.getMemberIDField().setText("");
        borrowFormView.repaint();
    }
}