package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.jigsawbysockets.contollers.GameFormController;
import ru.hse.iuturakulov.jigsawbysockets.models.enums.ShapeType;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.FigureType;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Tiles;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.*;

public class Game {
    public static ArrayList<FigureType> array = new ArrayList<>();
    private static Game currentPlayingGame;
    private static Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    private static Pane gamePane;
    private int id;
    private int currentIndexShape;
    private Player playingPerson;
    private Player otherPlayingPerson;
    private Timer currentGameTime;

    public Game(int id, List<Player> players) {
        this.id = id;
        playingPerson = players.get(0);
        otherPlayingPerson = players.get(1);
        currentGameTime = new Timer();
        createBoard();
    }

    public Game() {
        currentGameTime = new Timer();
        createBoard();
    }

    public Game(Player players) {
        setOtherPlayingPerson(players);
    }

    public static Pane getGamePane() {
        return gamePane;
    }

    public static void checkForNewFigure() {
        if (currentPlayingGame.currentIndexShape + 1 > array.size()) {
            currentPlayingGame.sendMoreShape();
        } else {
            if (GameFormController.figure.isDisable()) {
                gamePane.getChildren().add(new Figure(Game.getGamePane().getPrefWidth() * 1.2 / 3, HEIGHT_CELL * (SIZE + 2) + 20));
            }
        }
    }

    public static FigureType getNextShape() {
        return array.get(currentPlayingGame.currentIndexShape++);
    }

    public static Game getCurrentPlayingGame() {
        return currentPlayingGame;
    }

    public static void setCurrentPlayingGame(Game currentPlayingGame) {
        Game.currentPlayingGame = currentPlayingGame;
    }

    public static Rectangle[][] getBoard() {
        return board;
    }

    public static boolean isPossibleToPlace(Figure figure) {
        return true;
    }

    private void createBoard() {
        createPane();
        board = new Rectangle[HEIGHT_CELL][WIDTH_CELL];
        for (int rows = 0; rows < HEIGHT_CELL; rows++) {
            for (int columns = 0; columns < WIDTH_CELL; columns++) {
                Tiles tilePane = new Tiles(columns, rows, new Rectangle(SIZE, SIZE));
                board[rows][columns] = tilePane.tileCreation(Color.WHITE);
                gamePane.getChildren().add(board[rows][columns]);
            }
        }
    }

    /**
     * Create right pane.
     */
    private void createPane() {
        gamePane = new Pane();
        int width = WIDTH_CELL * (SIZE + 2);
        int height = HEIGHT_CELL * (SIZE + 2);
        gamePane.setPrefSize(width + 2, height + 200);
        gamePane.setTranslateY(50);
        gamePane.setVisible(false);
    }

    public void play(int index) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play");
        jsonSender.putRequest("index", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public void sendMoreShape() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "more_shape");
        ServerSocket.sendRequest(jsonSender.toString());
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
