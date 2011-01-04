/*
 * This file is part of DALS - Distributed Artificial Life Simulation.
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
