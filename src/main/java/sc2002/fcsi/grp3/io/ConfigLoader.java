package sc2002.fcsi.grp3.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The ConfigLoader class is responsible for loading configuration properties from a resource file.
 * It provides methods to retrieve configuration values by key.
 */
public class ConfigLoader {

    private final Properties props = new Properties();

    /**
     * Constructs a ConfigLoader and loads properties from the specified resource file.
     *
     * @param resourceName the name of the resource file to load
     */
    public ConfigLoader(String resourceName) {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (input == null) {
                System.out.println("Config file not found in resources: " + resourceName);
                return;
            }
            props.load(input);
        } catch (IOException e) {
            System.out.println("Failed to load config: " + e.getMessage());
        }
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key to look up
     * @return the value associated with the key, or null if the key is not found
     */
    public String get(String key) {
        return props.getProperty(key);
    }

    /**
     * Retrieves the value associated with the specified key, or a default value if the key is not found.
     *
     * @param key          the key to look up
     * @param defaultValue the default value to return if the key is not found
     * @return the value associated with the key, or the default value if the key is not found
     */
    public String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
