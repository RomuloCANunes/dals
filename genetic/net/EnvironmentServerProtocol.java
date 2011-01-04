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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * A procol to haldes communication from other envonments to this Environment
 * server
 * @author romulo
 */
public class EnvironmentServerProtocol extends Thread {

    private Socket socket = null;
    private PrintStream out = null;
    private BufferedReader in = null;

    /**
     * Default protocol constructor, receives a socket and hadles the
     * communication from a for it;
     * @param socket
     */
    public EnvironmentServerProtocol(Socket socket) {
        this.socket = socket;
        System.out.println(socket);
    }

    /**
     * Starts a protocol that will listen for messages
     */
    public void run() {
        getInputOutputFromSocket();
        listenForMessage();
        closeConnection();
    }

    /**
     * Obtains input and output instances from socket
     */
    private void getInputOutputFromSocket() {
        try {
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * listens for messages and, when a message is received, dispatchs it for
     * message processing
     */
    private void listenForMessage() {
        String line = null;
        try {
            while ((line = in.readLine()) != null) {
                processMessage(line);
            }
            System.out.println("s environement protocol finishing");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Process a received message
     * @param message
     */
    private void processMessage(String message) {
        System.out.println("s received: " + message);
        out.println("echo: " + message);
    }

    /**
     * closes the connection
     */
    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println("s closing the connection handler");
    }
}
