package dev.moreko.librarymanager.utils;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class GraphicUtils {
    
    public static void setFrameMaximized(Frame frame) {
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        int height = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
        frame.setSize(new Dimension(width, height));
    }

    public static void changeGlobalFont(Font font) {
        UIManager.put("Label.font", font);
        UIManager.put("Button.font", font);
        UIManager.put("CheckBox.font", font);
        UIManager.put("Label.font", font);
        UIManager.put("Table.font", font);
        UIManager.put("TextField.font", font);  
        UIManager.put("TableHeader.font", font);
    }

    public static void successMessage(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Success", 1);
    }

    public static void errorMessage(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Success", 0);
    }

    public static void warningMessage(Component parent, String msg) {
        JOptionPane.showMessageDialog(parent, msg, "Success", 2);
    }

    public static int confirm(Component parent, String msg) {
        return JOptionPane.showConfirmDialog(parent, msg, "Confirm", 0, 2);
    }
}
