package ru.hse.iuturakulov.jigsawbysockets.models;

import org.json.JSONObject;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class RatingPlayers {

    public static ArrayList<RatingPlayers> ratingPlayers = new ArrayList<>();

    private final int gameID;
    private final String loginPlayerGame;
    private final Timestamp endDateTime;
    private final int playerPlacedBlocks;
    private final String gameTime;

    public RatingPlayers(int gameID, String loginPlayerGame, Timestamp endDateTime, int playerPlacedBlocks, String gameTime) {
        this.gameID = gameID;
        this.loginPlayerGame = loginPlayerGame;
        this.endDateTime = endDateTime;
        this.playerPlacedBlocks = playerPlacedBlocks;
        this.gameTime = gameTime;
    }

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

    public static void getRatingList() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "top_rating");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public String getLoginPlayerGame() {
        return loginPlayerGame;
    }

    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    public int getPlayerPlacedBlocks() {
        return playerPlacedBlocks;
    }

    public String getGameTime() {
        return gameTime;
    }

    public int getGameID() {
        return gameID;
    }
}
