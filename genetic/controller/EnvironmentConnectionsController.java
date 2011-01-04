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
