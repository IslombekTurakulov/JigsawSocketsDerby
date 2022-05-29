package ru.hse.iuturakulov.clientside.models;

import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.clientside.controllers.GameFormController;
import ru.hse.iuturakulov.clientside.models.shapes.Figure;
import ru.hse.iuturakulov.clientside.models.shapes.Tiles;
import ru.hse.iuturakulov.clientside.network.ServerHandler;
import ru.hse.iuturakulov.clientside.network.ServerSocket;
import ru.hse.iuturakulov.clientside.utils.JSONSender;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;

import static ru.hse.iuturakulov.clientside.utils.Constants.*;

/**
 * The Game logic.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see FigureType
 */
public class Game {
    /**
     * The constant array.
     */
    public final static ArrayList<FigureType> array = new ArrayList<>();
    /**
     * The constant timeline.
     */
    public static Timeline timeline;
    /**
     * The constant localTime.
     */
    public static LocalTime localTime;
    private static Game currentPlayingGame;
    private static Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    private static Pane gamePane;
    private static int placedBlocks;
    private static String gameMode;
    private static int currentIndexShape;
    private static Player playingPerson;
    private static Player otherPlayingPerson;
    private static boolean isGameStopped;
    private static boolean isNeedToPlace;
    private static String currentGameTime;
    private static String gameTimeLeft;
    private static Boolean isPlayingGame;
    /**
     * Instantiates a new Game.
     *
     * @param id      the id
     * @param players the players
     */
    public Game(int id, List<Player> players) {
        playingPerson = players.get(0);
        otherPlayingPerson = players.get(1);
        placedBlocks = 0;
        Game.createBoard();
    }
    /**
     * Instantiates a new Game.
     */
    public Game() {
        placedBlocks = 0;
        playingPerson = new Player(Player.getPlayer().getUsername(), Player.getPlayer().getUuid(), 0);
        Game.createBoard();
    }

    /**
     * Instantiates a new Game.
     *
     * @param players the players
     */
    public Game(Player players) {
        setOtherPlayingPerson(players);
    }

    public static boolean isIsNeedToPlace() {
        return isNeedToPlace;
    }

    public static void setIsNeedToPlace(boolean isNeedToPlace) {
        Game.isNeedToPlace = isNeedToPlace;
    }

    /**
     * Gets game time left.
     *
     * @return the game time left
     */
    public static String getGameTimeLeft() {
        return gameTimeLeft;
    }

    /**
     * Sets game time left.
     *
     * @param gameTimeLeft the game time left
     */
    public static void setGameTimeLeft(String gameTimeLeft) {
        Game.gameTimeLeft = gameTimeLeft;
    }

    /**
     * Gets is playing game.
     *
     * @return the is playing game
     */
    public static Boolean getIsPlayingGame() {
        return isPlayingGame;
    }

    /**
     * Sets is playing game.
     *
     * @param isPlayingGame the is playing game
     */
    public static void setIsPlayingGame(Boolean isPlayingGame) {
        Game.isPlayingGame = isPlayingGame;
    }

    /**
     * Sets current index shape.
     *
     * @param currentIndexShape the current index shape
     */
    public static void setCurrentIndexShape(int currentIndexShape) {
        Game.currentIndexShape = currentIndexShape;
    }

    /**
     * Gets placed blocks.
     *
     * @return the placed blocks
     */
    public static int getPlacedBlocks() {
        return placedBlocks;
    }

    /**
     * Sets placed blocks.
     *
     * @param index the index
     */
    public static void setPlacedBlocks(int index) {
        placedBlocks = index;
    }

    /**
     * Gets game pane.
     *
     * @return the game pane
     */
    public static Pane getGamePane() {
        return gamePane;
    }

    /**
     * Check for new figure.
     */
    public static void checkForNewFigure() {
        if (!isIsNeedToPlace()) {
            checkExtraShapes();
            if (GameFormController.figure.isDisable()) {
                play(placedBlocks);
                GameFormController.figure = new Figure(Game.getGamePane().getPrefWidth() / 3, HEIGHT_CELL * (SIZE + 2) + 20);
                gamePane.getChildren().add(GameFormController.figure);
            }
        }
    }

    /**
     * Check extra shapes.
     */
    public static void checkExtraShapes() {
        if (currentIndexShape + 2 >= array.size()) {
            currentIndexShape = 0;
            LOGGER.log(Level.INFO, "Creating a request for another 20 shapes..");
            currentPlayingGame.sendMoreShape();
        }
    }

    /**
     * Gets next shape.
     *
     * @return the next shape
     */
    public static FigureType getNextShape() {
        return array.get(currentIndexShape++);
    }

    /**
     * Gets current playing game.
     *
     * @return the current playing game
     */
    public static Game getCurrentPlayingGame() {
        return Game.currentPlayingGame;
    }

    /**
     * Sets current playing game.
     *
     * @param currentPlayingGame the current playing game
     */
    public static void setCurrentPlayingGame(Game currentPlayingGame) {
        Game.currentPlayingGame = currentPlayingGame;
    }

    /**
     * Is possible to place boolean.
     *
     * @param figure the figure
     * @return the boolean
     */
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

    /**
     * Gets game mode.
     *
     * @return the game mode
     */
    public static String getGameMode() {
        return gameMode;
    }

    /**
     * Sets game mode.
     *
     * @param gameMode the game mode
     */
    public static void setGameMode(String gameMode) {
        Game.gameMode = gameMode;
    }

    /**
     * Play.
     *
     * @param index the index
     */
    public static void play(int index) {
        // Game.currentGame.play(index - 1);
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "play");
        jsonSender.putRequest("player", Player.getPlayer().getUsername());
        jsonSender.putRequest("placed", index);
        jsonSender.putRequest("playerUuid", Player.getPlayer().getUuid());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Finish single game.
     */
    public static void finishSingleGame() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "single_player_finished");
        jsonSender.putRequest("name", Player.getPlayer().getUsername());
        jsonSender.putRequest("uuidPlayer", Player.getPlayer().getUuid());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Save game.
     *
     * @param player the player
     * @param index  the index
     */
    public static void saveGame(Player player, int index) {
        // game_id,login_player,end_game_date,placed_blocks,time_game
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss.");
        format.setTimeZone(TimeZone.getDefault());
        jsonSender.putRequest("function", "save_game");
        jsonSender.putRequest("login_player", player.getUsername());
        jsonSender.putRequest("end_game_date", format.format(timestamp) + String.format("%09d", timestamp.getNanos()));
        jsonSender.putRequest("placed_blocks", index);
        jsonSender.putRequest("time_game", getGameTimeLeft());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Finish multiplayer game.
     *
     * @param index the index
     */
    public static void finishMultiplayerGame(int index) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "multiplayer_finished");
        jsonSender.putRequest("name", Game.getOtherPlayingPerson().getUsername());
        jsonSender.putRequest("uuidPlayer", Game.getOtherPlayingPerson().getUuid());
        jsonSender.putRequest("placed", index);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Gets other playing person.
     *
     * @return the other playing person
     */
    public static Player getOtherPlayingPerson() {
        return otherPlayingPerson;
    }

    /**
     * Sets other playing person.
     *
     * @param otherPlayingPerson the other playing person
     */
    public void setOtherPlayingPerson(Player otherPlayingPerson) {
        Game.otherPlayingPerson = otherPlayingPerson;
    }

    /**
     * Is game stopped boolean.
     *
     * @return the boolean
     */
    public static boolean isGameStopped() {
        return isGameStopped;
    }

    /**
     * Reject game invite.
     */
    public static void rejectGameInvite() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "invite_decline");
        jsonSender.putRequest("opponent", ServerHandler.otherPlayingPlayer);
        jsonSender.putRequest("uuidPlayer", ServerHandler.otherPlayingPlayerUUID);
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Reject multiplayer game invite.
     *
     * @param name the name
     * @param uuid the uuid
     */
    public static void rejectMultiplayerGameInvite(String name, String uuid) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "invite_decline_for_game");
        jsonSender.putRequest("opponent", name);
        jsonSender.putRequest("opponentUUID", uuid);
        jsonSender.putRequest("cause", "This player is playing another game");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Accept game invite.
     */
    public static void acceptGameInvite() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "invite_accept");
        jsonSender.putRequest("opponent", ServerHandler.otherPlayingPlayer);
        jsonSender.putRequest("opponentUUID", ServerHandler.otherPlayingPlayerUUID);
        jsonSender.putRequest("acceptedPlayer", Player.getPlayer().getUsername());
        jsonSender.putRequest("acceptedPlayerUUID", Player.getPlayer().getUuid());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    /**
     * Sets is game stopped.
     *
     * @param isGameStopped the is game stopped
     */
    public static void setIsGameStopped(boolean isGameStopped) {
        Game.isGameStopped = isGameStopped;
    }

    /**
     * Leave.
     */
    public static void leave() {
        if (Game.currentPlayingGame != null) {
            Game.currentPlayingGame = null;
        }
        ServerHandler.otherPlayingPlayer = null;
        ServerHandler.otherPlayingPlayerUUID = null;
    }

    /**
     * Gets playing person.
     *
     * @return the playing person
     */
    public static Player getPlayingPerson() {
        return playingPerson;
    }

    /**
     * Gets current game time.
     *
     * @return the current game time
     */
    public static String getCurrentGameTime() {
        return currentGameTime;
    }

    /**
     * Sets current game time.
     *
     * @param currentGameTime the current game time
     */
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

    /**
     * Create board.
     */
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

    /**
     * Show game info.
     */
    public static void showGameInfo() {
        System.out.println("==============================================");
        System.out.printf("Game-mode - %s%n", getGameMode());
        System.out.printf("Length of array figures - %d%n", array.size());
        System.out.printf("Current playing player - %s%n", Player.getPlayer().getUsername());
        System.out.printf("Other opponent player - %s%n", Game.getOtherPlayingPerson() != null ? Game.getOtherPlayingPerson().getUsername() : "NULL");
        System.out.printf("Max time - %s%n", getCurrentGameTime());
        System.out.printf("Is game stopped - %s%n", isGameStopped());
        System.out.println("==============================================");
        if (array.isEmpty()) {
            checkExtraShapes();
        }
    }

    /**
     * Send more shape.
     */
    public void sendMoreShape() {
        if (currentPlayingGame != null) {
            JSONSender jsonSender = JSONSender.getInstance();
            jsonSender.clearRequests();
            jsonSender.putRequest("function", "more_shape");
            jsonSender.putRequest("mode", Game.getGameMode().equals("Single-player") ? "single_player" : "multi_player");
            ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
        }
    }

    /**
     * Send game request.
     */
    public void sendGameRequest() {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.clearRequests();
        jsonSender.putRequest("function", "invite_request");
        jsonSender.putRequest("opponent", getOtherPlayingPerson().getUsername());
        jsonSender.putRequest("opponentUUID", getOtherPlayingPerson().getUuid());
        jsonSender.putRequest("inviter", Player.getPlayer().getUsername());
        jsonSender.putRequest("uuidInviter", Player.getPlayer().getUuid());
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

}
