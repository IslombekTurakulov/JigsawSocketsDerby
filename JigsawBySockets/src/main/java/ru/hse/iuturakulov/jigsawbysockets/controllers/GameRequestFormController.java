package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Game request form controller.
 */
public class GameRequestFormController implements Initializable {
    @FXML
    private Label opponentLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opponentLabel.setText(ServerHandler.otherPlayingPlayer);
    }

    @FXML
    private void cancel(ActionEvent ae) throws IOException {
        Game.rejectGameInvite();
        if (Game.getCurrentPlayingGame() != null) {
            Game.setCurrentPlayingGame(null);
        }
        App.setRoot("main_form");
    }
}
