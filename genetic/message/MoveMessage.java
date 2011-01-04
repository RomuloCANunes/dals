package genetic.message;

/**
 *
 * @author romulo
 */
public class MoveMessage extends ObservableMessage {
    /**
     * Constructor
     * @param message
     */
    public MoveMessage(String message) {
        super(ObservableMessage.TYPE_PLAYPAUSE, message);
    }
}
