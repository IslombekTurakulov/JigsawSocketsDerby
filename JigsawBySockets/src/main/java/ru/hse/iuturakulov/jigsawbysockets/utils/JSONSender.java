package ru.hse.iuturakulov.jigsawbysockets.utils;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.jigsawbysockets.models.ObjJsonSender;

/**
 *
 */
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
     * @return
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

    public boolean validateGson(String array) {
        try {
            if (array == null || array.isEmpty()) {
                return false;
            }
            new Gson().fromJson(array, ObjJsonSender.class);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static class SingletonHolder {
        public static final JSONSender JSON_SENDER = new JSONSender();
    }
}