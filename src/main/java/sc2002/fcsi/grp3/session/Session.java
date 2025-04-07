package sc2002.fcsi.grp3.session;

import sc2002.fcsi.grp3.model.User;

import java.util.HashMap;
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
    }

    // session data management

    public static void put(String key, String value) {
        sessionData.put(key, value);
    }

    public static <T> T get(String key, Class<T> type) {
        Object value = sessionData.get(key);
        if (type.isInstance(value)) {
            return type.cast(value);
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
