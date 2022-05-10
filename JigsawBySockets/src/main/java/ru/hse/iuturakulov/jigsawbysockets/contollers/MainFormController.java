package ru.hse.iuturakulov.jigsawbysockets.contollers;

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

public class MainFormController implements Initializable {
    @FXML
    private Label playerNameField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playerNameField.setText(Player.getPlayer().getPlayerName());
    }

    @FXML
    private void singlePlay(ActionEvent ae) {
        JSONSender jsonSender = JSONSender.getInstance();
        jsonSender.putRequest("function", "single_player");
        jsonSender.putRequest("record", "no");
        ServerSocket.sendRequest(jsonSender.getRequestInstance().toString());
    }

    @FXML
    private void multiPlay(ActionEvent ae) throws IOException {
        //Player.getOnlinePlayers();
        App.setRoot("players_form");
    }

    @FXML
    private void exit(ActionEvent ae) {
        Player.logout();
        System.exit(0);
    }
}
