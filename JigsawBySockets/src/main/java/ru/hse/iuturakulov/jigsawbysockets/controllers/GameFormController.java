package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.models.TimelineCounter;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

import java.net.URL;
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
        TimelineCounter.getInstance().incrementTime();
        String time = TimelineCounter.getInstance().getTime().format(DATE_TIME_FORMATTER);
        if (time.equals(Game.getCurrentGameTime())) {
            TimelineCounter.getInstance().getTimeline().stop();
            figure.setDisable(true);
            figure.setLayoutX(figure.getInitialX());
            figure.setLayoutY(figure.getInitialY());
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
        Game.createBoard();
        yourNameForGame.setText("You: " + Player.getPlayer().getUsername());
        maxTimeForGame.setText("Max time: " + Game.getCurrentGameTime());
        currentGameMode.setText(Game.getGameMode());
        initializeCurrentGameMode();
        borderGamePane.setCenter(Game.getGamePane());
        initializeTimer();
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
        TimelineCounter.getInstance().initializeTimer();
        TimelineCounter.getInstance().setTimeline(new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime())));
        TimelineCounter.getInstance().getTimeline().setCycleCount(Animation.INDEFINITE);
        TimelineCounter.getInstance().getTimeline().play();
    }

    private void exitGameSession() {
        Constants.LOGGER.log(Level.INFO, "Exit from game session");
        Game.leave();
        App.setRoot("main_form");
        Game.setCurrentIndexShape(0);
    }

    private void playAgainSession() {
        if (isSinglePlayer) {
            Game.setCurrentPlayingGame(new Game());
            Game.setCurrentGameTime(maxTimeForGame.getText());
            Game.setGameMode("Single-player");
            Platform.runLater(() ->
                    App.setRoot("game_form")
            );
        } else {
            Game.setCurrentPlayingGame(new Game(Game.getOtherPlayingPerson()));
            Game.getCurrentPlayingGame().sendGameRequest();
        }
    }
}