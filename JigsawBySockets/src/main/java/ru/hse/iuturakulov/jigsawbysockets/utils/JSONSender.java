package ru.hse.iuturakulov.jigsawbysockets.utils;

import org.json.JSONObject;

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

    public static class SingletonHolder {
        public static final JSONSender JSON_SENDER = new JSONSender();
    }
}