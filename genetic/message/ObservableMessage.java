package genetic.message;

/**
 * A message class for comunication btween Observers and Observables
 * @author romulo
 */
public abstract class ObservableMessage {

    /**
     * Defines the type of this ObservableMessage
     */
    protected String type;
    /**
     * Defines the actual message
     */
    protected String message;
    /**
     * Type of ObservableMessage
     */
    public static String TYPE_PLAYPAUSE = "play_pause";
    /**
     * Type of ObservableMessage
     */
    public static String TYPE_MOVE = "move";

    /**
     * Default constructor
     * @param type
     * @param message
     */
    public ObservableMessage(String type, String message) {
        this.type = type;
        this.message = message;
    }

    /**
     * Retrieves the message
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     * Retrieves the type
     * @return
     */
    public String getType() {
        return type;
    }
}
