package sc2002.fcsi.grp3.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private final Properties props = new Properties();

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

    public String get(String key) {
        return props.getProperty(key);
    }

    public String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
