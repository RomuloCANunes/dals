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
package genetic.net;

import genetic.Environment;
import genetic.EnvironmentSerializable;
import genetic.components.Individual;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

/**
 * Protocol that handles incoming messages to this environment
 * @author romulo
 */
public class EnvironmentProtocol implements MessageListener {

    /**
     * Environment that this protocol belongs to
     */
    protected Environment environment;
    private static String PREFIX_INDIVIDUAL = "<genetic.components.Individual>";
    private static String PREFIX_INCONNECTION = "incoming connection from: ";
    private static String PREFIX_STATUS_QUERY = "status";
    /**
     * The value used to represent the North direction
     */
    public static String DIRECTION_NORTH = "north";
    /**
     * The value used to represent the South direction
     */
    public static String DIRECTION_SOUTH = "south";
    /**
     * The value used to represent the East direction
     */
    public static String DIRECTION_EAST = "east";
    /**
     * The value used to represent the West direction
     */
    public static String DIRECTION_WEST = "west";

    /**
     * Default constructor
     * @param environment
     */
    public EnvironmentProtocol(Environment environment) {
        this.environment = environment;
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
        if (message.startsWith(PREFIX_INDIVIDUAL)) {
            processIndividualMessage(message);
        } else if (message.startsWith(PREFIX_INCONNECTION)) {
            processIncomingConnection(message);
        } else if (message.startsWith(PREFIX_STATUS_QUERY)) {
            processStatusQuery(chat);
        } else {
            System.out.println("Other message received: " + message);
        }
    }

    /**
     * Handles a message that has been identified as an Individual serialized
     * @param serializedIndividualXML
     */
    private void processIndividualMessage(String serializedIndividualXML) {
        System.out.println("Individual received!!");
        unserializeIndividual(serializedIndividualXML);
    }

    /**
     * Handles a status query message, to respond accordingly
     * @param message
     */
    private void processStatusQuery(Chat chat) {
        EnvironmentSerializable environmentSerializable = new EnvironmentSerializable();
        environmentSerializable.loadFromEnvironmentInstance(environment);
        try {
            chat.sendMessage(environmentSerializable.serializeXML());
        } catch (XMPPException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles a message that has been identified as a new incoming connection
     * from other Environment
     * @param message
     */
    private void processIncomingConnection(String message) {
        System.out.println("Incoming connection: ");
        String direction = message.substring(PREFIX_INCONNECTION.length());
        if (direction.equals(DIRECTION_NORTH)) {
            System.out.println("north");
        } else if (direction.equals(DIRECTION_EAST)) {
            System.out.println("east");
        } else if (direction.equals(DIRECTION_SOUTH)) {
            System.out.println("south");
        } else if (direction.equals(DIRECTION_WEST)) {
            System.out.println("west");
        } else {
            System.out.println("direction unknow");
        }
    }

    /**
     * Verifies if a given String is a valid direction to the system
     * @param direction
     * @return true if the direction is valid
     */
    public static boolean isValidDirection(String direction) {
        return (direction.equals(DIRECTION_NORTH)
                || direction.equals(DIRECTION_EAST)
                || direction.equals(DIRECTION_SOUTH)
                || direction.equals(DIRECTION_WEST));
    }

    /**
     * Unserializes an Individual based on a XML string
     * @param message
     */
    private void unserializeIndividual(String message) {
        Individual ind = Individual.unserializeXML(message);
        ind.postInitilize();
        environment.receiveEasternImmigrant(ind);
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
}
