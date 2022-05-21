package ru.hse.iuturakulov.serverjigsawsockets.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Obj json sender.
 */
public class ObjJsonSender implements Serializable {
    /**
     * The Main.
     */
    @SerializedName("keys")
    public List<Key> keys = new ArrayList<>();
    /**
     * The Figures.
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
        private String type;

        /**
         * The Code.
         */
        @SerializedName("status")
        private String value;

        /**
         * Instantiates a new Key.
         *
         * @param key   the key
         * @param value the value
         */
        public Key(String key, String value) {
            this.type = key;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }
    }
}