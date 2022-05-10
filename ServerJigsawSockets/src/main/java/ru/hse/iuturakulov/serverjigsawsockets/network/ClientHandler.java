package ru.hse.iuturakulov.serverjigsawsockets.network;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

class ClientHandler extends Thread {

    BufferedReader bufferReader;
    PrintStream printStream;

    public ClientHandler(Socket socket) {
        try {
            bufferReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
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
                    handleLoginRequest(parsedRequest.getString("username"));
                } else {
                    JSONSender.getInstance().putStatusRequest("Unknown request");
                    printStream.println(JSONSender.getInstance().getRequestInstance());
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private Boolean checkAlreadyLoggedIn(String username) {
        for (Client client : Client.onlineClients) {
            if (client.playerNameProperty().getValue().equalsIgnoreCase(username))
                return true;
        }
        return false;
    }

    private void removeClientByUsername(String username) {
        Client.onlineClients.removeIf(c -> c.playerNameProperty().getValue().equalsIgnoreCase(username));
    }

    private void handleLoginRequest(String username) {
        if (checkAlreadyLoggedIn(username)) {
            printStream.println(JSONSender.getInstance().login(false, "This username is already logged in from other client", null));
            return;
        }
        Player _player = new Player(username, 0, true);
        this.interrupt();
        new Client(_player.playerNameProperty().getValue(), bufferReader, printStream);
        printStream.println(JSONSender.getInstance().login(true, "Logged In", _player));
        Player.playersList.add(_player);
    }
}