package ru.hse.iuturakulov.jigsawbysockets.contollers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.models.TimelineCounter;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Figure;

import java.net.URL;
import java.util.ResourceBundle;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.DATE_TIME_FORMATTER;

public class GameFormController implements Initializable {

    public static Figure figure;
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

    public void incrementTime() {
        TimelineCounter.getInstance().incrementTime();
        timeCurrentGame.setText("Time-left: " + TimelineCounter.getInstance().getTime().format(DATE_TIME_FORMATTER));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yourNameForGame.setText(Player.getPlayer().getPlayerName());
        maxTimeForGame.setText(Game.getCurrentPlayingGame().getCurrentGameTime().toString());
        currentGameMode.setText(Game.getGameMode());
        if (!currentGameMode.getText().equals("Single-player")) {
            opponentsNameForGame.setVisible(true);
            opponentsNameForGame.setText(Game.getOtherPlayingPerson().getPlayerName());
        } else {
            opponentsNameForGame.setVisible(false);
        }
        TimelineCounter.getInstance().initializeTimer();
        TimelineCounter.getInstance().setTimeline(new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime())));
        TimelineCounter.getInstance().getTimeline().setCycleCount(Animation.INDEFINITE);
        borderGamePane.setCenter(Game.getGamePane());
        TimelineCounter.getInstance().getTimeline().play();
    }

    public Pane getTilesForGame() {
        return tilesForGame;
    }

    public void setTilesForGame(Pane tilesForGame) {
        this.tilesForGame = tilesForGame;
    }
}
