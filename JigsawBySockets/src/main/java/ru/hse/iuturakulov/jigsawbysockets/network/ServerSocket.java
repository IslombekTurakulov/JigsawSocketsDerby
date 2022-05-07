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

public abstract class ServerSocket {

    private static BufferedReader bufferedReader;
    private static PrintStream printStream;
    private static Socket serverSocket;

    public static void attachToClient() {
        new Thread(ServerSocket::run).start();
    }

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

    private static void run() {
        try {
            while (serverSocket != null && !(serverSocket.isClosed())) {
                ServerHandler.initialize(bufferedReader.readLine());
            }
            Constants.LOGGER.log(Level.INFO, "Server is closing...");
            printStream.close();
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException exception) {
            Constants.LOGGER.log(Level.SEVERE, ServerSocket.class.getName(), exception);
        }
    }

    public static void sendRequest(String line) {
        printStream.println(line);
    }
}
