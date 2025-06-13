package dev.moreko.librarymanager.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import dev.moreko.librarymanager.theme.Colors;
import dev.moreko.librarymanager.theme.Icons;
import net.miginfocom.swing.MigLayout;

public class BookFormView extends JPanel {
    private String callType = "Add";
    private JButton backButton = new JButton("", Icons.BACK_BUTTON);
    private JTextField IDField = new JTextField();
    private JTextField titleField = new JTextField();
    private JTextField authorField = new JTextField();
    private JTextField categoryField = new JTextField();
    private JTextField publisherField = new JTextField();
    private JSpinner publishYearField = new JSpinner();
    private JButton submitButton = new JButton("Add", Icons.ADD_BUTTON);
    
    public BookFormView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("inset 10", "[grow]", ""));

        backButton.setBackground(Colors.EDIT_BUTTON_COLOR);

        this.add(backButton, "wrap");
        this.add(new JLabel("Book ID:"), "grow, wrap");
        this.add(IDField, "grow, wrap");
        this.add(new JLabel("Book title:"), "grow, wrap");
        this.add(titleField, "grow, wrap");
        this.add(new JLabel("Book author:"), "grow, wrap");
        this.add(authorField, "grow, wrap");
        this.add(new JLabel("Book category:"), "grow, wrap");
        this.add(categoryField, "grow, wrap");
        this.add(new JLabel("Book publisher:"), "grow, wrap");
        this.add(publisherField, "grow, wrap");
        this.add(new JLabel("Book publish year:"), "grow, wrap");
        this.add(publishYearField, "grow, wrap");
        this.add(submitButton, "grow");
    }

    public JButton getBackButton() { return this.backButton; }
    public JTextField getIDField() { return this.IDField; }
    public JTextField getTitleField() { return this.titleField; }
    public JTextField getAuthorField() { return this.authorField; }
    public JTextField getCategoryField() { return this.categoryField; }
    public JTextField getPublisherField() { return this.publisherField; }
    public JSpinner getPublisherYearField() { return this.publishYearField; }
    public JButton getSubmitButton() { return this.submitButton; }
    public void setCallType(String type) {
        if (type.equals("Add")) {
            this.callType = "Add";
            this.submitButton.setText("Add");
            this.submitButton.setIcon(Icons.ADD_BUTTON);
            this.submitButton.setBackground(Colors.ADD_BUTTON_COLOR);
        } else {
            this.callType = "Edit";
            this.submitButton.setText("Edit");
            this.submitButton.setIcon(Icons.EDIT_BUTTON);
            this.submitButton.setBackground(Colors.EDIT_BUTTON_COLOR);
        }
    }
    public String getCallType() {
        return this.callType;
    }
}
