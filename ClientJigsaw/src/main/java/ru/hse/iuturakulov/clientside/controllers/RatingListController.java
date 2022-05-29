package ru.hse.iuturakulov.clientside.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import ru.hse.iuturakulov.clientside.App;
import ru.hse.iuturakulov.clientside.models.RatingPlayers;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Rating list controller.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see RatingPlayers
 */
public class RatingListController implements Initializable {


    @FXML
    private TableView ratingPlayersTable;


    /*
        По завершению игры результат игры записывается хостом (т.е. сервером) в свою
        локальную базу данных (Derby), в таблицу, состоящую из следующих столбцов:
        - уникальный id записи (можно использовать UUID), выступающий в качестве primary key;
        - login игрока (имя игрока, которое игрок ввел при запуске программы);
        - дата и время окончания игры (хранение делать в тайм зоне UTC+0);
        - количество выполненных ходов;
        - время потраченное на игру
        Done.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        TableColumn gameIdCol = new TableColumn("GAME ID");
        TableColumn loginPlayerCol = new TableColumn("PLAYER");
        TableColumn endDateTimeCol = new TableColumn("GAME END DATETIME");
        TableColumn placedBlocksCol = new TableColumn("PLACED BLOCKS");
        TableColumn timeLeftCol = new TableColumn("GAME TIME LEFT");
        gameIdCol.setCellValueFactory(new PropertyValueFactory<>("gameID"));
        loginPlayerCol.setCellValueFactory(new PropertyValueFactory<>("loginPlayerGame"));
        endDateTimeCol.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        placedBlocksCol.setCellValueFactory(new PropertyValueFactory<>("playerPlacedBlocks"));
        timeLeftCol.setCellValueFactory(new PropertyValueFactory<>("gameTime"));
        if (ratingPlayersTable == null) {
            ratingPlayersTable = new TableView();
        }
        ratingPlayersTable.getColumns().addAll(gameIdCol, loginPlayerCol, endDateTimeCol, placedBlocksCol, timeLeftCol);
        ratingPlayersTable.getItems().setAll(RatingPlayers.ratingPlayers);
    }

    /**
     * Back.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void back(ActionEvent actionEvent) {
        App.setRoot("main_form");
    }

    /**
     * Refresh.
     *
     * @param actionEvent the action event
     */
    @FXML
    public void refresh(ActionEvent actionEvent) {
        RatingPlayers.getRatingList();
        ratingPlayersTable.getItems().setAll(RatingPlayers.ratingPlayers);
        ratingPlayersTable.refresh();
    }
}
