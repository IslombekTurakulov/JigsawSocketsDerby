package ru.hse.iuturakulov.clientside.network;

import javafx.scene.control.Alert;
import ru.hse.iuturakulov.clientside.models.Game;
import ru.hse.iuturakulov.clientside.utils.Constants;
import ru.hse.iuturakulov.clientside.utils.DialogCreator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;

import static ru.hse.iuturakulov.clientside.models.Game.timeline;

/**
 * The type Server socket.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ServerHandler
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
                if (Game.getIsPlayingGame() != null) {
                    timeline.setCycleCount(0);
                    timeline.stop();
                    Game.setIsGameStopped(false);
                    Game.setCurrentPlayingGame(null);
                }
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Error", "Failed to connect to server", false);
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
        if (line != null && printStream != null) {
            printStream.println(line);
        }
    }
}
