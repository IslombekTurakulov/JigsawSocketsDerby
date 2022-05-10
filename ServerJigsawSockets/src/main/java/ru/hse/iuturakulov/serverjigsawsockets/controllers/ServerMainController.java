package ru.hse.iuturakulov.serverjigsawsockets.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import ru.hse.iuturakulov.serverjigsawsockets.models.Player;
import ru.hse.iuturakulov.serverjigsawsockets.network.Server;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ServerMainController implements Initializable {

    @FXML
    private TextField portField;
    @FXML
    private Button startServerBtn;
    @FXML
    private Button stopServerBtn;
    @FXML
    private Label statusText;
    @FXML
    private TableView<Player> playersTable;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<Player, String> usernameCol = new TableColumn<>("Player");
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());

        TableColumn<Player, Boolean> onlineCol = new TableColumn<>("Status");
        onlineCol.setCellValueFactory(cellData -> cellData.getValue().onlineProperty());
        playersTable.getColumns().addAll(usernameCol, onlineCol);
    }

    public void loadTableData() {
        try {
            playersTable.setItems(Player.playersList);
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Failed");
            a.setHeaderText("Connection Failed");
            a.setResizable(true);
            a.setContentText("Connection Failed");
            a.showAndWait();
            System.exit(0);
        }
    }

    public void startServer(ActionEvent ae) {
        if (portField.getText().isEmpty() || !Pattern.matches(
                "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$",
                portField.getText())) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Failed");
            a.setHeaderText("Server Failed to start");
            a.setResizable(true);
            a.setContentText("The port number is not valid");
            a.showAndWait();
            return;
        }
        int port = Integer.parseInt(portField.getText());
        Server.createServer(port);
        stopServerBtn.setVisible(true);
        startServerBtn.setVisible(false);
        statusText.setTextFill(Color.GREEN);
        statusText.setText("Online");
        portField.setDisable(true);
        loadTableData();
    }

    private void setServerStopped() {
        Server.stop();
        stopServerBtn.setVisible(false);
        startServerBtn.setVisible(true);
        statusText.setTextFill(Color.RED);
        statusText.setText("Offline");
        portField.setDisable(false);
    }

    public void stopServer(ActionEvent ae) {
        setServerStopped();
    }

    public void exit(ActionEvent ae) {
        setServerStopped();
        System.exit(0);
    }
}