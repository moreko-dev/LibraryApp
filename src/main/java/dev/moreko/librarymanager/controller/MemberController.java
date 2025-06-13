package dev.moreko.librarymanager.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import dev.moreko.librarymanager.model.Member.MemberFields;
import dev.moreko.librarymanager.model.LibraryManager;
import dev.moreko.librarymanager.model.Member;
import dev.moreko.librarymanager.model.exceptions.ActionNotAbleException;
import dev.moreko.librarymanager.model.exceptions.DuplicateValueInListException;
import dev.moreko.librarymanager.model.exceptions.InvalidEmailException;
import dev.moreko.librarymanager.model.exceptions.InvalidPhoneNumberException;
import dev.moreko.librarymanager.model.exceptions.NullOrEmptyValueException;
import dev.moreko.librarymanager.utils.GraphicUtils;
import dev.moreko.librarymanager.view.MemberFormView;
import dev.moreko.librarymanager.view.MainView;
import dev.moreko.librarymanager.view.MembersView;

public class MemberController extends MouseAdapter implements KeyListener, ListSelectionListener {
    private MainView mainView;
    private MembersView memberView;
    private MemberFormView memberFormView;
    private DefaultTableModel model;
    private LibraryManager lib;

    public MemberController(MainView mainv, MembersView memberv, MemberFormView memberFormv, LibraryManager lib) {
        this.mainView = mainv;
        this.memberView = memberv;
        this.memberFormView = memberFormv;
        this.lib = lib;
        this.model = memberView.getTableModel();
        memberView.getAddMemberButton().addMouseListener(this);
        memberView.getEditMemberButton().addMouseListener(this);
        memberView.getDeleteMemberButton().addMouseListener(this);
        memberView.getSearchField().addKeyListener(this);
        memberView.getMembersTable().getSelectionModel().addListSelectionListener(this);
        memberFormView.getBackButton().addMouseListener(this);
        memberFormView.getSubmitButton().addMouseListener(this);

        for (String item : lib.getMemberFields()) {
            memberView.getMemberSearchType().addItem(item);
        }

        model = new DefaultTableModel(memberList2Array(lib.getAllMembers()), lib.getMemberFields());
        tableReset();
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        Object obj = me.getSource();
        if (obj == memberView.getAddMemberButton()) {

            sendUserToForm("Add");

        } else if (obj == memberView.getEditMemberButton()) {

            if (memberView.getEditMemberButton().isEnabled()) {
                sendUserToForm("Edit");

                int index = memberView.getMembersTable().getSelectedRow();
                Member selectedMember = lib.getAllMembers().get(index);
                memberFormView.getIDField().setText(selectedMember.getID());
                memberFormView.getNameField().setText(selectedMember.getName());
                memberFormView.getEmailField().setText(selectedMember.getEmail());
                memberFormView.getPhoneField().setText(selectedMember.getPhone());
                memberFormView.repaint();
            }

        } else if (obj == memberFormView.getBackButton()) {

            mainView.getAddressLabel().setText("Library App > Members");
            mainView.getMainLayout().show(mainView.getMainPanel(), "members");

        } else if (obj == memberFormView.getSubmitButton()) {

            int index = memberView.getMembersTable().getSelectedRow();
            try {
                String id = memberFormView.getIDField().getText();
                String title = memberFormView.getNameField().getText();
                String author = memberFormView.getEmailField().getText();
                String category = memberFormView.getPhoneField().getText();

                Member newMember = new Member(id, title, author, category);

                if (memberFormView.getCallType().equals("Add")) {
                    lib.addMember(newMember);
                } else {
                    lib.editMember(index, newMember);
                }

                GraphicUtils.successMessage(memberFormView, "Member " + ((memberFormView.getCallType().equals("add")) ? "added" : "edited") + " successfuly.");

                mainView.getMainLayout().show(mainView.getMainPanel(), "members");
                resetFormView();
                if (memberFormView.getCallType().equals("Add")) {
                    model.addRow(member2Array(newMember));
                } else {
                    model.setValueAt(newMember.getID(), index, 0);
                    model.setValueAt(newMember.getName(), index, 1);
                    model.setValueAt(newMember.getEmail(), index, 2);
                    model.setValueAt(newMember.getPhone(), index, 3);
                }
                tableReset();
            } catch (NullOrEmptyValueException noeve) {
                GraphicUtils.errorMessage(memberFormView, "Some values are null or empty.");
            } catch (InvalidEmailException iee) {
                GraphicUtils.errorMessage(memberFormView, "Seems like email is not valid.");
            } catch (InvalidPhoneNumberException ipne) {
                GraphicUtils.errorMessage(memberFormView, "Seems like phone is not valid.");
            } catch (DuplicateValueInListException dvile) {
                GraphicUtils.errorMessage(memberFormView, "Duplicate member ID or Email or Phone.");
            } catch (Exception e) {
                GraphicUtils.errorMessage(memberFormView, "Error: " + e.getMessage());
            }

        } else if (obj == memberView.getDeleteMemberButton()) {

            if (memberView.getDeleteMemberButton().isEnabled()) {
                try {
                    int index = memberView.getMembersTable().getSelectedRow();
                    int result = GraphicUtils.confirm(memberView, "Are you sure to delete this record?");
                    if (result == 0) {
                        lib.removeMember(index);
                        model.removeRow(index);
                        tableReset();
                        GraphicUtils.successMessage(memberView, "Member deleted successfuly.");
                    }
                } catch (ActionNotAbleException anae) {
                    GraphicUtils.errorMessage(memberView, "Member has borrowed book.");
                } catch (Exception e) {
                    GraphicUtils.errorMessage(memberView, "Error: " + e.getMessage());
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
        String keyword = memberView.getSearchField().getText();
        MemberFields type = MemberFields.find(memberView.getMemberSearchType().getSelectedIndex());
        if (keyword.isEmpty()) {
            model = new DefaultTableModel(memberList2Array(lib.getAllMembers()), lib.getMemberFields());
        } else {
            List<Member> foundMembers = lib.searchMemberBy(keyword, type);
            model = new DefaultTableModel(memberList2Array(foundMembers), lib.getMemberFields());
        }
        tableReset();
    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        setEditButtonsEnabled(memberView.getMembersTable().getSelectedRow() != -1);
    }

    public String[][] memberList2Array(List<Member> list) {
        String[][] arr = new String[list.size()][4];
        for (int i = 0; i < arr.length; i++) {
            arr[i][0] = list.get(i).getID();
            arr[i][1] = list.get(i).getName();
            arr[i][2] = list.get(i).getEmail();
            arr[i][3] = list.get(i).getPhone();
        }
        return arr;
    }

    public String[] member2Array(Member member) {
        String[] arr = new String[lib.getMemberFields().length];
        arr[0] = member.getID();
        arr[1] = member.getName();
        arr[2] = member.getEmail();
        arr[3] = member.getPhone();
        return arr;
    }

    public void tableReset() {
        memberView.getMembersTable().setModel(model);
        memberView.getMembersTable().repaint();
    }

    public void setEditButtonsEnabled(boolean enabled) {
        memberView.getEditMemberButton().setEnabled(enabled);
        memberView.getDeleteMemberButton().setEnabled(enabled);
    }

    public void sendUserToForm(String callType) {
        mainView.getAddressLabel().setText("Library App > Members > " + callType + " member");
        memberFormView.setCallType(callType);
        mainView.getMainLayout().show(mainView.getMainPanel(), "member_form");
    }

    public void resetFormView() {
        memberFormView.getIDField().setText("");
        memberFormView.getNameField().setText("");
        memberFormView.getEmailField().setText("");
        memberFormView.getPhoneField().setText("");
        memberFormView.repaint();
    }
}