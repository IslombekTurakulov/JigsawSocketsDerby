package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.jigsawbysockets.contollers.GameFormController;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Tiles;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.*;

public class Game {
    public static ArrayList<FigureType> array = new ArrayList<>();
    private static Game currentPlayingGame;
    private static Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    private static Pane gamePane;
    private static int placedBlocks;
    private static String gameMode;
    private static int currentIndexShape;
    private static Player playingPerson;
    private static Player otherPlayingPerson;
    private int id;
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
        if (currentIndexShape + 1 >= array.size()) {
            currentIndexShape = 0;
            LOGGER.log(Level.INFO, "Creating a request for another 10 shapes..");
            currentPlayingGame.sendMoreShape();
        }
        if (GameFormController.figure.isDisable()) {
            // TODO: make a method play request to place points
            play(placedBlocks);
            GameFormController.figure = new Figure(Game.getGamePane().getPrefWidth() / 3, HEIGHT_CELL * (SIZE + 2) + 20);
            gamePane.getChildren().add(GameFormController.figure);
        }
    }

    public static FigureType getNextShape() {
        return array.get(currentIndexShape++);
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
        ArrayList<Integer> listAxisX = new ArrayList<>();
        ArrayList<Integer> listAxisY = new ArrayList<>();
        getApproxLayoutPlace(figure, listAxisX, listAxisY);
        int i = 0;
        while (i < listAxisX.size()) {
            if (listAxisY.get(i) < 0 || listAxisY.get(i) >= WIDTH_CELL || listAxisX.get(i) < 0 || listAxisX.get(i) >= HEIGHT_CELL || board[listAxisY.get(i)][listAxisX.get(i)].getFill() != Color.TRANSPARENT) {
                return false;
            }
            i++;
        }
        i = 0;
        for (; i < listAxisX.size(); i++) {
            board[listAxisY.get(i)][listAxisX.get(i)].setFill(figure.getRectangleList().get(0).getFill());
        }
        gamePane.getChildren().remove(figure);
        figure.setDisable(true);
        placedBlocks++;
        return true;
    }

    private static void getApproxLayoutPlace(Figure figure, ArrayList<Integer> listAxisX, ArrayList<Integer> listAxisY) {
        for (Rectangle rectangle : figure.getRectangleList()) {
            double columns = (figure.getLayoutX() + rectangle.getX() - 2) / (SIZE + 2);
            double rows = (figure.getLayoutY() + rectangle.getY() - 2) / (SIZE + 2);
            int approxX = (int) Math.round(columns);
            int approxY = (int) Math.round(rows);
            if (approxX >= 0 && approxX + 0.5 <= columns) {
                approxX++;
            }
            if (approxY >= 0 && approxY + 0.5 <= rows) {
                approxY++;
            }
            listAxisX.add(approxX);
            listAxisY.add(approxY);
        }
    }

    public static String getGameMode() {
        return gameMode;
    }

    public static void setGameMode(String gameMode) {
        Game.gameMode = gameMode;
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
        gamePane.setVisible(true);
    }

    public static void play(int index) {
        // Game.currentGame.play(index - 1);
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play");
        jsonSender.putRequest("placed", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public void sendMoreShape() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "more_shape");
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

    public static Player getOtherPlayingPerson() {
        return otherPlayingPerson;
    }

    public void setOtherPlayingPerson(Player otherPlayingPerson) {
        Game.otherPlayingPerson = otherPlayingPerson;
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

    public static Player getPlayingPerson() {
        return playingPerson;
    }


    private void createBoard() {
        createPane();
        board = new Rectangle[HEIGHT_CELL][WIDTH_CELL];
        for (int rows = 0; rows < HEIGHT_CELL; rows++) {
            for (int columns = 0; columns < WIDTH_CELL; columns++) {
                Tiles tilePane = new Tiles(columns, rows, new Rectangle(SIZE, SIZE));
                board[rows][columns] = tilePane.tileCreation(Color.TRANSPARENT);
                board[rows][columns].setStroke(Paint.valueOf("#FFFFFF"));
                gamePane.getChildren().add(board[rows][columns]);
            }
        }
        GameFormController.figure = new Figure(Game.getGamePane().getPrefWidth() / 3, HEIGHT_CELL * (SIZE + 2) + 20);
        Game.getGamePane().getChildren().add(GameFormController.figure);
    }
}
