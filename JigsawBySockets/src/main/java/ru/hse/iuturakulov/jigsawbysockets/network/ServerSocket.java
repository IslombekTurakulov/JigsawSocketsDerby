package ru.hse.iuturakulov.jigsawbysockets.network;

import javafx.scene.control.Alert;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.DialogCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

/**
 * The type Server socket.
 */
public abstract class ServerSocket {

    /**
     * The constant serverSocket.
     */
    public static Socket serverSocket;
    /**
     * The constant thread.
     */
    public static Thread thread;
    private static BufferedReader bufferedReader;
    private static PrintStream printStream;

    /**
     * Attach to client.
     */
    public static void attachToClient() {
        thread = new Thread(() -> {
            try {
                while (serverSocket != null && !(serverSocket.isClosed())) {
                    ServerHandler.initializeResponses(bufferedReader.readLine());
                }
                Constants.LOGGER.log(Level.WARNING, "Server is closing...");
                printStream.close();
                serverSocket.close();
            } catch (NullPointerException | IOException exception) {
                Constants.LOGGER.log(Level.SEVERE, ("%s: %s").formatted(ServerSocket.class.getName(), exception));
            }
        });
        thread.start();
    }

    /**
     * Connect.
     *
     * @param address the address
     * @param port    the port
     */
    public static void connect(String address, int port) {
        Constants.LOGGER.log(Level.INFO, "Start connection");
        try {
            serverSocket = new Socket(InetAddress.getByName(address), port);
            printStream = new PrintStream(serverSocket.getOutputStream(), true);
            bufferedReader = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        } catch (IOException e) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Error", "Failed to connect to server", true);
        }
        Constants.LOGGER.log(Level.INFO, "Connecting...");
        attachToClient();
    }

    /**
     * Send request.
     *
     * @param line the line
     */
    public static void sendRequest(String line) {
        if (line != null) {
            printStream.println(line);
        }
    }
}
