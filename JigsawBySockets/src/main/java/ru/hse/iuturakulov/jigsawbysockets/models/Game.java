package ru.hse.iuturakulov.jigsawbysockets.models;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.jigsawbysockets.controllers.GameFormController;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Tiles;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerHandler;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.*;

public class Game {
    public final static ArrayList<FigureType> array = new ArrayList<>();
    public static String winner;
    private static Game currentPlayingGame;
    private static Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    private static Pane gamePane;
    private static int placedBlocks;
    private static String gameMode;
    private static int currentIndexShape;
    private static Player playingPerson;
    private static Player otherPlayingPerson;
    private static boolean isGameStopped;
    private static String currentGameTime;
    private int id;

    public Game(int id, List<Player> players) {
        this.id = id;
        playingPerson = players.get(0);
        otherPlayingPerson = players.get(1);
        placedBlocks = 0;
        Game.createBoard();
    }

    public Game() {
        placedBlocks = 0;
        playingPerson = new Player(Player.getPlayer().getUsername(), 0);
        Game.createBoard();
    }

    public Game(Player players) {
        setOtherPlayingPerson(players);
    }

    public static void setCurrentIndexShape(int currentIndexShape) {
        Game.currentIndexShape = currentIndexShape;
    }

    public static int getPlacedBlocks() {
        return placedBlocks;
    }

    public static Pane getGamePane() {
        return gamePane;
    }

    public static void checkForNewFigure() {
        if (!isGameStopped) {
            checkExtraShapes();
            if (GameFormController.figure.isDisable()) {
                play(placedBlocks);
                GameFormController.figure = new Figure(Game.getGamePane().getPrefWidth() / 3, HEIGHT_CELL * (SIZE + 2) + 20);
                gamePane.getChildren().add(GameFormController.figure);
            }
        }
    }

    public static void checkExtraShapes() {
        if (currentIndexShape + 1 >= array.size()) {
            currentIndexShape = 0;
            LOGGER.log(Level.INFO, "Creating a request for another 10 shapes..");
            currentPlayingGame.sendMoreShape();
        }
    }

    public static FigureType getNextShape() {
        return array.get(currentIndexShape++);
    }

    public static Game getCurrentPlayingGame() {
        return Game.currentPlayingGame;
    }

    public static void setCurrentPlayingGame(Game currentPlayingGame) {
        Game.currentPlayingGame = currentPlayingGame;
    }

    public static boolean isPossibleToPlace(Figure figure) {
        ArrayList<Integer> listAxisX = new ArrayList<>();
        ArrayList<Integer> listAxisY = new ArrayList<>();
        getApproxLayoutPlace(figure, listAxisX, listAxisY);
        int i = 0;
        while (i < listAxisX.size()) {
            if (listAxisY.get(i) < 0 || listAxisY.get(i) >= WIDTH_CELL || listAxisX.get(i) < 0) {
                return false;
            } else if (listAxisX.get(i) >= HEIGHT_CELL || board[listAxisY.get(i)][listAxisX.get(i)].getFill() != Color.TRANSPARENT) {
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

    public static void play(int index) {
        // Game.currentGame.play(index - 1);
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play");
        jsonSender.putRequest("placed", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }


    public static void finishGame(int index) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "multiplayer_finished");
        jsonSender.putRequest("name", Game.getOtherPlayingPerson().getUsername());
        jsonSender.putRequest("placed", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public static Player getOtherPlayingPerson() {
        return otherPlayingPerson;
    }

    public void setOtherPlayingPerson(Player otherPlayingPerson) {
        Game.otherPlayingPerson = otherPlayingPerson;
    }

    public static boolean isGameStopped() {
        return isGameStopped;
    }

    public static void rejectGameInvite() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "invite_decline");
        jsonSender.putRequest("opponent", ServerHandler.otherPlayingPlayer);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public static void acceptGameInvite() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "invite_accept");
        jsonSender.putRequest("opponent", ServerHandler.otherPlayingPlayer);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public static void setIsGameStopped(boolean isGameStopped) {
        Game.isGameStopped = isGameStopped;
    }

    public static void leave() {
        if (Game.currentPlayingGame != null) {
            Game.currentPlayingGame = null;
        }
        ServerHandler.otherPlayingPlayer = null;
    }

    public static Player getPlayingPerson() {
        return playingPerson;
    }

    public static String getCurrentGameTime() {
        return currentGameTime;
    }

    public static void setCurrentGameTime(String currentGameTime) {
        Game.currentGameTime = currentGameTime;
    }

    /**
     * Create right pane.
     */
    private static void createPane() {
        gamePane = new Pane();
        int width = 392;
        int height = 551;
        gamePane.setPrefSize(width + 2, height + 100);
        gamePane.setTranslateY(50);
        gamePane.setVisible(true);
    }

    public static void createBoard() {
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
        try {
            checkExtraShapes();
            GameFormController.figure = new Figure(Game.getGamePane().getPrefWidth() / 3, HEIGHT_CELL * (SIZE + 2) + 20);
            Game.getGamePane().getChildren().add(GameFormController.figure);
        } catch (IndexOutOfBoundsException | NullPointerException exception) {
            LOGGER.log(Level.SEVERE, exception.getMessage());
        }
    }

    public static void showGameInfo() {
        System.out.println("==============================================");
        System.out.printf("Game-mode - %s%n", getGameMode());
        System.out.printf("Length of array figures - %d%n", array.size());
        System.out.printf("Current playing player - %s%n", Player.getPlayer().getUsername());
        System.out.printf("Other opponent player - %s%n", Game.getOtherPlayingPerson() != null ? Game.getOtherPlayingPerson().getUsername() : "NULL");
        System.out.printf("Max time - %s%n", getCurrentGameTime());
        System.out.println("==============================================");
    }

    public void sendMoreShape() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "more_shape");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public void sendGameRequest() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "invite_request");
        jsonSender.putRequest("opponent", getOtherPlayingPerson().getUsername());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    public void sendFinishedGameRequest() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "multiplayer_finished");
        ServerSocket.sendRequest(jsonSender.toString());
    }
}
