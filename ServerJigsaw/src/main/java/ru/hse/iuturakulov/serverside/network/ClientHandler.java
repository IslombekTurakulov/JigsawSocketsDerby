package ru.hse.iuturakulov.serverside.network;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverside.models.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Client handler
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see Client
 * @see Server
 */
class ClientHandler extends Thread {

    /**
     * The Buffer reader.
     */
    BufferedReader bufferReader;
    /**
     * The Print stream.
     */
    PrintStream printStream;

    /**
     * Instantiates a new Client handler.
     *
     * @param socket the socket
     */
    public ClientHandler(Socket socket) {
        try {
            bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

    public void run() {
        try {
            String inputLine;
            while (!Thread.interrupted() && (inputLine = bufferReader.readLine()) != null) {
                System.out.println(inputLine);
                JSONObject parsedRequest = new JSONObject(inputLine.trim());
                String action = (String) parsedRequest.get("function");
                if (action.equals("login")) {
                    handleLoginRequest(parsedRequest.getString("username"), parsedRequest.getString("uuidPlayer"));
                } else {
                    JSONSender.getInstance().putStatusRequest("Unknown request");
                    printStream.println(JSONSender.getInstance().getRequestInstance());
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void handleLoginRequest(String username, String uuidPlayer) {
        Player _player = new Player(username, uuidPlayer, 0, true);
        this.interrupt();
        new Client(_player.playerNameProperty().getValue(), uuidPlayer, bufferReader, printStream);
        printStream.println(JSONSender.getInstance().login(true, "Logged In", _player));
        Player.playersList.add(_player);
    }
}