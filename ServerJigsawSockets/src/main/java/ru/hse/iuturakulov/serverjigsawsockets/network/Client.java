package ru.hse.iuturakulov.serverjigsawsockets.network;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.GameLogic;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;
import ru.hse.iuturakulov.serverjigsawsockets.models.Singleplayer;
import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends Player {
    public static ArrayList<Client> onlineClients = new ArrayList<>();

    private final BufferedReader bufferReader;
    private final PrintStream printStream;


    public Client(String _username, BufferedReader _bufferReader, PrintStream _printStream) {
        super(_username);
        notifyAllPlayers();
        setOnline(true);
        bufferReader = _bufferReader;
        printStream = _printStream;
        startListener();
        onlineClients.add(this);
    }

    private static boolean isIt(JSONObject parsedResponse, String key, String value) {
        return Objects.equals(parsedResponse.getString(key), key);
    }

    private void startListener() {
        new Thread(() -> {
            try {
                String str;
                while (!Thread.interrupted() && (str = bufferReader.readLine()) != null) {
                    System.out.println(str);
                    JSONObject jsonObject = new JSONObject(str.trim());
                    String function = (String) jsonObject.get("function");
                    switch (function) {
                        case "online_players":
                            sendRequest(JSONSender.getInstance().onlinePlayers(getPlayerName()).toString());
                            break;
                        case "single_player":
                            GameLogic.listOfGames.add(new Singleplayer(this));
                            break;
                        case "multi_player":
                            handleMultiplayerGame(jsonObject.getString("opponent"));
                            break;
                        case "invite_request":
                            handleAcceptRequest(jsonObject.getString("opponent"), jsonObject.getString("record"));
                            break;
                        case "invite_decline":
                            String opponent = jsonObject.getString("opponent");
                            // ((Multiplayer) getGame()).getOtherOpponent(opponent).sendRequest(JSONSender.getInstance().inviteDeclined(getPlayerName()).toString());
                            break;
                        case "play":
                            // handlePlayMove(jsonObject.getInt("index"));
                            break;
                        case "send-message":
                            if (getGame() != null)
                                // ((Multiplayer) getGame()).sendMessage(this, jsonObject.getString("message"));
                                break;
                        case "logout":
                            remove();
                            break;
                        default:
                            JSONSender jsonSender = JSONSender.getInstance();
                            jsonSender.putRequest("status", "Unknown Request");
                            sendRequest(jsonSender.getRequestInstance().toString());
                    }
                }

            } catch (IOException | JSONException e) {
                Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        }).start();
    }

    public Boolean isPlaying() {
        return !(getGame() == null);
    }

    private void notifyAllPlayers() {
        for (Client c : Client.onlineClients) {
            if (!c.getPlayerName().equalsIgnoreCase(getPlayerName())) {
                c.sendRequest(JSONSender.getInstance().playerConnected(getPlayerName(), getPlacedBlocks()).toString());
            }
        }
    }

    public Client getClientByUsername(String username) {
        for (Client client : onlineClients) {
            if (client.getPlayerName().equalsIgnoreCase(username)) {
                return client;
            }
        }
        return null;
    }


    public void sendRequest(String content) {
        printStream.println(content);
    }


    private void handleMultiplayerGame(String opponent) {
        Client opponentClient = getClientByUsername(opponent);
        if (opponentClient == null) {
            printStream.println(JSONSender.getInstance().playRequest(false, "Player is not online").toString());
            return;
        }
        if (getPlayerName().equalsIgnoreCase(opponent)) {
            printStream.println(JSONSender.getInstance().playRequest(false, "You can't play with yourself!").toString());
            return;
        }
        if (opponentClient.isPlaying()) {
            printStream.println(JSONSender.getInstance().playRequest(false, "Player is Currently Playing a game").toString());
            return;
        }
        opponentClient.sendRequest(JSONSender.getInstance().playRequest(true, getPlayerName()).toString());
    }

    private void handleAcceptRequest(String opponent, String record) {
        Client opponentClient = getClientByUsername(opponent);
        if (opponentClient == null) {
            printStream.println(JSONSender.getInstance().playRequest(false, "Player is not online").toString());
        }
        // GameLogic.listOfGames.add(new Multiplayer(this, opponentClient, (record.equalsIgnoreCase("yes"))));
    }


    public void remove() {
        setOnlineOnArrayList(false);
        onlineClients.remove(this);
        playersList.remove(this);
    }

    private void handlePlayMove(int x, int y) {
        if (getGame() == null) {
            sendRequest(JSONSender.getInstance().play(false, "No Active game", getPlayerName(), ShapeType.NONE).toString());
        } else {
            getGame().play(this, x, y);
        }
    }

    public void removeGame() {
        GameLogic.listOfGames.removeIf(game -> game.hasPlayer(getPlayerName()));
    }

    public GameLogic getGame() {
        return GameLogic.listOfGames.stream().filter(game -> game.hasPlayer(getPlayerName())).findFirst().orElse(null);
    }

    public boolean close() {
        if (this.getOnline()) {
            printStream.println(JSONSender.getInstance().getCloseRequest(true).toString());
            return true;
        }
        printStream.println(JSONSender.getInstance().getCloseRequest(false).toString());
        return false;
    }
}
