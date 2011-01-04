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
package world;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPException;
import world.net.WorldXMPPConnectionHandler;
import world.view.WorldManagement;

/**
 * This class makes queries to the monitored Environments in a timed pace, for
 * collecting information about them
 * @author romulo
 */
public class EnvironmentInformationQueryer implements Runnable {
    private int delayTime = 15000;
    private WorldXMPPConnectionHandler connectionXMPP;
    private WorldManagement view;

    /**
     * Default constructor
     * @param view
     */
    public EnvironmentInformationQueryer(WorldManagement view) {
        this.view = view;
    }

    /**
     * Executes the query and sleeps, in a loop
     */
    public void run() {
        connectionXMPP = new WorldXMPPConnectionHandler(view);
        connectionXMPP.setHostName("romulo-netbook");
        connectionXMPP.setUserName("mundo");
        connectionXMPP.setPassword("mundo");
        try {
            connectionXMPP.connect();
        } catch (XMPPException ex) {
            ex.printStackTrace();
        }
        connectionXMPP.start();

        do {
            for( MonitoredEnvironment env : view.getMonitoredEnvironments() ) {
                try {
                    // envia mensagem
                    System.out.println("enviando mensagem para: " + env.getUsername());
                    connectionXMPP.sendMessage(env.getUsername(), "status");
                } catch (XMPPException ex) {
                    ex.printStackTrace();
                }
            }
            try {
                System.out.println("aguardando");
                Thread.sleep(delayTime);
            } catch (InterruptedException ex) {
                break;
            }
        } while(true);
    }
}
