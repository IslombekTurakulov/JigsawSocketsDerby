package ru.hse.iuturakulov.clientside.models;

import org.json.JSONObject;
import ru.hse.iuturakulov.clientside.controllers.RatingListController;
import ru.hse.iuturakulov.clientside.network.ServerSocket;
import ru.hse.iuturakulov.clientside.utils.JSONSender;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Rating players.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see RatingListController
 */
public class RatingPlayers {

    /**
     * The constant ratingPlayers.
     */
    public static ArrayList<RatingPlayers> ratingPlayers = new ArrayList<>();

    private final int gameID;
    private final String loginPlayerGame;
    private final Timestamp endDateTime;
    private final int playerPlacedBlocks;
    private final String gameTime;

    /**
     * Instantiates a new Rating players.
     *
     * @param gameID             the game id
     * @param loginPlayerGame    the login player game
     * @param endDateTime        the end date time
     * @param playerPlacedBlocks the player placed blocks
     * @param gameTime           the game time
     */
    public RatingPlayers(int gameID, String loginPlayerGame, Timestamp endDateTime, int playerPlacedBlocks, String gameTime) {
        this.gameID = gameID;
        this.loginPlayerGame = loginPlayerGame;
        this.endDateTime = endDateTime;
        this.playerPlacedBlocks = playerPlacedBlocks;
        this.gameTime = gameTime;
    }

    /**
     * Parse top ratings.
     *
     * @param response the response
     */
    public static void parseTopRatings(JSONObject response) {
        ratingPlayers.clear();
        for (Object object : response.getJSONArray("games")) {
            JSONObject parsedObj = ((JSONObject) object);
            int gameID = parsedObj.getInt("gameID");
            int placed = parsedObj.getInt("placed");
            String loginPlayer = parsedObj.getString("loginPlayer");
            String endDateTime = !parsedObj.isNull("endDateTime") ? parsedObj.getString("endDateTime") : "";
            String gameTime = !parsedObj.isNull("gameTime") ? parsedObj.getString("gameTime") : "";
            ratingPlayers.add(new RatingPlayers(gameID, loginPlayer, parseStringToTimestamp(endDateTime), placed, gameTime));
        }
    }

    private static Timestamp parseStringToTimestamp(String data) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return new Timestamp(dateFormat.parse(data).getTime());
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets rating list.
     */
    public static void getRatingList() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "top_rating");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Gets login player game.
     *
     * @return the login player game
     */
    public String getLoginPlayerGame() {
        return loginPlayerGame;
    }

    /**
     * Gets end date time.
     *
     * @return the end date time
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * Gets player placed blocks.
     *
     * @return the player placed blocks
     */
    public int getPlayerPlacedBlocks() {
        return playerPlacedBlocks;
    }

    /**
     * Gets game time.
     *
     * @return the game time
     */
    public String getGameTime() {
        return gameTime;
    }

    /**
     * Gets game id.
     *
     * @return the game id
     */
    public int getGameID() {
        return gameID;
    }
}
