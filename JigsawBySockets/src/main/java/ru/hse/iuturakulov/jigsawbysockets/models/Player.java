package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

public class Player {
    private String userName;
    private static int uniqueId;


    public static void login(String text, String text1) {


        uniqueId = Constants.uniqueId.incrementAndGet();
    }

    public static int getUniqueId() {
        return uniqueId;
    }
}
