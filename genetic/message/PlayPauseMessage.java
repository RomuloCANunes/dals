package genetic.message;

/**
 *
 * @author romulo
 */
public class PlayPauseMessage extends ObservableMessage {
    /**
     * Constructor
     * @param message
     */
    public PlayPauseMessage(String message) {
        super(ObservableMessage.TYPE_PLAYPAUSE, message);
    }
}
