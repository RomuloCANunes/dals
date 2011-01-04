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
