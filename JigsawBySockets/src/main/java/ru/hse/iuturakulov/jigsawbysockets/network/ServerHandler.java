package ru.hse.iuturakulov.jigsawbysockets.network;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.json.JSONObject;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.models.ObjJsonSender;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.DialogCreator;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket.serverSocket;

/**
 * The type Server handler.
 */
public class ServerHandler {

    /**
     * The constant playerSet.
     */
    public static ArrayList<Player> playerSet = new ArrayList<>();
    /**
     * The constant otherPlayingPlayer.
     */
    public static String otherPlayingPlayer;
    public static String otherPlayingPlayerUUID;

    /**
     * Handle auth or register.
     *
     * @param jsonResponse the json response
     */
    public static void handleAuthOrRegister(JSONObject jsonResponse) {
        if (isIt(jsonResponse, "status", "success")) {
            if (isIt(jsonResponse, "type", "login")) {
                JSONObject parsed = jsonResponse.getJSONObject("player");
                Player.setPlayer(new Player(parsed.getString("username"), parsed.getString("uuidPlayer"), parsed.getInt("placed")));
                App.setRoot("main_form");
                Constants.LOGGER.log(Level.INFO, "Connected.. Opening Main home form");
            } else {
                App.setRoot("login_form");
            }
        } else {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Auth Failure", jsonResponse.getString("message"), false);
        }
    }

    /**
     * Initialize responses.
     *
     * @param response the response
     * @throws IOException the io exception
     */
    public static void initializeResponses(String response) throws IOException {
        System.out.println(response);
        if (checkTheGameSinglePlayer(response)) {
            return;
        }
        if (checkTheGameMultiPlayer(response)) {
            return;
        }
        if (checkTheFigureShapes(response)) {
            return;
        }
        if (!JSONSender.getInstance().validateJSON(response)) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "JSON is invalid!", "Try again", false);
            Constants.LOGGER.log(Level.WARNING, "JSON -> %s  is invalid".formatted(response));
        }

        JSONObject parsedResponse = new JSONObject(response);
        String function = "type";
        if (isIt(parsedResponse, function, "play")) {
            if (Game.getOtherPlayingPerson() != null && Objects.equals(parsedResponse.getString("uuid"), Game.getOtherPlayingPerson().getUuid())) {
                Game.getOtherPlayingPerson().setPlacedBlocks(parsedResponse.getInt("placed"));
            } else {
                Player.getPlayer().setPlacedBlocks(parsedResponse.getInt("placed"));
            }
        }
        if (Player.getPlayer() == null && (isIt(parsedResponse, function, "login"))) {
            handleAuthOrRegister(parsedResponse);
        } else if (isIt(parsedResponse, function, "logout")) {
            Player.setPlayer(new Player(parsedResponse.getString("playerName"), parsedResponse.getString("uuidPlayer"), parsedResponse.getInt("playerPoints")));
        } else if (isIt(parsedResponse, function, "multiplayer_game")) {
            multiplayerGame(parsedResponse);
        } else if (isIt(parsedResponse, function, "close_socket")) {
            closeSocket(response, parsedResponse);
        } else if (isIt(parsedResponse, function, "connected")) {
            connectedNotification(parsedResponse);
        } else if (isIt(parsedResponse, function, "invite_request")) {
            handlePlayResponse(parsedResponse);
        } else if (isIt(parsedResponse, function, "invite_accept")) {
            handlePlayResponse(parsedResponse);
        } else if (isIt(parsedResponse, function, "invite_decline")) {
            declinedInvite(parsedResponse);
        } else if (isIt(parsedResponse, function, "get-online-players")) {
            handlePlayersList(response);
        } else if (isIt(parsedResponse, function, "game-finish")) {
            if (isIt(parsedResponse, "status", "success")) {
                Constants.LOGGER.log(Level.WARNING, "Force finish game");
                Game.setIsGameStopped(true);
            }
        }
    }

    private static boolean checkTheFigureShapes(String response) {
        if (JSONSender.getInstance().validateGson(response)) {
            if ((response.contains("keys") && response.contains("get_shapes"))) {
                Game.array.clear();
                Game.array.addAll(new Gson().fromJson(response, ObjJsonSender.class).moves);
                Constants.LOGGER.log(Level.INFO, "New figures got. Total decoded size is %d".formatted(Game.array.size()));
                return true;
            }
        }
        return false;
    }

    private static boolean checkTheGameMultiPlayer(String response) {
        if (JSONSender.getInstance().validateGson(response)) {
            if (response.contains("keys") && response.contains("start_multi_player")) {
                ObjJsonSender obj = new Gson().fromJson(response, ObjJsonSender.class);
                Game.setIsGameStopped(false);
                Game.array.clear();
                Game.setCurrentIndexShape(0);
                Game.setPlacedBlocks(0);
                Game.array.addAll(obj.moves);
                Constants.LOGGER.log(Level.INFO, "Figures got. Total decoded size is " + Game.array.size());
                Player opponent = new Player(obj.keys.get(1).getCode(), obj.keys.get(3).getCode());
                Game.setCurrentPlayingGame(new Game(opponent));
                Game.setCurrentGameTime(obj.keys.get(2).getCode());
                Game.setGameMode("Multi-player");
                Platform.runLater(() -> App.setRoot("game_form"));
                return true;
            }
        }
        return false;
    }

    private static boolean checkTheGameSinglePlayer(String response) {
        if (JSONSender.getInstance().validateGson(response)) {
            if ((response.contains("keys") && response.contains("single_player"))) {
                Game.leave();
                ObjJsonSender obj = new Gson().fromJson(response, ObjJsonSender.class);
                Game.setIsPlayingGame(false);
                if (!Game.array.isEmpty()) {
                    Game.array.clear();
                    Game.setCurrentIndexShape(0);
                    Game.setPlacedBlocks(0);
                }
                Game.array.addAll(obj.moves);
                Constants.LOGGER.log(Level.INFO, "Figures got. Total decoded size is %d".formatted(Game.array.size()));
                Game.setCurrentPlayingGame(new Game());
                Game.setCurrentGameTime(obj.keys.get(1).getCode());
                Game.setGameMode("Single-player");
                Platform.runLater(() ->
                        App.setRoot("game_form")
                );
                return true;
            }
        }
        return false;
    }

    private static void closeSocket(String response, JSONObject parsedResponse) throws IOException {
        if (isIt(parsedResponse, "status", "success")) {
            Constants.LOGGER.log(Level.WARNING, "Server was closed");
            Constants.LOGGER.log(Level.INFO, response);
            Player.setPlayer(null);
            serverSocket.close();
            App.setRoot("login_form");
        }
    }

    private static void connectedNotification(JSONObject parsedResponse) {
        String player = parsedResponse.getString("player");
        Constants.LOGGER.log(Level.INFO, "Player %s connected".formatted(player));
    }

    private static void declinedInvite(JSONObject parsedResponse) {
        if (Game.getCurrentPlayingGame() != null) {
            Game.setCurrentPlayingGame(null);
        }
        DialogCreator.showCustomDialog(Alert.AlertType.INFORMATION, "Canceled", "Player Request has been canceled", false);
        Platform.runLater(() -> App.setRoot("main_form"));
    }

    private static void multiplayerGame(JSONObject parsedResponse) {
        if (isIt(parsedResponse, "value", "success")) {
            otherPlayingPlayer = parsedResponse.getString("opponent");
            otherPlayingPlayerUUID = parsedResponse.getString("uuidPlayer");
            App.setRoot("game_request_accept_form");
        } else {
            Constants.LOGGER.log(Level.INFO, "Failed playing with opponent %s".formatted(parsedResponse.getString("opponent")));
        }
    }

    private static void handlePlayResponse(JSONObject response) {
        String status = response.getString("status");
        if (status.equals("success")) {
            if (Game.getPlayingPerson() == null || (Game.getIsPlayingGame() != null && !Game.getIsPlayingGame())) {
                otherPlayingPlayer = response.getString("opponent");
                otherPlayingPlayerUUID = response.getString("uuidPlayer");
                Platform.runLater(() -> App.setRoot("game_request_accept_form"));
            } else {
                Game.rejectMultiplayerGameInvite(response.getString("inviter"), response.getString("uuidInviter"));
            }
        } else {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Failed", response.getString("opponent"), false);
            Platform.runLater(() -> App.setRoot("main_form"));
        }
    }

    private static void handlePlayersList(String resp) {
        playerSet.clear();
        JSONObject JsonObj = new JSONObject(resp);
        for (Object object : JsonObj.getJSONArray("players")) {
            playerSet.add(new Player(((JSONObject) object).getString("username"), ((JSONObject) object).getString("uuidPlayer"), ((JSONObject) object).getInt("placed")));
        }
    }

    private static boolean isIt(JSONObject parsedResponse, String key, String value) {
        return Objects.equals(parsedResponse.getString(key), value);
    }

}
