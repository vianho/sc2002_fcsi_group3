package sc2002.fcsi.grp3.session;

import sc2002.fcsi.grp3.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Session class manages the current user session.
 * It provides methods to store, retrieve, and remove session data.
 */
public class Session {

    private static User currentUser;
    private static final Map<String, Object> sessionData = new HashMap<>();

    // User session management

    /**
     * Sets the current user for the session.
     *
     * @param currentUser the user to set as the current user
     */
    public static void setCurrentUser(User currentUser) {
        Session.currentUser = currentUser;
    }

    /**
     * Gets the current user of the session.
     *
     * @return the current user, or null if no user is logged in
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is logged in.
     *
     * @return true if a user is logged in, false otherwise
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Logs out the current user and clears all session data.
     */
    public static void logout() {
        currentUser = null;
        sessionData.clear();
    }

    // Session data management

    /**
     * Stores a value in the session with the specified key.
     *
     * @param key   the key to associate with the value
     * @param value the value to store
     */
    public static void put(String key, Object value) {
        sessionData.put(key, value);
    }

    /**
     * Retrieves a value from the session by its key and type.
     *
     * @param key  the key of the value to retrieve
     * @param type the expected type of the value
     * @param <T>  the type of the value
     * @return the value if it exists and matches the type, or null otherwise
     */
    public static <T> T get(String key, Class<T> type) {
        Object value = sessionData.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }

    /**
     * Sets a value in the session with the specified key.
     *
     * @param key   the key to associate with the value
     * @param value the value to set
     */
    public static void set(String key, Object value) {
        sessionData.put(key, value);
    }

    /**
     * Retrieves a list from the session by its key and element type.
     *
     * @param key  the key of the list to retrieve
     * @param type the expected type of the list elements
     * @param <T>  the type of the list elements
     * @return the list if it exists and matches the element type, or null otherwise
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> getList(String key, Class<T> type) {
        Object value = sessionData.get(key);
        if (value instanceof List<?> rawList) {
            if (rawList.stream().allMatch(type::isInstance)) {
                return (List<T>) rawList;
            }
        }
        return null;
    }

    /**
     * Retrieves a map from the session by its key and key-value types.
     *
     * @param key       the key of the map to retrieve
     * @param keyType   the expected type of the map keys
     * @param valueType the expected type of the map values
     * @param <K>       the type of the map keys
     * @param <V>       the type of the map values
     * @return the map if it exists and matches the key-value types, or null otherwise
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> getMap(String key, Class<K> keyType, Class<V> valueType) {
        Object value = sessionData.get(key);
        if (value instanceof Map<?, ?> map) {
            boolean valid = map.entrySet().stream().allMatch(entry ->
                    keyType.isInstance(entry.getKey()) && valueType.isInstance(value)
            );
            if (valid) {
                return (Map<K, V>) map;
            }
        }
        return null;
    }

    /**
     * Removes a value from the session by its key.
     *
     * @param key the key of the value to remove
     */
    public static void remove(String key) {
        sessionData.remove(key);
    }

    /**
     * Checks if the session contains a value with the specified key.
     *
     * @param key the key to check
     * @return true if the session contains the key, false otherwise
     */
    public static boolean contains(String key) {
        return sessionData.containsKey(key);
    }
}
