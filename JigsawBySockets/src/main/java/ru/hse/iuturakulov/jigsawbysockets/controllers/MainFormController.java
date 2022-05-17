package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import ru.hse.iuturakulov.jigsawbysockets.App;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.JSONSender;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Main form controller.
 */
public class MainFormController implements Initializable {
    @FXML
    private Label playerNameField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerNameField.setText(Player.getPlayer().getUsername());
    }

    @FXML
    private void singlePlay(ActionEvent ae) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "single_player");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    @FXML
    private void multiplayer(ActionEvent ae) {
        Player.getOnlineList();
        App.setRoot("games_form");
    }

    @FXML
    private void exit(ActionEvent ae) {
        Player.logout();
        System.exit(0);
    }
}
