package world.net;

import genetic.EnvironmentSerializable;
import genetic.net.XMPPConnectionHandler;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import world.view.WorldManagement;

/**
 * @todo send information to the WorldView when the status message is received
 * @author romulo
 */
public class WorldProtocol implements MessageListener {
    private static String PREFIX_STATUS = "<genetic.EnvironmentSerializable>";
    private WorldManagement view;

    /**
     * default constructor
     * @param view
     */
    public WorldProtocol(WorldManagement view) {
        this.view = view;
    }

    /**
     * Message broker that idenfy a message and calls the apropriated method to
     * handle it
     * @param chat
     * @param msg
     */
    public void processMessage(Chat chat, Message msg) {
        String message = HTMLEntitiesUnescape(msg.getBody());
        System.out.println("message arrived from: " + XMPPConnectionHandler.extractUsername(msg.getFrom()));
        if (message.startsWith(PREFIX_STATUS)) {
            processStatusMessage(XMPPConnectionHandler.extractUsername(msg.getFrom()), message);
        } else {
            System.out.println("Other message received: " + message);
        }
    }

    /**
     * Takes a String with html entities (&lt; &gt;) and reverts it to < and >
     * @param escapedString
     * @return
     */
    private String HTMLEntitiesUnescape(String escapedString) {
        String htmlString = escapedString;
        htmlString = htmlString.replace("&lt;", "<");
        htmlString = htmlString.replace("&gt;", ">");
        return htmlString;
    }

    /**
     * Handles status messages received from monitored Environments
     * @param user
     * @param message
     */
    private void processStatusMessage(String user, String message) {
        view.updateEnvironmentInfo(user, unserializeEnvironment(message));
    }

    /**
     * Unserializes an EnvironmentSerializable based on a XML string
     * @param message
     */
    private EnvironmentSerializable unserializeEnvironment(String message) {
        EnvironmentSerializable env = EnvironmentSerializable.unserializeXML(message);
        return env;
    }
}
