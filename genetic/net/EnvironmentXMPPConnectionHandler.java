package genetic.net;

import genetic.Environment;
import org.jivesoftware.smack.*;

/**
 * This class creates a XMPP connection and binds a EnvironmentProtocol for
 * processing incomming messages
 * @author romulo
 */
public class EnvironmentXMPPConnectionHandler extends XMPPConnectionHandler {
    private Environment environment;

    /**
     * Default constructor
     * @param environment
     */
    public EnvironmentXMPPConnectionHandler(Environment environment) {
        this.environment = environment;
    }

    /**
     * Adds a listener to handle incoming chats
     */
    protected void startChatListener() {
        connection.getChatManager().addChatListener(new ChatManagerListener() {

            public void chatCreated(Chat chat, boolean bln) {
                incomingChats.put(EnvironmentXMPPConnectionHandler.extractUsername(chat.getParticipant()), chat);
                chat.addMessageListener(new EnvironmentProtocol(environment));
            }
        });
    }

    /**
     * Creates a new chat and stores it.
     * @param user
     */
    public void createChat(String user) {
        Chat chat = connection.getChatManager().createChat(fullUserName(user), new EnvironmentProtocol(environment));
        outgoingChats.put(fullUserName(user), chat);
        //System.out.println("chat criado: " + chat.getParticipant() + " " + chat.getThreadID());
    }
}
