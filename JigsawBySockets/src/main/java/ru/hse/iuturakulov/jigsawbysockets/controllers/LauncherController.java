package ru.hse.iuturakulov.jigsawbysockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.network.ServerSocket;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;
import ru.hse.iuturakulov.jigsawbysockets.utils.DialogCreator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LauncherController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private void login(ActionEvent actionEvent) {
        Constants.LOGGER.log(Level.INFO, actionEvent.toString());
        if (usernameField.getText().trim().length() < 4) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Incorrect name", "Please type the correct name", false);
        } else {
            Player.login(usernameField.getText());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: server connection
        Constants.LOGGER.log(Level.WARNING, "Launch connection...");
        ServerSocket.connect("127.0.0.1", 5000);
    }
}