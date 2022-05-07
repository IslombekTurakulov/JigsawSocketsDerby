package ru.hse.iuturakulov.jigsawbysockets.contollers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import ru.hse.iuturakulov.jigsawbysockets.utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class RegisterFormController implements Initializable {

    @FXML
    private TextField nameOfPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Constants.LOGGER.log(Level.INFO, "Register launched...");
    }
}
