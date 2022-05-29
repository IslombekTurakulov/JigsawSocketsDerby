package ru.hse.iuturakulov.clientside.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ru.hse.iuturakulov.clientside.App;
import ru.hse.iuturakulov.clientside.models.Game;
import ru.hse.iuturakulov.clientside.network.ServerHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Game request form controller.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see GameRequestAcceptFormController
 * @see ServerHandler
 */
public class GameRequestFormController implements Initializable {
    @FXML
    private Label opponentLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opponentLabel.setText(ServerHandler.otherPlayingPlayer);
    }

    @FXML
    private void cancel(ActionEvent ae) {
        Game.rejectGameInvite();
        if (Game.getCurrentPlayingGame() != null) {
            Game.setCurrentPlayingGame(null);
        }
        App.setRoot("main_form");
    }
}
