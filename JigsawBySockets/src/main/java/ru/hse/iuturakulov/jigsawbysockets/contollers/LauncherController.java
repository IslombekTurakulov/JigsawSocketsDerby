package ru.hse.iuturakulov.jigsawbysockets.contollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.hse.iuturakulov.jigsawbysockets.models.Player;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LauncherController implements Initializable {
    @FXML
    private TextField loginPlayer;

    @FXML
    private PasswordField passwordPlayer;

    @FXML
    private void login(ActionEvent actionEvent) {
        Constants.LOGGER.log(Level.INFO, actionEvent.toString());
        Player.login(loginPlayer.getText(), passwordPlayer.getText());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: server connection
    }
}