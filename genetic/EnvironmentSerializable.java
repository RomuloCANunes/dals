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
package genetic;

import com.thoughtworks.xstream.XStream;

/**
 *
 * @author romulo
 */
public class EnvironmentSerializable {
    private boolean playing;
    private float timeSpeed;
    private int individualCount, foodCount;

    /**
     * Loads data from an Environment instance
     * @param environment
     */
    public void loadFromEnvironmentInstance(Environment environment) {
        playing = environment.isPlaying();
        timeSpeed = environment.getTimeSpeed();
        individualCount = environment.getIndividualCount();
        foodCount = environment.getFoodCount();
    }

    /**
     * Serializes this instance in a XML formatted String
     * @return the individual serialized as XML
     */
    public String serializeXML() {
        XStream xstream = new XStream();
        return xstream.toXML(this);
    }

    /**
     * Retrieves the foodCount
     * @return
     */
    public int getFoodCount() {
        return foodCount;
    }

    /**
     *
     * @return
     */
    public int getIndividualCount() {
        return individualCount;
    }

    /**
     *
     * @return true if is playing
     */
    public boolean isPlaying() {
        return playing;
    }

    /**
     *
     * @return
     */
    public float getTimeSpeed() {
        return timeSpeed;
    }

    /**
     * Retrieves an EnvironmentSerializable from a XML String
     * @param xml
     * @return
     */
    public static EnvironmentSerializable unserializeXML(String xml) {
        XStream xstream = new XStream();
        EnvironmentSerializable environmentSerializable = (EnvironmentSerializable)xstream.fromXML(xml);
        return environmentSerializable;
    }
}
