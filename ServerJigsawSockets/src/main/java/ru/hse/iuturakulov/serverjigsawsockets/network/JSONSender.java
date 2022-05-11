package ru.hse.iuturakulov.serverjigsawsockets.network;


import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;
import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.FigureType;

import java.util.ArrayList;

public class JSONSender {

    private final JSONObject request = new JSONObject();

    public static JSONSender getInstance() {
        return SingletonHolder.JSON_SENDER;
    }

    public void putRequest(String key, String value) {
        request.put(key, value);
    }

    public void putRequest(String key, int value) {
        request.put(key, value);
    }

    public void putRequest(String key, JSONObject value) {
        request.put(key, value);
    }

    public void putRequest(String key, JSONArray value) {
        request.put(key, value);
    }

    public void putStatusRequest(String status) {
        request.put("status", status);
    }

    public JSONObject login(Boolean success, String msg, Player player) {
        clearRequests();
        putRequest("type", "login");
        putRequest("status", (success ? "success" : "fail"));
        if (player != null) {
            JSONObject playerJson = new JSONObject();
            playerJson.put("username", player.getPlayerName());
            playerJson.put("placed", player.getPlacedBlocks());
            putRequest("player", playerJson);
        }
        putRequest("message", msg);
        return getRequestInstance();
    }

    public JSONObject getCloseRequest(boolean b) {
        clearRequests();
        putRequest("type", "close_socket");
        putRequest("status", (b ? "success" : "fail"));
        return getRequestInstance();
    }

    public JSONObject onlinePlayers(String myUsername) {
        clearRequests();
        JSONArray jsonResponse = new JSONArray();
        for (Client client : Client.onlineClients) {
            if (client.getPlayerName().equalsIgnoreCase(myUsername)) continue;
            JSONObject playerJSON = new JSONObject();
            playerJSON.put("username", client.getPlayerName());
            playerJSON.put("placed", client.getPlacedBlocks());
            jsonResponse.put(playerJSON);
        }
        putRequest("type", "get-online-players");
        putRequest("players", jsonResponse);
        return getRequestInstance();
    }

    public JSONObject gameFinished(String status, String axis) {
        clearRequests();
        putRequest("type", "game-finish");
        putRequest("status", status);
        return getRequestInstance();
    }

    public JSONObject playerConnected(String username, int points) {
        clearRequests();
        putRequest("type", "player-connected");
        putRequest("player", username);
        putRequest("placed", points);
        return getRequestInstance();
    }

    public JSONObject playRequest(Boolean success, String opponent) {
        clearRequests();
        putRequest("type", "invite_request");
        putRequest("status", (success ? "success" : "fail"));
        putRequest("opponent", opponent);
        return getRequestInstance();
    }

    public JSONObject inviteDeclined(String opponent) {
        clearRequests();
        putRequest("type", "invite_decline");
        putRequest("opponent", opponent);
        return getRequestInstance();
    }

    public JSONObject singleGameStarted(ArrayList<FigureType> shape) {
        clearRequests();
        Gson gson = new Gson();
        gson.toJson(shape);
        putRequest("type", "single_player");
        putRequest("status", "success");
        request.put("move", shape.toArray());
        return request;
    }

    public JSONObject play(Boolean success, String msg, String playerUsername, ShapeType turn) {
        clearRequests();
        putRequest("type", "play");
        putRequest("player", playerUsername);
        putRequest("status", (success ? "success" : "fail"));
        if (success) {
            putRequest("move", String.valueOf(turn));
        } else {
            putRequest("message", msg);
        }
        return request;
    }

    public JSONObject getRequestInstance() {
        return request;
    }

    public void clearRequests() {
        request.clear();
    }

    /**
     * Method which checks a given string valid or not
     *
     * @return boolean
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

    public static class SingletonHolder {
        public static final JSONSender JSON_SENDER = new JSONSender();
    }
}