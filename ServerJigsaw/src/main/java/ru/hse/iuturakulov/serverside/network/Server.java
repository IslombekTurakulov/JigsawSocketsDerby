package ru.hse.iuturakulov.serverside.network;

import ru.hse.iuturakulov.serverside.models.Player;
import ru.hse.iuturakulov.serverside.utils.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Server.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ClientHandler
 * @see Client
 */
public class Server implements Runnable {
    private static final Set<Server> SERVERS = new HashSet<>();
    private static Thread serverThread;
    private static ServerSocket serverSocket;
    private static int port;
    private static Boolean isRunning = true;

    private Server(int port) {
        Server.port = port;
    }

    /**
     * Gets server.
     *
     * @return the server
     */
    public static Thread getServer() {
        return serverThread;
    }

    /**
     * Create server.
     *
     * @param port the port
     */
    public static void createServer(int port) {
        isRunning = true;
        Logger.getLogger(Server.class.getSimpleName()).log(Level.INFO, "Server starting");
        Logger.getLogger(Server.class.getSimpleName()).log(Level.INFO, MessageFormat.format("Game time for each game is (HH:mm:ss) -> {0}", Constants.timeCurrent));
        serverThread = new Thread(new Server(port));
        serverThread.start();
    }

    /**
     * Stops the server.
     */
    public static void stop() {
        isRunning = false;
        if (serverSocket != null) {
            Logger.getLogger(Server.class.getSimpleName()).log(Level.INFO, "Stopping the server");
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Player.playersList.clear();
            Client.onlineClients.removeIf(Client::close);
            Client.onlineClients.clear();
            Logger.getLogger(Server.class.getSimpleName()).log(Level.INFO, "Server stopped");
        }
    }

    /**
     * Close socket.
     */
    public static void closeSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
                isRunning = false;
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public void run() {
        try {
            Logger.getLogger(Server.class.getSimpleName()).log(Level.INFO, "Server is running");
            serverSocket = new ServerSocket(port);
            while (true) {
                if (isRunning) {
                    Socket s = serverSocket.accept();
                    new ClientHandler(s);
                }
            }
        } catch (IOException exception) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, exception.getMessage());
        }
    }
}
