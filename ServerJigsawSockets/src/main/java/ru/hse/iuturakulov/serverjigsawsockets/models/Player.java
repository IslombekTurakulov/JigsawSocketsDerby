package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Player model
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see GameLogic
 */
public class Player {

    /**
     * The Players list.
     */
    public static ObservableList<Player> playersList = FXCollections.observableArrayList();
    private static IntegerProperty placedBlocks;
    private StringProperty playerName;
    private StringProperty uuidPlayer;
    private BooleanProperty online;

    /**
     * Instantiates a new Player.
     *
     * @param name the username
     */
    public Player(String name, String uuid) {
        setPlayerName(name);
        setUuidPlayer(uuid);
        setPlacedBlocks(0);
    }

    /**
     * Instantiates a new Player.
     *
     * @param name         the username
     * @param placedBlocks the placed blocks
     * @param isOnline     the is online
     */
    public Player(String name, String uuid, int placedBlocks, boolean isOnline) {
        setPlayerName(name);
        setUuidPlayer(uuid);
        setPlacedBlocks(placedBlocks);
        setOnline(isOnline);
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getUuidPlayer() {
        return this.uuidPlayer.get();
    }

    public void setUuidPlayer(String uuid) {
        if (this.uuidPlayer == null) {
            this.uuidPlayer = new SimpleStringProperty(this, "uuidPlayer");
        }
        this.uuidPlayer.set(uuid);
    }

    public StringProperty getUuidPlayerProperty() {
        if (this.uuidPlayer == null) {
            this.uuidPlayer = new SimpleStringProperty(this, "uuidPlayer");
        }
        return this.uuidPlayer;
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
     * Sets online on array list.
     *
     * @param status the status
     */
    public void setOnlineOnArrayList(Boolean status) {
        for (Player player : playersList) {
            if (player.getUuidPlayer().equalsIgnoreCase(this.getUuidPlayer())) {
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
}
