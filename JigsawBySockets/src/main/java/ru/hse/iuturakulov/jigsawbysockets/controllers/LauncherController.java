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
import java.util.UUID;
import java.util.logging.Level;

import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.MAX_LENGTH_LOGIN;
import static ru.hse.iuturakulov.jigsawbysockets.utils.Constants.MIN_LENGTH_LOGIN;

public class LauncherController implements Initializable {
    @FXML
    private TextField usernameField;

    @FXML
    private void login(ActionEvent actionEvent) {
        Constants.LOGGER.log(Level.INFO, actionEvent.toString());
        if (usernameField.getText().trim().length() < MIN_LENGTH_LOGIN) {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Incorrect name", "The length of your login is less than %d".formatted(MIN_LENGTH_LOGIN), false);
        } else {
            ServerSocket.connect("127.0.0.1", 5000);
            Player.login(usernameField.getText(), UUID.randomUUID().toString());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constants.LOGGER.log(Level.WARNING, "Launch connection...");
        // Limit the text length for auth
        usernameField.textProperty().addListener((ov, oldValue, newValue) -> {
            if (usernameField.getText().length() > MAX_LENGTH_LOGIN) {
                usernameField.setText(usernameField.getText().substring(0, MAX_LENGTH_LOGIN));
            }
        });
    }
}