package world.net;

import genetic.net.*;
import org.jivesoftware.smack.*;
import world.view.WorldManagement;

/**
 * This class creates a XMPP connection and binds a EnvironmentProtocol for
 * processing incomming messages
 * @author romulo
 */
public class WorldXMPPConnectionHandler extends XMPPConnectionHandler {

    private WorldManagement view;

    /**
     * Default constructor
     * @param view
     */
    public WorldXMPPConnectionHandler(WorldManagement view) {
        this.view = view;
    }

    /**
     * Adds a listener to handle incoming chats
     */
    protected void startChatListener() {
        connection.getChatManager().addChatListener(new ChatManagerListener() {

            public void chatCreated(Chat chat, boolean bln) {
                incomingChats.put(WorldXMPPConnectionHandler.extractUsername(chat.getParticipant()), chat);
                chat.addMessageListener(new WorldProtocol(view));
            }
        });
    }

    /**
     * Creates a new chat and stores it.
     * @param user
     */
    public void createChat(String user) {
        Chat chat = connection.getChatManager().createChat(fullUserName(user), new WorldProtocol(view));
        outgoingChats.put(fullUserName(user), chat);
    }
}
