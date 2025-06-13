package dev.moreko.librarymanager.comp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import dev.moreko.librarymanager.theme.ThemeManager;

public class SidebarItem extends JButton {

    public SidebarItem(String text, Icon icon) {
        super(text, icon);
        setHorizontalAlignment(SwingConstants.LEFT);
        setVerticalAlignment(SwingConstants.CENTER);
        setIconTextGap(10);
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(new Color(0, 0, 0, 0));
        setMargin(new Insets(0, 0, 0, 0));
        setPreferredSize(new Dimension(180, 40));
        setFont(new Font("Segoe UI", Font.PLAIN, 16));

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                setBackground(ThemeManager.getSidebarItemHover());
            }

            public void mouseExited(MouseEvent me) {
                setBackground(new Color(0, 0, 0, 0));
            }
        });
    }
}
