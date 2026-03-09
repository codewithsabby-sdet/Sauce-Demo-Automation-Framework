package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    // This will hold all the key-value pairs from config.properties
    private static Properties prop = new Properties();

    /**
     * Loads the config.properties file into memory.
     * It is called automatically when get() is used for the first time.
     */
    private static void loadProperties() {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
            prop.load(fis);
            fis.close();
        } catch (IOException e) {
            System.out.println("Failed to load config.properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Returns the value of a key from config.properties.
     * Example: get("url") → returns "https://www.saucedemo.com/"
     */
    public static String getProperty(String key) {
        // If properties are not loaded yet, load them
        if (prop.isEmpty()) {
            loadProperties();
        }
        return prop.getProperty(key);
    }
}