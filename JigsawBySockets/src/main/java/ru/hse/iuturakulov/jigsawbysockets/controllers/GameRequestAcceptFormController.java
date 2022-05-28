package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Game;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerHandler;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Game request accept form controller.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ServerHandler
 */
public class GameRequestAcceptFormController implements Initializable {

    public Button rejectBtn;
    public Button acceptBtn;
    @FXML
    private Label playerLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerLabel.setText(ServerHandler.otherPlayingPlayer);
        rejectBtn.setOnAction(this::reject);
        acceptBtn.setOnAction(this::accept);
    }

    /**
     * Accept.
     *
     * @param ae the ae
     */
    @FXML
    public void accept(ActionEvent ae) {
        Game.acceptGameInvite();
    }

    /**
     * Reject.
     *
     * @param ae the ae
     */
    @FXML
    public void reject(ActionEvent ae) {
        Game.rejectGameInvite();
        App.setRoot("main_form");
    }
}
