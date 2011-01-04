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
