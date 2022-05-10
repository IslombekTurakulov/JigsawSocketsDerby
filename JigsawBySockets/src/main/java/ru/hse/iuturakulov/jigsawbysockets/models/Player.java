package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.network.ServerHandler;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.logging.Level;

public class Player {
    private static int uniqueId;
    private final String playerName;
    private int playerPoints;
    private static Player player = null;
    private Boolean isPlayerOnline;

    public Player(String name, int points) {
        playerName = name;
        playerPoints = points;
        isPlayerOnline = false;
    }

    public Player(String name) {
        playerName = name;
    }

    public static void login(String name) {
        Constants.LOGGER.log(Level.INFO, "Sending login request..");
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "login");
        jsonSender.putRequest("username", name);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
        uniqueId = Constants.uniqueId.incrementAndGet();
    }

    public static void logout() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "logout");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public static void register(String playerName) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "register");
        jsonSender.putRequest("username", playerName);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public static int getUniqueId() {
        return uniqueId;
    }

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        Player.player = player;
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

    public Boolean getPlayerOnline() {
        return isPlayerOnline;
    }

    public void setPlayerOnline(Boolean playerOnline) {
        isPlayerOnline = playerOnline;
    }
}
