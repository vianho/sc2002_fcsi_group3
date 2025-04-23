package sc2002.fcsi.grp3.service.result;

public class ActionResult<T> {
    private final boolean success;
    private final String message;
    private final T data;

    private ActionResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> ActionResult<T> success(String message, T data) {
        return new ActionResult<>(true, message, data);
    }

    public static <T> ActionResult<T> success(String message) {
        return new ActionResult<>(true, message, null);
    }

    public static <T> ActionResult<T> failure(String message) {
        return new ActionResult<>(false, message, null);
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public T getData() { return data; }
}

