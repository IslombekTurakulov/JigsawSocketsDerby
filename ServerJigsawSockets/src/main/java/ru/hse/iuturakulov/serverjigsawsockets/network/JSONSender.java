package ru.hse.iuturakulov.serverjigsawsockets.network;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;

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
            playerJson.put("points", player.getPoints());
            putRequest("player", playerJson);
        }
        putRequest("message", msg);
        return getRequestInstance();
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
     * @see <a href="https://stackoverflow.com/questions/10174898/how-to-check-whether-a-given-string-is-valid-json-in-java">How to check if JSON is valid</a>
     * @return boolean
     */
    public boolean validateJSON(String json) {
        try {
            if (json == null || json.isEmpty()){
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