package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.DialogCreator;

import java.net.URL;
import java.text.MessageFormat;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.DATE_TIME_FORMATTER;

/**
 * The type Game form controller.
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
    private Timeline timeline;
    private LocalTime localTime;
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
        if (time.equals(Game.getCurrentGameTime())) {
            Constants.LOGGER.log(Level.FINE, "Timer stopped. Game stopped.");
            timeline.pause();
            Game.setIsGameStopped(true);
            gameEndInfoPane.setVisible(true);
            playAgainBtn.setVisible(true);
            findTheWinner();
        }
        timeCurrentGame.setText("Current time: %s".formatted(time));
    }

    private void findTheWinner() {
        if (isSinglePlayer) {
            opponentPlacedBlocks.setVisible(false);
            winnerCurrentGame.setVisible(false);
            yourPlacedBlocks.setText("Your blocks: %d".formatted(Game.getPlacedBlocks()));
        } else {
            Game.finishGame(Game.getPlacedBlocks());
            opponentPlacedBlocks.setVisible(false);
            winnerCurrentGame.setVisible(true);
            winnerCurrentGame.setText("Winner: %s".formatted(Player.getPlayer().getPlaced() > Game.getOtherPlayingPerson().getPlaced() ? "YOU" : Player.getPlayer().getPlaced() < Game.getOtherPlayingPerson().getPlaced() ? Game.getOtherPlayingPerson().getUsername() : "DRAW"));
            yourPlacedBlocks.setText("Your blocks: %d".formatted(Game.getPlacedBlocks()));
            opponentPlacedBlocks.setText("Opponent blocks: %d".formatted(Game.getOtherPlayingPerson().getPlaced()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Game.checkExtraShapes();
        Game.createBoard();
        Game.showGameInfo();
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
        if (!currentGameMode.getText().equals("Single-player")) {
            opponentsNameForGame.setVisible(true);
            isSinglePlayer = false;
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
        if (!Game.isGameStopped()) {
            String temp;
            if (!isSinglePlayer) {
                temp = "Winner: %s".formatted(Player.getPlayer().getPlaced() > Game.getOtherPlayingPerson().getPlaced() ? "YOU" : Player.getPlayer().getPlaced() < Game.getOtherPlayingPerson().getPlaced() ? Game.getOtherPlayingPerson().getUsername() : "DRAW");
                temp += "\nYour blocks: %d".formatted(Game.getPlacedBlocks());
                temp += "\nOpponent blocks: %d".formatted(Game.getOtherPlayingPerson().getPlaced());
                DialogCreator.showCustomDialog(Alert.AlertType.INFORMATION, "Results - Multi player", temp, false);
            } else {
                temp = "Your blocks: %d".formatted(Game.getPlacedBlocks());
                DialogCreator.showCustomDialog(Alert.AlertType.INFORMATION, "Results - Single player", temp, false);
            }
        }
        Constants.LOGGER.log(Level.INFO, "Exit from game session");
        recoverGameStatus();
        Game.leave();
        Game.setCurrentPlayingGame(null);
        Platform.runLater(() -> App.setRoot("main_form")
        );
    }

    private void playAgainSession() {
        recoverGameStatus();
        if (isSinglePlayer) {
            Game.setCurrentPlayingGame(new Game());
            Game.setGameMode("Single-player");
            Platform.runLater(() -> App.setRoot("game_form")
            );
        } else {
            Game.setCurrentPlayingGame(new Game(Game.getOtherPlayingPerson()));
            Game.getCurrentPlayingGame().sendGameRequest();
        }
    }

    private void recoverGameStatus() {
        Game.array.clear();
        timeline.stop();
        Game.setIsGameStopped(false);
    }
}
