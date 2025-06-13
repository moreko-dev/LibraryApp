package dev.moreko.librarymanager.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JOptionPane;

import dev.moreko.librarymanager.AppConfig;

public class ConfigLoader {
    private static Properties prop = new Properties();
    private static String propFileName = AppConfig.getDirectoryBase() + "config/config.properties";

    public static void setProperty(String key, String value) {
        try (OutputStream outStream = new FileOutputStream(propFileName)) {
            prop.setProperty(key, value);
            prop.store(outStream, "Configurations");
        } catch (IOException ioe) {
            JOptionPane.showInternalMessageDialog(
                null,
                "Error: " + ioe.getMessage() + "\nConfig file is missing. Running AutoConfig to restore settings...",
                "Error",
                0
            );
            reconfig();
            setProperty(key, value);
        }
    }

    public static String getProperty(String key) {
        String value = "";
        try (InputStream inStream = new FileInputStream(propFileName)) {
            prop.load(inStream);
            value = prop.getProperty(key);
        } catch (IOException ioe) {
            JOptionPane.showInternalMessageDialog(
                null, 
                "Error: " + ioe.getMessage() + "\nConfig file is missing. Running AutoConfig to restore settings...",
                "Error",
                0
            );
            reconfig();
            value = getProperty(key);
        }
        return value;
    }

    public static void reconfig() {
        prop.clear();
        prop.setProperty("app.name", "Moreko library manager");
        prop.setProperty("app.version", "1.0");

        try (OutputStream outStream = new FileOutputStream(propFileName)) {
            prop.store(outStream, "Default configurations");
            JOptionPane.showMessageDialog(
                null, 
                "Application settings restored. App will strat soon.", 
                "Success", 
                0
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null, 
                "Error: " + e.getMessage(), 
                "Error", 
                0
            );
        }
    }
}
