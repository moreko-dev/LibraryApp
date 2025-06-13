package dev.moreko.librarymanager.view;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dev.moreko.librarymanager.theme.Colors;
import dev.moreko.librarymanager.theme.Icons;
import net.miginfocom.swing.MigLayout;

public class BooksView extends JPanel {
    private JPanel toolsPanel = new JPanel(new MigLayout("inset 5", "", "[grow]"));
    private JTable booksTable = new JTable();
    private JTextField searchField = new JTextField();
    private JButton addBookButton = new JButton("", Icons.ADD_BUTTON);
    private JButton editBookButton = new JButton("", Icons.EDIT_BUTTON);
    private JButton deleteBookButton = new JButton("", Icons.DELETE_BUTTON);
    private JComboBox<String> bookSearchType = new JComboBox<>();
    private DefaultTableModel bookModel = new DefaultTableModel(
        new String[] {
            "ID", "Fullname", "Code", "Phone number"
        }, 0
    );
    private JScrollPane tableScroll = new JScrollPane(booksTable);

    public BooksView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("fill, inset 10", "[grow]", "[][grow]"));

        booksTable.setModel(bookModel);

        toolsPanel.setBackground(new Color(0, 0, 0, 0));

        addBookButton.setBackground(Colors.ADD_BUTTON_COLOR);
        editBookButton.setBackground(Colors.EDIT_BUTTON_COLOR);
        deleteBookButton.setBackground(Colors.DELETE_BUTTON_COLOR);
        bookSearchType.setBorder(
            BorderFactory.createCompoundBorder(
                bookSearchType.getBorder(), 
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
            )
        );

        editBookButton.setEnabled(false);
        deleteBookButton.setEnabled(false);

        toolsPanel.add(new JLabel("Books"), "push");
        toolsPanel.add(new JLabel("Search for: "), "");
        toolsPanel.add(searchField, "w 300!");
        toolsPanel.add(bookSearchType, "w 150!, push");
        toolsPanel.add(editBookButton, "");
        toolsPanel.add(deleteBookButton, "");
        toolsPanel.add(addBookButton, "");

        this.add(toolsPanel, "grow, wrap");
        this.add(tableScroll, "grow");

    }

    public JTable getBooksTable() { return this.booksTable; }
    public JTextField getSearchField() { return this.searchField; }
    public JButton getAddBookButton() { return this.addBookButton; }
    public JButton getEditBookButton() { return this.editBookButton; }
    public JButton getDeleteBookButton() { return this.deleteBookButton; }
    public JComboBox<String> getBookSearchType() { return this.bookSearchType; }
    public DefaultTableModel getTableModel() { return this.bookModel; }
}
