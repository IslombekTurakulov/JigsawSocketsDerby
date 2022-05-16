package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.logging.Level;

/**
 * The type Player.
 */
public class Player {
    private static Player player = null;
    private String username;
    private int placed;

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name   the name
     * @param placed the placed
     */
    public Player(String name, int placed) {
        username = name;
        this.placed = placed;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name) {
        username = name;
    }

    /**
     * Gets online list.
     */
    public static void getOnlineList() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "online_players");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Login.
     *
     * @param name the name
     */
    public static void login(String name) {
        Constants.LOGGER.log(Level.INFO, "Sending login request..");
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "login");
        jsonSender.putRequest("username", name);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Logout.
     */
    public static void logout() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "logout");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Register.
     *
     * @param playerName the player name
     */
    public static void register(String playerName) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "register");
        jsonSender.putRequest("username", playerName);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Gets player.
     *
     * @return the player
     */
    public static Player getPlayer() {
        return player;
    }

    /**
     * Sets player.
     *
     * @param player the player
     */
    public static void setPlayer(Player player) {
        Player.player = player;
    }

    /**
     * Increment placed blocks.
     *
     * @param point the point
     */
    public void incrementPlacedBlocks(int point) {
        placed += point;
    }

    /**
     * Gets placed.
     *
     * @return the placed
     */
    public int getPlaced() {
        return placed;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets placed blocks.
     *
     * @param placed the placed
     */
    public void setPlacedBlocks(int placed) {
        this.placed = placed;
    }
}
