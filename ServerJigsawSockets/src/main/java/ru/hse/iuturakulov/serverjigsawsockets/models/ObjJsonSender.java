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
    public List<Key> Main = new ArrayList<>();
    /**
     * The Figures.
     */
    @SerializedName("moves")
    public List<FigureType> figures = new ArrayList<>();

    /**
     * The type Key.
     */
    public static class Key {
        /**
         * The Key.
         */
        @SerializedName("type")
        public String key;

        /**
         * The Code.
         */
        @SerializedName("status")
        public String code;

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
    }
}