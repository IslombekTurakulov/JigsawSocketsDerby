package ru.hse.iuturakulov.serverjigsawsockets.network;

import ru.hse.iuturakulov.serverjigsawsockets.models.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Runnable {

    private static final Set<Server> SERVERS = new HashSet<>();
    private static Thread serverThread;
    private static ServerSocket serverSocket;
    private static int port;
    private static Boolean isRunning = true;

    private Server(int _port) {
        port = _port;
    }

    public static Thread getServer() {
        return serverThread;
    }

    public static void createServer(int port) {
        isRunning = true;
        serverThread = new Thread(new Server(port));
        serverThread.start();
    }

    public static void stop() {
        isRunning = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Player.playersList.clear();
            Client.onlineClients.removeAll(Client.onlineClients);
        }
    }

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
            serverSocket = new ServerSocket(port);
            while (true) {
                if (isRunning) {
                    Socket s = serverSocket.accept();
                    new ClientHandler(s);

                }
            }
        } catch (IOException exception) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, exception);
        }
    }
}