/*
 * This file is part of DALS - Distributed Artificial Life Simulator.
 * DALS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Foobar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with DALS.  If not, see <http://www.gnu.org/licenses/>.
 */
package genetic.net;

import genetic.Environment;
import genetic.util.Configuration;
import java.util.Hashtable;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;

/**
 * This class creates a XMPP connection and binds a protocol for process
 * incomming messages
 * @author romulo
 */
public abstract class XMPPConnectionHandler extends Thread {

    /**
     * The XMPP connection
     */
    protected XMPPConnection connection;
    private String userName = Configuration.XMPP_USERNAME;
    private String password = Configuration.XMPP_PASSWORD;
    private String hostName = Configuration.XMPP_HOSTNAME;
    /**
     * Stores the outgoing chats. Chats that have been started by this user
     */
    protected Hashtable<String, Chat> outgoingChats = new Hashtable<String, Chat>();
    /**
     * Stores the incoming chats. Chats that have been started by other users
     */
    protected Hashtable<String, Chat> incomingChats = new Hashtable<String, Chat>();
    private PacketTypeFilter allMessages = new PacketTypeFilter(Message.class);
    private MessageListener listener;

    /**
     * The XMPP server host
     * @param hostName
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * The XMPP server password for this user
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * The XMPP server user to be identified with
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Connects to the server
     * @throws XMPPException
     */
    public void connect() throws XMPPException {
        connection = new XMPPConnection(hostName);
        //SASLAuthentication.supportSASLMechanism("PLAIN", 0);

        connection.connect();
        //System.out.println("username: " + userName + " password: " + password);
        connection.login(userName, password);
        //System.out.println("connected");
        startChatListener();
    }

    /**
     * Adds a listener to handle incoming chats
     */
    protected abstract void startChatListener();

    /**
     * Creates a new chat and stores it.
     * @param user
     */
    public abstract void createChat(String user);

    /**
     * Retrieves a started chat with user
     * @param user
     * @return
     */
    private Chat getChat(String user) {
        return outgoingChats.get(fullUserName(user));
    }

    /**
     * Returns true if a chat with this user is already started
     * @param user
     * @return
     */
    private boolean availableChat(String user) {
        return outgoingChats.containsKey(fullUserName(user));
    }

    /**
     * Unites the user with hostname
     * @param user
     * @return
     */
    protected String fullUserName(String user) {
        return user + "@" + hostName;
    }

    /**
     * Extracts only the username from a String like username@hostname/hashcode
     * @param fullIdentifier
     * @return the username, or the full string if username not found
     */
    public static String extractUsername(String fullIdentifier) {
        int position = fullIdentifier.indexOf('@');
        if (position > -1) {
            return fullIdentifier.substring(0, position);
        }
        return fullIdentifier;
    }


    /**
     * Sends a message to the user. If there are no started chat, starts one.
     * @param user
     * @param message
     * @throws XMPPException
     */
    public void sendMessage(String user, String message) throws XMPPException {
        if (!availableChat(user)) {
            createChat(user);
        }
        Chat chat = getChat(user);
        chat.sendMessage(message);
        //System.out.println("message sent: " + message + " - to: " + fullUserName(user));
    }

    /**
     * Connects to server and stay running until disconnected
     */
    @Override
    public void run() {
        //System.out.println("started");
        if( ! connection.isConnected() ) {
            try {
                connect();
            } catch (XMPPException ex) {
                ex.printStackTrace();
            }
        }
        
        while (connection.isConnected()) {
            try {
                sleep(500);
            } catch (InterruptedException ex) {
                break;
            }
        }
        //System.out.println("xmpp disconnected");
    }
}
