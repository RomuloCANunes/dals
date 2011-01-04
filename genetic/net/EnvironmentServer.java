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

import genetic.util.Configuration;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author romulo
 */
public class EnvironmentServer extends ServerSocket implements Runnable {
    /**
     * Constructor
     * @throws IOException
     */
    public EnvironmentServer() throws IOException {
        super(Configuration.ENVIRONMENT_SOCKET_SERVER_PORT);
    }

    /**
     * The proccess to be run when started as a Thread
     */
    public void run() {
        try {
            acceptAllConnections();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Loop that waits for a connection and when a connection is stabilished
     * invokes a connection handling method onAcceptedConnection
     * @throws IOException
     */
    public void acceptAllConnections() throws IOException {
        while (true) {
            //System.out.println("waiting for a new connection");
            Socket inSocket = accept();
            onAcceptedConnection(inSocket);
        }
    }

    /**
     * Receives a socket from an accepted connection, starts a protocol Thread
     * to handle the communication from this connection
     * @param socket
     */
    private void onAcceptedConnection(Socket socket) {
        System.out.println("new connection arrived: " + socket);
        EnvironmentServerProtocol protocol = new EnvironmentServerProtocol(socket);
        protocol.start();
    }
}
