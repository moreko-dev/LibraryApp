package dev.moreko.librarymanager.theme;

import java.awt.Color;

// Manage the colors that UIManager doesn't manage
public class ThemeManager {
    public static enum Theme {
        LIGHT, DARK
    }

    private static Theme currentTheme = Theme.LIGHT;

    public static void setTheme(Theme theme) {
        currentTheme = theme;
    }

    public static Theme getTheme() {
        return currentTheme;
    }

    public static Color getTopPanelBackground() {
        return (getTheme() == Theme.LIGHT) 
        ? new Color(245, 245, 245) 
        : new Color(30, 30, 30);
    }

    public static Color getSidePanelBackground() {
        return (getTheme() == Theme.LIGHT) 
        ? new Color(220, 220, 220) 
        : new Color(45, 45, 45);
    }

    public static Color getMainPanelBackground() {
        return (currentTheme == Theme.LIGHT)  
        ? Color.WHITE 
        : new Color(25, 25, 25);
    }

    public static Color getButtonsIconColor() {
        return (currentTheme == Theme.LIGHT)
        ? new Color(60, 60, 60)
        :new Color(220, 220, 220);
    }

    public static Color getSidebarItemHover() {
        return (currentTheme == Theme.LIGHT)
        ? new Color(220, 220, 220)
        : new Color(30, 30, 30);
    }
}
