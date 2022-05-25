package ru.hse.iuturakulov.jigsawbysockets.utils;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.hse.iuturakulov.jigsawbysockets.models.ObjJsonSender;

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

    /**
     * Validate gson boolean.
     *
     * @param array the array
     * @return the boolean
     */
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