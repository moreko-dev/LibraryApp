package dev.moreko.librarymanager.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import dev.moreko.librarymanager.model.LibraryManager;
import dev.moreko.librarymanager.utils.GraphicUtils;
import dev.moreko.librarymanager.view.MainView;

public class MainController extends MouseAdapter {
    private MainView view;
    private LibraryManager lib;

    public MainController(MainView view, LibraryManager lib) {
        this.view = view;
        this.lib = lib;
        view.getDashboardButton().addMouseListener(this);
        view.getMembersButton().addMouseListener(this);
        view.getBooksButton().addMouseListener(this);
        view.getBorrowsButton().addMouseListener(this);
        view.getHelpButton().addMouseListener(this);
        view.getSaveButton().addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Object obj = me.getSource();

        if (obj == view.getSaveButton()) {
            try {
                lib.saveData();
                GraphicUtils.successMessage(view, "Data saved successfuly.");
            } catch (Exception e) {
                GraphicUtils.errorMessage(view, "An error occured while saving data.");
            }
        } else if (obj == view.getDashboardButton()) {
            view.getAddressLabel().setText("Library App > Dashboard");
            view.getMainLayout().show(view.getMainPanel(), "dashboard");
        } else if (obj == view.getMembersButton()) {
            view.getAddressLabel().setText("Library App > Members");
            view.getMainLayout().show(view.getMainPanel(), "members");
        } else if (obj == view.getBooksButton()) {
            view.getAddressLabel().setText("Library App > Books");
            view.getMainLayout().show(view.getMainPanel(), "books");
        } else if (obj == view.getBorrowsButton()) {
            view.getAddressLabel().setText("Library App > Borrows");
            view.getMainLayout().show(view.getMainPanel(), "borrows");
        } else if (obj == view.getHelpButton()) {
            view.getAddressLabel().setText("Library App > Help");
            view.getMainLayout().show(view.getMainPanel(), "help");
        } else {
            JOptionPane.showMessageDialog(view, "Invalid component.", "Error", 0);
        }
    }
}
