package ru.hse.iuturakulov.serverjigsawsockets.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ObjJsonSender implements Serializable {
    @SerializedName("keys")
    public List<Key> Main = new ArrayList<>();
    @SerializedName("moves")
    public List<FigureType> figures = new ArrayList<>();

    public static class Key {
        @SerializedName("type")
        public String key;

        @SerializedName("status")
        public String code;

        public Key(String key, String value) {
            this.key = key;
            this.code = value;
        }
    }
}