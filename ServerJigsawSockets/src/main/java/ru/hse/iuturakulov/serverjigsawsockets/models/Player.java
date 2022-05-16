package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Timer;

import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.*;

/**
 * The type Player.
 */
public class Player {

    /**
     * The Players list.
     */
    public static ObservableList<Player> playersList = FXCollections.observableArrayList();
    /**
     * The Board.
     */
    public static Rectangle[][] board;
    private static IntegerProperty placedBlocks;
    private ArrayList<FigureType> move;
    private FigureType currentShape;
    private StringProperty playerName;
    private BooleanProperty online;
    private Timer timer;

    /**
     * Instantiates a new Player.
     *
     * @param _username the username
     */
    public Player(String _username) {
        setPlayerName(_username);
        setPlacedBlocks(0);
    }

    /**
     * Instantiates a new Player.
     *
     * @param _username the username
     * @param _points   the points
     * @param isOnline  the is online
     */
    public Player(String _username, int _points, boolean isOnline) {
        setPlayerName(_username);
        setPlacedBlocks(_points);
        setOnline(isOnline);
    }

    /**
     * Online property boolean property.
     *
     * @return the boolean property
     */
    public BooleanProperty onlineProperty() {
        if (online == null) {
            online = new SimpleBooleanProperty(this, "online");
        }
        return online;
    }

    /**
     * Gets move.
     *
     * @return the move
     */
    public ArrayList<FigureType> getMove() {
        return move;
    }

    /**
     * Sets next shape.
     */
    public void setNextShape() {
        currentShape = move.get(0);
    }

    /**
     * Gets current shape.
     *
     * @return the current shape
     */
    public FigureType getCurrentShape() {
        return currentShape;
    }

    /**
     * Sets move.
     *
     * @param move the move
     */
    public void setMove(ArrayList<FigureType> move) {
        this.move = move;
    }

    /**
     * Sets collection to move.
     *
     * @param list the list
     */
    public void setCollectionToMove(ArrayList<FigureType> list) {
        if (move == null) {
            move = new ArrayList<FigureType>();
        }
        move.addAll(list);
    }

    /**
     * Sets online on array list.
     *
     * @param status the status
     */
    public void setOnlineOnArrayList(Boolean status) {
        for (Player player : playersList) {
            if (player.getPlayerName().equalsIgnoreCase(this.getPlayerName())) {
                player.setOnline(status);
                break;
            }
        }
    }

    /**
     * Gets online.
     *
     * @return the online
     */
    public Boolean getOnline() {
        return onlineProperty().get();
    }

    /**
     * Sets online.
     *
     * @param value the value
     */
    public void setOnline(Boolean value) {
        onlineProperty().set(value);
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getPlayerName() {
        return this.playerName.get();
    }

    /**
     * Sets player name.
     *
     * @param playerName the player name
     */
    public void setPlayerName(String playerName) {
        if (this.playerName == null) {
            this.playerName = new SimpleStringProperty(this, "username");
        }
        this.playerName.set(playerName);
    }

    /**
     * Player name property string property.
     *
     * @return the string property
     */
    public StringProperty playerNameProperty() {
        if (this.playerName == null) {
            this.playerName = new SimpleStringProperty(this, "username");
        }
        return this.playerName;
    }

    /**
     * Gets placed blocks.
     *
     * @return the placed blocks
     */
    public int getPlacedBlocks() {
        return placedBlocks.get();
    }

    /**
     * Sets placed blocks.
     *
     * @param placedBlocks the placed blocks
     */
    public void setPlacedBlocks(int placedBlocks) {
        if (Player.placedBlocks == null) {
            Player.placedBlocks = new SimpleIntegerProperty();
        }
        Player.placedBlocks.set(placedBlocks);
    }

    /**
     * Increment placed blocks.
     *
     * @param times the times
     */
    public void incrementPlacedBlocks(int times) {
        setPlacedBlocks(getPlacedBlocks() + times);
    }

    /**
     * Placed blocks property integer property.
     *
     * @return the integer property
     */
    public IntegerProperty placedBlocksProperty() {
        if (placedBlocks == null) {
            placedBlocks = new SimpleIntegerProperty();
        }
        return placedBlocks;
    }
}
