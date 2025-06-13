package dev.moreko.librarymanager.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HelpView extends JPanel {
    
    public HelpView() {
        this.setOpaque(false);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(new JLabel("Hello and welcome to help center."));
    }
}
