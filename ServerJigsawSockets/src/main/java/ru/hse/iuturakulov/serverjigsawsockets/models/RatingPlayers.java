package ru.hse.iuturakulov.serverjigsawsockets.models;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.hse.iuturakulov.serverjigsawsockets.network.DatabaseDerbyAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RatingPlayers {

    public static RatingPlayers getInstance() {
        return SingletonHolder.RATING_PLAYERS;
    }

    public JSONArray getTopPlayers() {
        JSONArray gamesArray = new JSONArray();
        DatabaseDerbyAccess derbyAccess = new DatabaseDerbyAccess();
        String sql = "SELECT GAME_ID, LOGIN_PLAYER, END_GAME_DATE, PLACED_BLOCKS, TIME_GAME " +
                     "FROM RATING_LIST ORDER BY END_GAME_DATE desc, TIME_GAME desc, PLACED_BLOCKS desc" +
                     " fetch first 10 rows only";
        try (PreparedStatement statement = derbyAccess.getConnection().prepareStatement(sql)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                JSONObject gameJSON = new JSONObject();
                gameJSON.put("gameID", result.getInt("game_id"));
                gameJSON.put("loginPlayer", result.getString("login_player"));
                gameJSON.put("endDateTime", result.getString("end_game_date"));
                gameJSON.put("placed", result.getString("placed_blocks"));
                gameJSON.put("gameTime", result.getString("time_game"));
                gamesArray.put(gameJSON);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            derbyAccess.close();
        }
        return gamesArray;
    }

    /**
     * The type Singleton holder.
     */
    public static class SingletonHolder {
        /**
         * The constant RATING_PLAYERS.
         */
        public static final RatingPlayers RATING_PLAYERS = new RatingPlayers();
    }
}
