package ru.hse.iuturakulov.clientside.models;

import com.google.gson.annotations.SerializedName;
import ru.hse.iuturakulov.clientside.utils.JSONSender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Key and value json sender
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see Game
 * @see JSONSender
 */
public class ObjJsonSender implements Serializable {
    /**
     * List of keys.
     */
    @SerializedName("keys")
    public List<Key> keys = new ArrayList<>();

    /**
     * List of figure types.
     */
    @SerializedName("moves")
    public List<FigureType> moves = new ArrayList<>();

    /**
     * The type Key.
     */
    public static class Key {
        /**
         * The Key.
         */
        @SerializedName("type")
        private final String key;

        /**
         * The Code.
         */
        @SerializedName("status")
        private final String code;

        /**
         * Instantiates a new Key.
         *
         * @param key   the key
         * @param value the value
         */
        public Key(String key, String value) {
            this.key = key;
            this.code = value;
        }

        public String getCode() {
            return code;
        }
    }
}