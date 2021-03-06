package ru.hse.iuturakulov.clientside.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.hse.iuturakulov.clientside.App;
import ru.hse.iuturakulov.clientside.models.Game;
import ru.hse.iuturakulov.clientside.models.Player;
import ru.hse.iuturakulov.clientside.network.ServerHandler;
import ru.hse.iuturakulov.clientside.utils.DialogCreator;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Multiplayer form controller.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ServerHandler
 * @see Game
 */
public class MultiplayerFormController implements Initializable {
    @FXML
    private TableView table;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn usernameCol = new TableColumn("Name");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        if (table == null) {
            table = new TableView();
        }
        table.getColumns().addAll(usernameCol);
        table.getItems().setAll(ServerHandler.playerSet);
    }

    @FXML
    private void back(ActionEvent ae) {
        App.setRoot("main_form");
    }

    @FXML
    private void invite(ActionEvent ae) {
        if (!table.getSelectionModel().isEmpty()) {
            App.setRoot("game_request_form");
            Player player = (Player) table.getSelectionModel().getSelectedItem();
            ServerHandler.otherPlayingPlayer = player.getUsername();
            ServerHandler.otherPlayingPlayerUUID = player.getUuid();
            Game.setCurrentPlayingGame(new Game(player));
            Game.getCurrentPlayingGame().sendGameRequest();
        } else {
            DialogCreator.showCustomDialog(Alert.AlertType.ERROR, "Failed", "You didn't select the player!", false);
        }
    }

    @FXML
    private void refresh(ActionEvent ae) {
        Player.getOnlineList();
        table.getItems().setAll(ServerHandler.playerSet);
        table.refresh();
    }
}
