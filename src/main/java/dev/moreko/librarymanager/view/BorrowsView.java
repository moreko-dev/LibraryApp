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

public class BorrowsView extends JPanel {
    private JPanel toolsPanel = new JPanel(new MigLayout("inset 5", "", "[grow]"));
    private JTable borrowsTable = new JTable();
    private JTextField searchField = new JTextField();
    private JButton addBorrowButton = new JButton("", Icons.ADD_BUTTON);
    private JButton editBorrowButton = new JButton("", Icons.EDIT_BUTTON);
    private JComboBox<String> borrowSearchType = new JComboBox<>();
    private DefaultTableModel borrowModel = new DefaultTableModel(
        new String[] {
            "ID", "Fullname", "Code", "Phone number"
        }, 0
    );
    private JScrollPane tableScroll = new JScrollPane(borrowsTable);

    public BorrowsView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("fill, inset 10", "[grow]", "[][grow]"));

        borrowsTable.setModel(borrowModel);

        toolsPanel.setBackground(new Color(0, 0, 0, 0));

        addBorrowButton.setBackground(Colors.ADD_BUTTON_COLOR); 
        editBorrowButton.setBackground(Colors.EDIT_BUTTON_COLOR);
        borrowSearchType.setBorder(
            BorderFactory.createCompoundBorder(
                borrowSearchType.getBorder(), 
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
            )
        );

        editBorrowButton.setEnabled(false);

        toolsPanel.add(new JLabel("Borrows"), "push");
        toolsPanel.add(new JLabel("Search for: "), "");
        toolsPanel.add(searchField, "w 300!");
        toolsPanel.add(borrowSearchType, "w 150!, push");
        toolsPanel.add(editBorrowButton, "");
        toolsPanel.add(addBorrowButton, "");

        this.add(toolsPanel, "grow, wrap");
        this.add(tableScroll, "grow");
    }

    public JTable getBorrowsTable() { return this.borrowsTable; }
    public JTextField getSearchField() { return this.searchField; }
    public JButton getAddBorrowButton() { return this.addBorrowButton; }
    public JButton getEditBorrowButton() { return this.editBorrowButton; }
    public JComboBox<String> getBorrowSearchType() { return this.borrowSearchType; }
    public DefaultTableModel getTableModel() { return this.borrowModel; }
}
