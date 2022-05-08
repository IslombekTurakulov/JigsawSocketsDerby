package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Timer;

public class Player {

    private StringProperty playerName;
    private IntegerProperty points;
    public GameMove move = GameMove.NONE;
    public static ObservableList<Player> playersList = FXCollections.observableArrayList();
    private Timer timer;



    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        if (playerName == null) {
            playerName = new SimpleStringProperty(this, "username");
        }
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public int getPoints() {
        return points.get();
    }

    public IntegerProperty pointsProperty() {
        if (points == null) {
            points = new SimpleIntegerProperty();
        }
        return points;
    }

    public void setPoints(int points) {
        this.points.set(points);
    }
}
