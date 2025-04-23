package sc2002.fcsi.grp3.session;

import sc2002.fcsi.grp3.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Session {
    private static User currentUser;
    private static final Map<String, Object> sessionData = new HashMap<String, Object>();

    // user session management
    public static void setCurrentUser(User currentUser) {
        Session.currentUser = currentUser;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static void logout() {
        currentUser = null;
        sessionData.clear();
    }

    // session data management

    public static void put(String key, Object value) {
        sessionData.put(key, value);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = sessionData.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
        }
        return null;
    }

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

    public static void remove(String key) {
        sessionData.remove(key);
    }

    public static boolean contains(String key) {
        return sessionData.containsKey(key);
    }
}
