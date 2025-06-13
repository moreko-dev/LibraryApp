package dev.moreko.librarymanager.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.moreko.librarymanager.theme.Colors;
import dev.moreko.librarymanager.theme.Icons;
import net.miginfocom.swing.MigLayout;

public class MemberFormView extends JPanel {
    private String callType = "Add";
    private JButton backButton = new JButton("", Icons.BACK_BUTTON);
    private JTextField IDField = new JTextField();
    private JTextField nameField = new JTextField();
    private JTextField emailField = new JTextField();
    private JTextField phoneField = new JTextField();
    private JButton submitButton = new JButton("Add", Icons.ADD_BUTTON);
    
    public MemberFormView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("inset 10", "[grow]", ""));

        backButton.setBackground(Colors.EDIT_BUTTON_COLOR);

        this.add(backButton, "wrap");
        this.add(new JLabel("Member ID:"), "grow, wrap");
        this.add(IDField, "grow, wrap");
        this.add(new JLabel("Member name:"), "grow, wrap");
        this.add(nameField, "grow, wrap");
        this.add(new JLabel("Member email:"), "grow, wrap");
        this.add(emailField, "grow, wrap");
        this.add(new JLabel("Member phone:"), "grow, wrap");
        this.add(phoneField, "grow, wrap");
        this.add(submitButton, "grow");
    }

    public JButton getBackButton() { return this.backButton; }
    public JTextField getIDField() { return this.IDField; }
    public JTextField getNameField() { return this.nameField; }
    public JTextField getEmailField() { return this.emailField; }
    public JTextField getPhoneField() { return this.phoneField; }
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
