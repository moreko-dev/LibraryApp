package dev.moreko.librarymanager;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import dev.moreko.librarymanager.controller.BookController;
import dev.moreko.librarymanager.controller.BorrowController;
import dev.moreko.librarymanager.controller.MainController;
import dev.moreko.librarymanager.controller.MemberController;
import dev.moreko.librarymanager.model.LibraryManager;
import dev.moreko.librarymanager.theme.ThemeManager;
import dev.moreko.librarymanager.theme.ThemeManager.Theme;
import dev.moreko.librarymanager.utils.GraphicUtils;
import dev.moreko.librarymanager.view.MainView;

public class MainApp {
    private static LibraryManager library = new LibraryManager();

    public static void main(String[] args) {
        // Load Configs to AppConfig
        AppConfig.updateConfig();

        // Load backup
        try {
            library.loadData();
        } catch (Exception e) {
            GraphicUtils.errorMessage(null, "Error: " + e.getLocalizedMessage());
        }

        // Theme
        try {
            UIManager.put("Button.margin", new Insets(7, 7, 7, 7));
            UIManager.put("TextField.margin", new Insets(7, 7, 7, 7));
            UIManager.put("Spinner.border", BorderFactory.createCompoundBorder(
                UIManager.getBorder("Spinner.border"), 
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
            ));
            if (AppConfig.getApplicationTheme().equals("light")) {
                ThemeManager.setTheme(Theme.LIGHT);
                UIManager.setLookAndFeel(new FlatLightLaf());
            } else {
                ThemeManager.setTheme(Theme.DARK);
                UIManager.setLookAndFeel(new FlatDarkLaf());
            }
            GraphicUtils.changeGlobalFont(new Font("Segoe UI", Font.PLAIN, 16));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null, 
                "Error: " + e.getMessage(), 
                "Error", 
                0);
        }

        // Start View and Controller
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView(AppConfig.getApplicationName());
            new MainController(mainView, library);
            new MemberController(
                mainView, 
                mainView.getMembersView(), 
                mainView.getMemberFormView(),
                library
            );
            new BookController(
                mainView, 
                mainView.getBooksView(), 
                mainView.getBookFormView(),
                library
            );
            new BorrowController(
                mainView, 
                mainView.getBorrowsView(), 
                mainView.getBorrowFormView(),
                library
            );
            mainView.setVisible(true);
        });
    }
}
