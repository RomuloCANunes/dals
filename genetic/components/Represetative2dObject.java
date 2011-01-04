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
package genetic.components;

import genetic.Environment;
import java.awt.Point;

/**
 * A representative object is any item (individual or food) that can be stored,
 * controlled, and displayed in an Environment
 * @author romulo
 */
public class Represetative2dObject extends Point {
    /**
     * the environment this object belongs to
     */
    protected Environment environment = null;

    /**
     * Default constructor - this constructor do not bind the created object to
     * an Envoronment so, in order to interact with environment is mandatory to
     * use the setEnvironment method
     * @see setEnvironment
     */
    public Represetative2dObject() {
    }

    /**
     * Binds the representative2dObject with the Environment, necessary to let
     * the individual interact with it
     *
     * @todo change this thight-coupled interaction to an observable, loosely
     * coupled interaction
     *
     * @param environment
     */
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    /**
     * Retrieves this object image name
     * @return
     */
    public String getImageName() {
        return "unknow.png";
    }
}
