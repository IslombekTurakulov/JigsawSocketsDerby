package ru.hse.iuturakulov.clientside.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.hse.iuturakulov.clientside.App;
import ru.hse.iuturakulov.clientside.models.Game;
import ru.hse.iuturakulov.clientside.models.Player;
import ru.hse.iuturakulov.clientside.models.shapes.Figure;
import ru.hse.iuturakulov.clientside.network.ServerSocket;
import ru.hse.iuturakulov.clientside.utils.Constants;
import ru.hse.iuturakulov.clientside.utils.DialogCreator;
import ru.hse.iuturakulov.clientside.utils.JSONSender;

import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static ru.hse.iuturakulov.clientside.models.Game.*;
import static ru.hse.iuturakulov.clientside.utils.Constants.DATE_TIME_FORMATTER;

/**
 * The type Game form controller.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see Game
 */
public class GameFormController implements Initializable {

    /**
     * The constant figure.
     */
    public static Figure figure;
    /**
     * The Is single player.
     */
    boolean isSinglePlayer;
    @FXML
    private Button playAgainBtn;
    @FXML
    private Button exitFromGameBtn;
    @FXML
    private Text opponentPlacedBlocks;
    @FXML
    private Text yourPlacedBlocks;
    @FXML
    private Text winnerCurrentGame;
    @FXML
    private Text maxTimeForGame;
    @FXML
    private Pane tilesForGame;
    @FXML
    private Text opponentsNameForGame;
    @FXML
    private Text currentGameMode;
    @FXML
    private Text yourNameForGame;
    @FXML
    private BorderPane borderGamePane;
    @FXML
    private AnchorPane leftGamePaneTimer;
    @FXML
    private Text timeCurrentGame;
    @FXML
    private Pane gameEndInfoPane;


    /**
     * Increment time.
     */
    public void incrementTime() {
        localTime = localTime.plusSeconds(1);
        String time = localTime.format(DATE_TIME_FORMATTER);
        if (Game.isGameStopped() || time.equals(Game.getCurrentGameTime())) {
            Game.setGameTimeLeft(localTime.format(DATE_TIME_FORMATTER));
            Constants.LOGGER.log(Level.FINE, "Timer stopped. Game stopped.");
            timeline.pause();
            Game.setIsNeedToPlace(true);
            figure.setDisable(true);
            endGameLogic();
        }
        timeCurrentGame.setText("Current time: %s".formatted(time));
    }

    private void endGameLogic() {
        Game.array.clear();
        timeline.stop();
        gameEndInfoPane.setVisible(true);
        playAgainBtn.setVisible(true);
        findTheWinner();
        Player.getPlayer().setPlacedBlocks(0);
        Game.setIsGameStopped(true);
        if (Game.getOtherPlayingPerson() != null) {
            Game.getOtherPlayingPerson().setPlacedBlocks(0);
        }
    }

    private void findTheWinner() {
        if (isSinglePlayer) {
            opponentPlacedBlocks.setVisible(false);
            winnerCurrentGame.setVisible(false);
            yourPlacedBlocks.setText("Your blocks: %d".formatted(Game.getPlacedBlocks()));
            saveGame(Player.getPlayer(), Player.getPlayer().getPlaced());
        } else {
            Game.finishMultiplayerGame(Game.getPlacedBlocks());
            winnerCurrentGame.setVisible(true);
            if (Player.getPlayer().getPlaced() > Game.getOtherPlayingPerson().getPlaced()) {
                winnerCurrentGame.setText("Winner: %s".formatted("YOU"));
                saveGame(Player.getPlayer(), Player.getPlayer().getPlaced());
            } else {
                if (Player.getPlayer().getPlaced() < Game.getOtherPlayingPerson().getPlaced()) {
                    winnerCurrentGame.setText("Winner: %s".formatted(Game.getOtherPlayingPerson().getUsername()));
                } else {
                    winnerCurrentGame.setText("Winner: %s".formatted("DRAW"));
                }
            }
            yourPlacedBlocks.setText("Your blocks: %d".formatted(Game.getPlacedBlocks()));
            opponentPlacedBlocks.setText("Opponent blocks: %d".formatted(Game.getOtherPlayingPerson().getPlaced()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Game.createBoard();
        Game.showGameInfo();
        Game.setIsNeedToPlace(false);
        yourNameForGame.setText(MessageFormat.format("You: {0}", Player.getPlayer().getUsername()));
        maxTimeForGame.setText(MessageFormat.format("Max time: {0}", Game.getCurrentGameTime()));
        currentGameMode.setText(Game.getGameMode());
        initializeCurrentGameMode();
        borderGamePane.setCenter(Game.getGamePane());
        initializeTimer();
        timeline.playFromStart();
        exitFromGameBtn.setOnAction(event -> exitGameSession());
        playAgainBtn.setOnAction(event -> playAgainSession());
        playAgainBtn.setVisible(false);
    }

    private void initializeCurrentGameMode() {
        Player.getPlayer().setPlacedBlocks(0);
        if (!currentGameMode.getText().equals("Single-player")) {
            opponentsNameForGame.setVisible(true);
            isSinglePlayer = false;
            Game.getOtherPlayingPerson().setPlacedBlocks(0);
            opponentsNameForGame.setText("Opponent: " + Game.getOtherPlayingPerson().getUsername());
        } else {
            isSinglePlayer = true;
            opponentsNameForGame.setVisible(false);
        }
    }


    private void initializeTimer() {
        localTime = LocalTime.parse("00:00:00");
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ae -> incrementTime()));
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void exitGameSession() {
        Game.setGameTimeLeft(localTime.format(DATE_TIME_FORMATTER));
        if (!Game.isGameStopped()) {
            Constants.LOGGER.log(Level.FINE, "Timer stopped. Game stopped.");
            timeline.pause();
            String temp;
            if (isSinglePlayer) {
                Game.finishSingleGame();
                temp = "Your blocks: %d".formatted(Game.getPlacedBlocks());
                DialogCreator.showCustomDialog(Alert.AlertType.INFORMATION, "Results - Single player", temp, false);
                saveGame(Player.getPlayer(), Player.getPlayer().getPlaced());
            } else {
                Game.finishMultiplayerGame(Game.getPlacedBlocks());
                if (Player.getPlayer().getPlaced() > Game.getOtherPlayingPerson().getPlaced()) {
                    temp = "Winner: %s".formatted("YOU");
                    saveGame(Player.getPlayer(), Player.getPlayer().getPlaced());
                } else {
                    if (Player.getPlayer().getPlaced() < Game.getOtherPlayingPerson().getPlaced()) {
                        temp = "Winner: %s".formatted(Game.getOtherPlayingPerson().getUsername());
                    } else {
                        temp = "Winner: %s".formatted(Player.getPlayer().getPlaced() == Game.getOtherPlayingPerson().getPlaced() ? "DRAW" : "No one won");
                    }
                }
                temp += "\nYour blocks: %d".formatted(Game.getPlacedBlocks());
                temp += "\nOpponent blocks: %d".formatted(Game.getOtherPlayingPerson().getPlaced());
                DialogCreator.showCustomDialog(Alert.AlertType.INFORMATION, "Results - Multi player", temp, false);
            }
        } else {
            if (isSinglePlayer) {
                Game.finishSingleGame();
            }
        }
        Game.setIsPlayingGame(false);
        Constants.LOGGER.log(Level.INFO, "Exit from game session");
        recoverGameStatus();
        Game.leave();
        App.setRoot("main_form");
    }

    private void playAgainSession() {
        recoverGameStatus();
        if (isSinglePlayer) {
            JSONSender jsonSender = JSONSender.getInstance();
            jsonSender.putRequest("function", "single_player");
            ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
        } else {
            Player otherPlayer = Game.getOtherPlayingPerson();
            // Game.leave();
            Game.setCurrentPlayingGame(new Game(otherPlayer));
            Game.getCurrentPlayingGame().sendGameRequest();
        }
    }

    private void recoverGameStatus() {
        Game.array.clear();
        timeline.stop();
        Game.setIsGameStopped(false);
    }
}
