package ru.hse.iuturakulov.jigsawbysockets.network;

import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class ServerHandler {

    public static Set<Player> gameSet = new HashSet<>();
    public static Set<Player> playerSet = new HashSet<>();

    public static void handleAuth() {

    }

    public static void handleRegister(String playerName) {

    }

    public static void handlePlayers() {

    }

    public static void initialize(String response) {
        Constants.LOGGER.log(Level.INFO, response);



    }


}
