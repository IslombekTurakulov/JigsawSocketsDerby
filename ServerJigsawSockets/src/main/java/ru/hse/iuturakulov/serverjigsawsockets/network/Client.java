package ru.hse.iuturakulov.serverjigsawsockets.network;

import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.GameLogic;
import ru.hse.iuturakulov.serverjigsawsockets.models.Multiplayer;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;
import ru.hse.iuturakulov.serverjigsawsockets.models.Singleplayer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Client.
 */
public class Client extends Player {
    /**
     * The constant onlineClients.
     */
    public static ArrayList<Client> onlineClients = new ArrayList<>();
    private final BufferedReader bufferReader;
    private final PrintStream printStream;


    /**
     * Instantiates a new Client.
     *
     * @param name   the username
     * @param reader the buffer reader
     * @param stream the print stream
     */
    public Client(String name, String uuid, BufferedReader reader, PrintStream stream) {
        super(name, uuid);
        notifyAllPlayers();
        setOnline(true);
        this.bufferReader = reader;
        this.printStream = stream;
        startListener();
        onlineClients.add(this);
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
                        case "more_shape":
                            ((Singleplayer) getGame()).generateExtraShape();
                        case "online_players":
                            sendRequest(JSONSender.getInstance().onlinePlayers(getPlayerName(), String.valueOf(getuuidPlayer())).toString());
                            break;
                        case "single_player":
                            GameLogic.listOfGames.add(new Singleplayer(this));
                            break;
                        case "single_player_finished":
                            getClientByUsername(jsonObject.getString("name"), jsonObject.getString("uuidPlayer")).getGame().finishGame();
                            // ((Singleplayer) getGame()).finishGame();
                            break;
                        case "invite_request":
                            handleMultiplayerGame(jsonObject.getString("opponent"), jsonObject.getString("opponentUUID"), jsonObject.getString("inviter"), jsonObject.getString("uuidInviter"));
                            break;
                        case "invite_decline_for_game":
                            if (getGame() != null) {
                                Client client = getClientByUsername(jsonObject.getString("opponent"), jsonObject.getString("opponentUUID"));
                                client.sendRequest(JSONSender.getInstance().inviteDeclined(jsonObject.getString("cause")).toString());
                            }
                        case "invite_accept":
                            handleAcceptRequest(jsonObject.getString("opponent"), jsonObject.getString("opponentUUID"));
                            break;
                        case "invite_decline":
                            if (getGame() != null) {
                                ((Multiplayer) getGame()).getOtherOpponent(jsonObject.getString("opponent")).sendRequest(JSONSender.getInstance().inviteDeclined(getPlayerName()).toString());
                            } else {
                                Client client = getClientByUsername(jsonObject.getString("opponent"), jsonObject.getString("uuidPlayer"));
                                client.sendRequest(JSONSender.getInstance().inviteDeclined(getPlayerName()).toString());
                            }
                            break;
                        case "play":
                            handlePlayMove(jsonObject.getInt("placed"));
                            break;
                        case "multiplayer_finished":
                            if (getGame() != null) {
                                getGame().finishGame();
                            }
                            break;
                        case "logout":
                            remove(jsonObject.getString("name"), jsonObject.getString("uuidPlayer"));
                            break;
                        default:
                            JSONSender jsonSender = JSONSender.getInstance();
                            jsonSender.putRequest("status", "Unknown Request");
                            sendRequest(jsonSender.getRequestInstance().toString());
                    }
                }

            } catch (ClassCastException | NullPointerException | IOException | JSONException e) {
                Logger.getLogger(GameLogic.class.getName()).log(Level.SEVERE, e.getMessage());
            }
        }).start();
    }


    private void notifyAllPlayers() {
        for (Client c : Client.onlineClients) {
            if (!c.getuuidPlayer().equalsIgnoreCase(getuuidPlayer())) {
                c.sendRequest(JSONSender.getInstance().playerConnected(getPlayerName(), getPlacedBlocks()).toString());
            }
        }
    }

    /**
     * Gets client by username.
     *
     * @param username the username
     * @return the client by username
     */
    public Client getClientByUsername(String username, String uuid) {
        for (Client client : onlineClients) {
            if (client.getuuidPlayer().equalsIgnoreCase(uuid)) {
                return client;
            }
        }
        return null;
    }


    /**
     * Send request.
     *
     * @param content the content
     */
    public void sendRequest(String content) {
        printStream.println(content);
    }


    private void handleMultiplayerGame(String opponent, String uuid, String inviter, String uuidPlayer) {
        Client opponentClient = getClientByUsername(opponent, uuid);
        if (opponentClient != null) {
            //if (!opponentClient.getPlayerName().equalsIgnoreCase(inviter) && !opponentClient.getuuidPlayer().equalsIgnoreCase(uuidPlayer)) {
            if (opponentClient.getGame() != null) {
                printStream.println(JSONSender.getInstance().playRequest(false, "Player is currently playing a game", uuid, inviter, uuidPlayer).toString());
                return;
            }
            opponentClient.sendRequest(JSONSender.getInstance().playRequest(true, getPlayerName(), getuuidPlayer(), inviter, uuidPlayer).toString());
         /*   } else {
                printStream.println(JSONSender.getInstance().playRequest(false, "You can't play with yourself!", uuid, inviter, uuidPlayer).toString());
            }*/
        } else {
            printStream.println(JSONSender.getInstance().playRequest(false, "Player is not online", uuid, inviter, uuidPlayer).toString());
        }
    }

    private void handleAcceptRequest(String opponent, String uuid) {
        Client opponentClient = getClientByUsername(opponent, uuid);
        if (opponentClient == null) {
            printStream.println(JSONSender.getInstance().playRequest(false, "Player is not online", uuid, "", "").toString());
        }
        GameLogic.listOfGames.add(new Multiplayer(this, opponentClient));
    }

    /**
     * Remove.
     */
    public void remove(String name, String uuid) {
        setOnlineOnArrayList(false);
        onlineClients.remove(this);
    }

    private void handlePlayMove(int placed) {
        if (getGame() == null) {
            sendRequest(JSONSender.getInstance().play(false, "No Active game", getPlayerName(), getuuidPlayer(), 0).toString());
        } else {
            getGame().play(this, placed);
        }
    }

    /**
     * Remove game.
     */
    public void removeGame() {
        GameLogic.listOfGames.removeIf(game -> game.hasPlayer(getPlayerName()));
    }

    /**
     * Gets game.
     *
     * @return the game
     */
    public GameLogic getGame() {
        for (GameLogic game : GameLogic.listOfGames) {
            if (game.hasPlayer(getPlayerName())) {
                return game;
            }
        }
        return null;
    }

    /**
     * Close boolean.
     *
     * @return the boolean
     */
    public boolean close() {
        if (this.getOnline()) {
            printStream.println(JSONSender.getInstance().getCloseRequest(true).toString());
            return true;
        }
        printStream.println(JSONSender.getInstance().getCloseRequest(false).toString());
        return false;
    }
}
