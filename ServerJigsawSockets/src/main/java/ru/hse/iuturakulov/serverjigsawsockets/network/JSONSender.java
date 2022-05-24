package ru.hse.iuturakulov.serverjigsawsockets.network;


import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.FigureType;
import ru.hse.iuturakulov.serverjigsawsockets.models.ObjJsonSender;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;
import ru.hse.iuturakulov.serverjigsawsockets.utils.Constants;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Json sender.
 */
public class JSONSender {

    private final JSONObject request = new JSONObject();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static JSONSender getInstance() {
        return SingletonHolder.JSON_SENDER;
    }

    /**
     * Play accepted string.
     *
     * @param opponent the opponent
     * @param figure   the figure
     * @return the string
     */
    public static String playAccepted(String opponent, String uuid, ArrayList<FigureType> figure) {
        ObjJsonSender keyValue = new ObjJsonSender();
        keyValue.keys.add(new ObjJsonSender.Key("start_multi_player", "success"));
        keyValue.keys.add(new ObjJsonSender.Key("opponent", opponent));
        keyValue.keys.add(new ObjJsonSender.Key("time", Constants.timeCurrent));
        keyValue.keys.add(new ObjJsonSender.Key("uuidPlayer", uuid));
        keyValue.moves.addAll(figure);
        return new Gson().toJson(keyValue);
    }

    /**
     * Put request.
     *
     * @param key   the key
     * @param value the value
     */
    public void putRequest(String key, String value) {
        request.put(key, value);
    }

    /**
     * Put request.
     *
     * @param key   the key
     * @param value the value
     */
    public void putRequest(String key, int value) {
        request.put(key, value);
    }

    /**
     * Put request.
     *
     * @param key   the key
     * @param value the value
     */
    public void putRequest(String key, JSONObject value) {
        request.put(key, value);
    }

    /**
     * Put request.
     *
     * @param key   the key
     * @param value the value
     */
    public void putRequest(String key, JSONArray value) {
        request.put(key, value);
    }

    /**
     * Put status request.
     *
     * @param status the status
     */
    public void putStatusRequest(String status) {
        request.put("status", status);
    }

    /**
     * Login json object.
     *
     * @param success the success
     * @param msg     the msg
     * @param player  the player
     * @return the json object
     */
    public JSONObject login(Boolean success, String msg, Player player) {
        clearRequests();
        putRequest("type", "login");
        putRequest("status", (success ? "success" : "fail"));
        if (player != null) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("username", player.getPlayerName());
            playerJson.put("uuidPlayer", player.getuuidPlayer());
            playerJson.put("placed", player.getPlacedBlocks());
            putRequest("player", playerJson);
        }
        putRequest("message", msg);
        return getRequestInstance();
    }

    /**
     * Gets close request.
     *
     * @param flag the flag
     * @return the close request
     */
    public JSONObject getCloseRequest(boolean flag) {
        clearRequests();
        putRequest("type", "close_socket");
        putRequest("status", (flag ? "success" : "fail"));
        return getRequestInstance();
    }

    /**
     * Online players json object.
     *
     * @param myUsername the my username
     * @return the json object
     */
    public JSONObject onlinePlayers(String myUsername, String uuid) {
        clearRequests();
        JSONArray jsonResponse = new JSONArray();
        for (Client client : Client.onlineClients) {
            if (client.getPlayerName().equalsIgnoreCase(myUsername) && client.getuuidPlayer().equals(uuid))
                continue;
            JSONObject playerJSON = new JSONObject();
            playerJSON.put("username", client.getPlayerName());
            playerJSON.put("uuidPlayer", client.getuuidPlayer());
            playerJSON.put("placed", client.getPlacedBlocks());
            jsonResponse.put(playerJSON);
        }
        putRequest("type", "get-online-players");
        putRequest("players", jsonResponse);
        return getRequestInstance();
    }

    /**
     * Game finished json object.
     *
     * @param status the status
     * @return the json object
     */
    public JSONObject gameFinished(String status) {
        clearRequests();
        putRequest("type", "game-finish");
        putRequest("status", status);
        Logger.getLogger(Client.class.getName()).log(Level.INFO, getRequestInstance().toString());
        return getRequestInstance();
    }

    /**
     * Player connected json object.
     *
     * @param username the username
     * @param points   the points
     * @return the json object
     */
    public JSONObject playerConnected(String username, int points) {
        clearRequests();
        putRequest("type", "player-connected");
        putRequest("player", username);
        putRequest("placed", points);
        return getRequestInstance();
    }

    /**
     * Play request json object.
     *
     * @param success  the success
     * @param opponent the opponent
     * @return the json object
     */
    public JSONObject playRequest(Boolean success, String opponent, String uuidOpponent, String inviter, String uuidInviter) {
        clearRequests();
        putRequest("type", "invite_request");
        putRequest("status", (success ? "success" : "fail"));
        putRequest("opponent", opponent);
        putRequest("uuidPlayer", uuidOpponent);
        putRequest("inviter", inviter);
        putRequest("uuidInviter", uuidInviter);
        return getRequestInstance();
    }

    /**
     * Invite declined json object.
     *
     * @param opponent the opponent
     * @return the json object
     */
    public JSONObject inviteDeclined(String opponent) {
        clearRequests();
        putRequest("type", "invite_decline");
        putRequest("opponent", opponent);
        return getRequestInstance();
    }

    /**
     * Single game started string.
     *
     * @param shape       the shape
     * @param timeCounter the time counter
     * @return the string
     */
    public String singleGameStarted(ArrayList<FigureType> shape, String timeCounter) {
        ObjJsonSender keyValue = new ObjJsonSender();
        keyValue.keys.add(new ObjJsonSender.Key("single_player", "success"));
        keyValue.keys.add(new ObjJsonSender.Key("time", timeCounter));
        keyValue.moves.addAll(shape);
        return new Gson().toJson(keyValue);
    }

    /**
     * Gets shapes for game.
     *
     * @param shape the shape
     * @return the shapes for game
     */
    public String getShapesForGame(ArrayList<FigureType> shape) {
        clearRequests();
        ObjJsonSender keyValue = new ObjJsonSender();
        keyValue.keys.add(new ObjJsonSender.Key("get_shapes", "success"));
        keyValue.moves.addAll(shape);
        return new Gson().toJson(keyValue);
    }

    /**
     * Play json object.
     *
     * @param success        the success
     * @param msg            the msg
     * @param playerUsername the player username
     * @param turn           the turn
     * @return the json object
     */
    public JSONObject play(Boolean success, String msg, String playerUsername, String uuid, int turn) {
        clearRequests();
        putRequest("type", "play");
        putRequest("player", playerUsername);
        putRequest("uuid", uuid);
        putRequest("status", (success ? "success" : "fail"));
        if (success) {
            putRequest("placed", String.valueOf(turn));
        } else {
            putRequest("message", msg);
        }
        return request;
    }

    /**
     * Gets request instance.
     *
     * @return the request instance
     */
    public JSONObject getRequestInstance() {
        return request;
    }

    /**
     * Clear requests.
     */
    public void clearRequests() {
        request.clear();
    }

    /**
     * Method which checks a given string valid or not
     *
     * @param json the json
     * @return boolean boolean
     * @see <a href="https://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java">How to check if JSON is valid</a>
     */
    public boolean validateJSON(String json) {
        try {
            if (json == null || json.isEmpty()) {
                return false;
            }
            new JSONObject(json);
        } catch (JSONException ex) {
            try {
                new JSONArray(json);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    /**
     * The type Singleton holder.
     */
    public static class SingletonHolder {
        /**
         * The constant JSON_SENDER.
         */
        public static final JSONSender JSON_SENDER = new JSONSender();
    }
}