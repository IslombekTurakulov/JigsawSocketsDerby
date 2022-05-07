package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

public class Player {
    private static int uniqueId;
    private final String playerName;
    private int playerPoints;
    private Boolean isPlayerOnline;

    public Player(String name, int points) {
        playerName = name;
        playerPoints = points;
    }

    public static void login(String text, String text1) {


        uniqueId = Constants.uniqueId.incrementAndGet();
    }

    public static void logout() {

    }


    public static void register(String password) {

    }

    public static int getUniqueId() {
        return uniqueId;
    }

    public void incrementPoints(int point) {
        playerPoints += point;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public String getPlayerName() {
        return playerName;
    }
}
