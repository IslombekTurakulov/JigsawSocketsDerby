package ru.hse.iuturakulov.jigsawbysockets.models;

import java.util.List;
import java.util.Timer;

public class Game {
    public static Game currentPlayingGame;
    public int id;
    public List<Player> listOfPlayers;
    public Timer currentGameTime;


    public Game(int _id, List<Player> players) {
        id = _id;
        listOfPlayers = players;
        currentGameTime = new Timer();
    }

    public Game(List<Player> players) {
        listOfPlayers = players;
    }

    public static void acceptRequest() {


    }

    public static void rejectRequest() {

    }

    public void play(int index) {


    }

    public void sendMessage(String message) {

    }


}
