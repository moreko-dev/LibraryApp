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

public class MembersView extends JPanel {
    private JPanel toolsPanel = new JPanel(new MigLayout("inset 5", "", "[grow]"));
    private JTable membersTable = new JTable();
    private JTextField searchField = new JTextField();
    private JButton addMemberButton = new JButton("", Icons.ADD_BUTTON);
    private JButton editMemberButton = new JButton("", Icons.EDIT_BUTTON);
    private JButton deleteMemberButton = new JButton("", Icons.DELETE_BUTTON);
    private JComboBox<String> memberSearchType = new JComboBox<>();
    private DefaultTableModel memberModel = new DefaultTableModel(
        new String[] {
            "ID", "Fullname", "Code", "Phone number"
        }, 0
    );
    private JScrollPane tableScroll = new JScrollPane(membersTable);

    public MembersView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new MigLayout("fill, inset 10", "[grow]", "[][grow]"));

        membersTable.setModel(memberModel);

        toolsPanel.setBackground(new Color(0, 0, 0, 0));

        addMemberButton.setBackground(Colors.ADD_BUTTON_COLOR);
        editMemberButton.setBackground(Colors.EDIT_BUTTON_COLOR);
        deleteMemberButton.setBackground(Colors.DELETE_BUTTON_COLOR);
        memberSearchType.setBorder(
            BorderFactory.createCompoundBorder(
                memberSearchType.getBorder(), 
                BorderFactory.createEmptyBorder(7, 7, 7, 7)
            )
        );

        editMemberButton.setEnabled(false);
        deleteMemberButton.setEnabled(false);

        toolsPanel.add(new JLabel("Members"), "push");
        toolsPanel.add(new JLabel("Search for: "), "");
        toolsPanel.add(searchField, "w 300!");
        toolsPanel.add(memberSearchType, "w 150!, push");
        toolsPanel.add(editMemberButton, "");
        toolsPanel.add(deleteMemberButton, "");
        toolsPanel.add(addMemberButton, "");

        this.add(toolsPanel, "grow, wrap");
        this.add(tableScroll, "grow");
    }

    public JTable getMembersTable() { return this.membersTable; }
    public JTextField getSearchField() { return this.searchField; }
    public JButton getAddMemberButton() { return this.addMemberButton; }
    public JButton getEditMemberButton() { return this.editMemberButton; }
    public JButton getDeleteMemberButton() { return this.deleteMemberButton; }
    public JComboBox<String> getMemberSearchType() { return this.memberSearchType; }
    public DefaultTableModel getTableModel() { return this.memberModel; }
}
