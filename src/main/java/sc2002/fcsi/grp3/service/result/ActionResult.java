package sc2002.fcsi.grp3.service.result;

/**
 * The ActionResult class represents the result of an action, including its success status, message, and optional data.
 *
 * @param <T> the type of data associated with the result
 */
public class ActionResult<T> {

    private final boolean success;
    private final String message;
    private final T data;

    /**
     * Constructs an ActionResult with the specified success status, message, and data.
     *
     * @param success whether the action was successful
     * @param message a message describing the result
     * @param data    the data associated with the result
     */
    private ActionResult(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    /**
     * Creates a successful ActionResult with a message and associated data.
     *
     * @param message the success message
     * @param data    the data associated with the result
     * @param <T>     the type of data
     * @return a successful ActionResult
     */
    public static <T> ActionResult<T> success(String message, T data) {
        return new ActionResult<>(true, message, data);
    }

    /**
     * Creates a successful ActionResult with a message and no associated data.
     *
     * @param message the success message
     * @param <T>     the type of data
     * @return a successful ActionResult
     */
    public static <T> ActionResult<T> success(String message) {
        return new ActionResult<>(true, message, null);
    }

    /**
     * Creates a failed ActionResult with a message.
     *
     * @param message the failure message
     * @param <T>     the type of data
     * @return a failed ActionResult
     */
    public static <T> ActionResult<T> failure(String message) {
        return new ActionResult<>(false, message, null);
    }

    /**
     * Checks if the action was successful.
     *
     * @return true if the action was successful, false otherwise
     */
    public boolean isSuccess() { return success; }

    /**
     * Gets the message describing the result.
     *
     * @return the result message
     */
    public String getMessage() { return message; }

    /**
     * Gets the data associated with the result.
     *
     * @return the result data
     */
    public T getData() { return data; }
}

