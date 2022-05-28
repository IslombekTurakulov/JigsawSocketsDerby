package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.network.ServerHandler;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.UUID;
import java.util.logging.Level;

/**
 * The type Player.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ServerHandler
 * @see Game
 */
public class Player {
    private static Player player = null;
    private final String username;
    private final UUID uuidPlayer;
    private int placed;

    /**
     * Instantiates a new Player.
     *
     * @param name   the name
     * @param placed the placed
     */
    public Player(String name, String uuidPlayer, int placed) {
        username = name;
        this.uuidPlayer = UUID.fromString(uuidPlayer);
        this.placed = placed;
    }

    /**
     * Instantiates a new Player.
     *
     * @param name the name
     */
    public Player(String name, String uuidPlayer) {
        username = name;
        this.uuidPlayer = UUID.fromString(uuidPlayer);
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
    public static void login(String name, String uuidPlayer) {
        Constants.LOGGER.log(Level.INFO, "Sending login request..");
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "login");
        jsonSender.putRequest("username", name);
        jsonSender.putRequest("uuidPlayer", uuidPlayer);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
        //  if (ServerSocket.serverSocket != null)
    }

    /**
     * Logout.
     */
    public static void logout() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "logout");
        // Just for information, who is logging out
        jsonSender.putRequest("name", getPlayer().getUsername());
        jsonSender.putRequest("uuidPlayer", getPlayer().getUuid());
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

    public String getUuid() {
        return String.valueOf(uuidPlayer);
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
