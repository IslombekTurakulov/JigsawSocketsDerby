package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.List;
import java.util.Timer;

public class Game {
    private static Game currentPlayingGame;
    private int id;
    private Player playingPerson;
    private Player otherPlayingPerson;
    private Timer currentGameTime;


    public Game(int _id, List<Player> players) {
        id = _id;
        playingPerson = players.get(0);
        otherPlayingPerson = players.get(1);
        currentGameTime = new Timer();
    }

    public Game(Player players) {
        setOtherPlayingPerson(players);
    }

    public void play(int index) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play");
        jsonSender.putRequest("index", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public void sendChatMessage(String message) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "msg_chat");
        jsonSender.putRequest("message", message);
    }

    public void sendGameRequest() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play_invitation");
        jsonSender.putRequest("opponent", getOtherPlayingPerson().getPlayerName());
        ServerSocket.sendRequest(jsonSender.toString());
    }


    public Player getOtherPlayingPerson() {
        return otherPlayingPerson;
    }

    public void setOtherPlayingPerson(Player otherPlayingPerson) {
        this.otherPlayingPerson = otherPlayingPerson;
    }

    public Timer getCurrentGameTime() {
        return currentGameTime;
    }

    public void setCurrentGameTime(Timer currentGameTime) {
        this.currentGameTime = currentGameTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Player getPlayingPerson() {
        return playingPerson;
    }

    public void setPlayingPerson(Player playingPerson) {
        this.playingPerson = playingPerson;
    }
}
