package dev.moreko.librarymanager;

import dev.moreko.librarymanager.config.ConfigLoader;

public class AppConfig {
    private static String base = "src/main/java/dev/moreko/librarymanager/";
    private static String name;
    private static String version;
    private static String theme;

    public static void updateConfig() {
        name = ConfigLoader.getProperty("app.name");
        version = ConfigLoader.getProperty("app.version");
        theme = ConfigLoader.getProperty("app.theme");
    }

    public static String getDirectoryBase() { return base; }
    public static String getApplicationName() { return name; }
    public static String getApplicationVersion() { return version; }
    public static String getApplicationTheme() { return theme; }
}