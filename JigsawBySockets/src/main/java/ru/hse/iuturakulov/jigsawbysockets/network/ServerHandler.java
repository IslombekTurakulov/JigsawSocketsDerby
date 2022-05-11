package ru.hse.iuturakulov.jigsawbysockets.network;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONObject;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.FigureType;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.DialogCreator;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket.serverSocket;

public class ServerHandler {

    public static Set<Player> gameSet = new HashSet<>();
    public static Set<Player> playerSet = new HashSet<>();
    public static String otherPlayingPlayer;

    public static void handleAuthOrRegister(JSONObject jsonResponse) {
        if (isIt(jsonResponse, "status", "success")) {
            if (isIt(jsonResponse, "type", "login")) {
                JSONObject parsed = jsonResponse.getJSONObject("player");
                Player.setPlayer(new Player(parsed.getString("username"), parsed.getInt("placed")));
                App.setRoot("main_form");
                Constants.LOGGER.log(Level.INFO, "Connected.. Opening Main home form");
            } else {
                App.setRoot("login_form");
            }
        } else {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Auth Failure", jsonResponse.getString("message"), false);
        }
    }

    public static void handlePlayers() {

    }

    public static void initializeResponses(String response) throws IOException {
        Constants.LOGGER.log(Level.INFO, response);
        if (!JSONSender.getInstance().validateJSON(response)) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "JSON is invalid!", "Try again", false);
            Constants.LOGGER.log(Level.WARNING, "JSON -> %s  is invalid".formatted(response));
        }
        // JsonResponse jsonResponse = new Gson().fromJson(response, JsonResponse.class);
        JSONObject parsedResponse = new JSONObject(response);

        String function = "type";
        if (Player.getPlayer() == null && (isIt(parsedResponse, function, "login"))) {
            handleAuthOrRegister(parsedResponse);
        } else if (isIt(parsedResponse, function, "logout")) {
            Player.setPlayer(new Player(parsedResponse.getString("playerName"), parsedResponse.getInt("playerPoints")));
        } else if (isIt(parsedResponse, function, "single_player")) {
            if (isIt(parsedResponse, "status", "success")) {
                JSONArray json = parsedResponse.getJSONArray("move");
                Game.array.addAll(List.of(new Gson().fromJson(json.toString(), FigureType[].class)));
                Game.setCurrentPlayingGame(new Game());
                Platform.runLater(() ->
                        App.setRoot("game_form")
                );
            } else {
                Constants.LOGGER.log(Level.SEVERE, "Fail. Couldn't Start Game");
            }
        } else if (isIt(parsedResponse, function, "multiplayer_game")) {
            if (isIt(parsedResponse, "status", "success")) {
                otherPlayingPlayer = parsedResponse.getString("opponent");
                App.setRoot("gameRequestAccept");
            } else {
                Constants.LOGGER.log(Level.INFO, "Failed playing with opponent %s".formatted(parsedResponse.getString("opponent")));
            }
        } else if (isIt(parsedResponse, function, "close_socket")) {
            if (isIt(parsedResponse, "status", "success")) {
                Constants.LOGGER.log(Level.WARNING, "Server was closed");
                Constants.LOGGER.log(Level.INFO, response);
                serverSocket.close();
                App.setRoot("login_form");
            }
        } else if (isIt(parsedResponse, function, "connected")) {

        } else if (isIt(parsedResponse, function, "message")) {

        } else if (isIt(parsedResponse, function, "invite_accept")) {

        } else if (isIt(parsedResponse, function, "invite_decline")) {

        }
    }

    private static boolean isIt(JSONObject parsedResponse, String key, String value) {
        return Objects.equals(parsedResponse.getString(key), value);
    }

}
