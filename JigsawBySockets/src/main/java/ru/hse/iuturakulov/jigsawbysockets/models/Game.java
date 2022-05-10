package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.jigsawbysockets.models.enums.ShapeType;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.HEIGHT_CELL;
import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.WIDTH_CELL;

public class Game {
    private static Game currentPlayingGame;
    private int id;
    public static ArrayList<ShapeType> array = new ArrayList<ShapeType>();
    private Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    private Player playingPerson;
    private Player otherPlayingPerson;
    private Timer currentGameTime;


    public Game(int id, List<Player> players) {
        this.id = id;
        playingPerson = players.get(0);
        otherPlayingPerson = players.get(1);
        currentGameTime = new Timer();
    }

    public Game () {
        currentGameTime = new Timer();
    }

    public Game(Player players) {
        setOtherPlayingPerson(players);
    }

    public static Game getCurrentPlayingGame() {
        return currentPlayingGame;
    }

    public static void setCurrentPlayingGame(Game currentPlayingGame) {
        Game.currentPlayingGame = currentPlayingGame;
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

    public Rectangle[][] getBoard() {
        return board;
    }

    public void setBoard(Rectangle[][] board) {
        this.board = board;
    }
}
