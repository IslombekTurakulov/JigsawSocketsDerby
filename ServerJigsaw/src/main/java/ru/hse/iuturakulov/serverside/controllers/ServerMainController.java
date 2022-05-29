package ru.hse.iuturakulov.serverside.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.hse.iuturakulov.serverside.ServerMain;
import ru.hse.iuturakulov.serverside.models.Player;
import ru.hse.iuturakulov.serverside.network.Server;
import ru.hse.iuturakulov.serverside.utils.Constants;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * Server controller
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see Server
 */
public class ServerMainController implements Initializable {

    @FXML
    private Button exitBtn;
    @FXML
    private Button timerServerBtn;
    @FXML
    private TextField portField;
    @FXML
    private Button startServerBtn;
    @FXML
    private Button stopServerBtn;
    @FXML
    private Label statusText;
    @FXML
    private Text gameTime;
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
        stopServerBtn.setVisible(false);
        TableColumn<Player, String> uuidCol = new TableColumn<>("UUID");
        uuidCol.setCellValueFactory(cellData -> cellData.getValue().getUuidPlayerProperty());
        TableColumn<Player, String> usernameCol = new TableColumn<>("Player");
        usernameCol.setCellValueFactory(cellData -> cellData.getValue().playerNameProperty());
        gameTime.setText(Constants.timeCurrent == null || Constants.timeCurrent.contentEquals("00:00:00") ? "Please choose game time" : "Game time: " + Constants.timeCurrent);
        TableColumn<Player, Boolean> onlineCol = new TableColumn<>("Status");
        onlineCol.setCellValueFactory(cellData -> cellData.getValue().onlineProperty());
        playersTable.getColumns().addAll(uuidCol, usernameCol, onlineCol);
        timerServerBtn.setOnAction(this::timerServer);
    }

    /**
     * Load table data.
     */
    public void loadTableData() {
        try {
            playersTable.setItems(Player.playersList);
        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Connection Failed");
            alert.setResizable(true);
            alert.setContentText("Connection Failed");
            alert.showAndWait();
            System.exit(0);
        }
    }

    /**
     * Start server.
     *
     * @param ae the ae
     */
    public void startServer(ActionEvent ae) {
        // Port validation
        if (portField.getText().isEmpty() || !Pattern.matches(
                "^((6553[0-5])|(655[0-2][0-9])|(65[0-4][0-9]{2})|(6[0-4][0-9]{3})|([1-5][0-9]{4})|([0-5]{0,5})|([0-9]{1,4}))$",
                portField.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Server Failed to start");
            alert.setResizable(true);
            alert.setContentText("The port number is not valid");
            alert.showAndWait();
            return;
        }
        // Game time validation
        if (Constants.timeCurrent == null || Constants.timeCurrent.contentEquals("00:00:00")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setHeaderText("Incorrect time");
            alert.setResizable(true);
            alert.setContentText("Please, choose time for game");
            alert.showAndWait();
            return;
        }
        int port = Integer.parseInt(portField.getText());
        Server.createServer(port);
        stopServerBtn.setVisible(true);
        startServerBtn.setVisible(false);
        timerServerBtn.setVisible(false);
        statusText.setTextFill(Color.GREEN);
        statusText.setText("Online");
        portField.setDisable(true);
        loadTableData();
    }

    private void setServerStopped() {
        Server.stop();
        Server.closeSocket();
        stopServerBtn.setVisible(false);
        startServerBtn.setVisible(true);
        timerServerBtn.setVisible(true);
        statusText.setTextFill(Color.RED);
        statusText.setText("Offline");
        portField.setDisable(false);
    }

    /**
     * Stop server.
     *
     * @param ae the ae
     */
    public void stopServer(ActionEvent ae) {
        setServerStopped();
    }

    /**
     * Exit.
     *
     * @param ae the ae
     */
    public void exit(ActionEvent ae) {
        setServerStopped();
        System.exit(0);
    }

    /**
     * Timer server.
     *
     * @param actionEvent the action event
     */
    public void timerServer(ActionEvent actionEvent) {
        ServerMain.setRoot("timer_form");
    }
}