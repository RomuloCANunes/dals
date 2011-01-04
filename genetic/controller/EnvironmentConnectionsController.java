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
package genetic.controller;

import genetic.Environment;
import genetic.net.EnvironmentProtocol;

/**
 *
 * @author romulo
 */
public class EnvironmentConnectionsController {
    private Environment environment = Environment.getInstance();

    /**
     * Defines the IP of a given direction
     * @param direction
     * @param user
     */
    public void setDirectionUser(String direction, String user) {
        if( user == null || user.equals("") ) {
            environment.freeBorderEnvironmentByDirection(direction);
        } else {
            environment.setBorderEnvironment(direction, user);
        }
    }

    private boolean getDirectionStatus(String direction) {
        String user = environment.getBorderEnvironment(direction);
        return user != null && ! user.equals("");
    }

    /**
     * Retrieves the status of the north direction
     * @return
     */
    public boolean getNorthStatus() {
        return getDirectionStatus(EnvironmentProtocol.DIRECTION_NORTH);
    }

    /**
     * Retrieves the status of the east direction
     * @return
     */
    public boolean getEastStatus() {
        return getDirectionStatus(EnvironmentProtocol.DIRECTION_EAST);
    }

    /**
     * Retrieves the status of the south direction
     * @return
     */
    public boolean getSouthStatus() {
        return getDirectionStatus(EnvironmentProtocol.DIRECTION_SOUTH);
    }

    /**
     * Retrieves the status of the west direction
     * @return
     */
    public boolean getWestStatus() {
        return getDirectionStatus(EnvironmentProtocol.DIRECTION_WEST);
    }

}
