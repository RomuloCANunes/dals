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
import java.net.UnknownHostException;

/**
 *
 * @author romulo
 */
public class EnvironmentClient extends Thread {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintStream out = null;
    private String host;

    /**
     * Constructor
     * @param host
     */
    public EnvironmentClient(String host) {
        this.host = host;
    }

    /**
     * The proccess to be run when started as a Thread
     */
    public void run() {
        try {
            socket = new Socket(host, 9666);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintStream(socket.getOutputStream(), true);
            String line;
            while( (line = in.readLine()) != null) {
                System.out.print("c received: " + line);
            }
            System.out.println("client stopped listening");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Prints some string in this client (sends it to the server)
     * @param s
     */
    public void print(String s) {
        System.out.print("c enviando: " + s);
        if(socket != null) {
            if(socket.isConnected()) {
                out.print(s);
            } else {
                System.out.println("c not connected.");
            }
        } else {
            System.out.println("c Socket is null.");
        }
    }

    /**
     * Prints something to this client (sends it to the server), adding a \n char
     * to the end of message, indicating the end of line
     * @param s
     */
    public void println(String s) {
        print(s + "\n");
    }

    /**
     * Closes this connection
     */
    public void close() {
        if(socket != null) {
            if(socket.isConnected()) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if( in != null ) {
            try {
                in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        if( out != null ) {
            out.close();
        }
    }
}
