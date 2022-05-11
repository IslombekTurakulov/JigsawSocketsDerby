package ru.hse.iuturakulov.jigsawbysockets.contollers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
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

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.*;

public class GameFormController implements Initializable {

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
    private TimelineCounter timelineCounter;
    public static Figure figure;

    public void incrementTime() {
        timelineCounter.setTime(timelineCounter.getTime().plusSeconds(1));
        timeCurrentGame.setText("Time-left: %s".formatted(timelineCounter.getTime().format(DATE_TIME_FORMATTER)));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        yourNameForGame.setText(Player.getPlayer().getPlayerName());
        opponentsNameForGame.setText(Game.getCurrentPlayingGame().getOtherPlayingPerson().getPlayerName());
        maxTimeForGame.setText(Game.getCurrentPlayingGame().getCurrentGameTime().toString());
        timelineCounter = new TimelineCounter();
        timelineCounter.setTimeline(new Timeline(new KeyFrame(Duration.millis(1000), ae -> incrementTime())));
        borderGamePane.setCenter(Game.getGamePane());
        figure = new Figure(Game.getGamePane().getPrefWidth() * 1.2 / 3, HEIGHT_CELL * (SIZE + 2) + 20);
    }

    public Pane getTilesForGame() {
        return tilesForGame;
    }

    public void setTilesForGame(Pane tilesForGame) {
        this.tilesForGame = tilesForGame;
    }
}
