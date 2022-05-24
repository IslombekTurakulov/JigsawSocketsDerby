package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.hse.iuturakulov.serverjigsawsockets.network.DatabaseDerbyAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

/**
 * The type Player.
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
     * @param _username the username
     */
    public Player(String _username, String uuid) {
        setPlayerName(_username);
        setUuidPlayer(uuid);
        setPlacedBlocks(0);
    }

    /**
     * Instantiates a new Player.
     *
     * @param _username the username
     * @param _points   the points
     * @param isOnline  the is online
     */
    public Player(String _username, String uuid, int _points, boolean isOnline) {
        setPlayerName(_username);
        setUuidPlayer(uuid);
        setPlacedBlocks(_points);
        setOnline(isOnline);
    }

    public static void register(String username, UUID uuid) {
        DatabaseDerbyAccess da = new DatabaseDerbyAccess();
        try (PreparedStatement st = da.getConnection().prepareStatement("INSERT INTO players(username,uuid) VALUES(?,?)")) {
            st.setString(1, username);
            st.setString(2, String.valueOf(uuid));
            st.executeUpdate();
            Player.getAll();
            da.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized ObservableList<Player> getAll() throws Exception {
        playersList.clear();
        String query = "SELECT * FROM players";
        DatabaseDerbyAccess da = new DatabaseDerbyAccess();
        Statement st = da.getConnection().createStatement();
        ResultSet result = st.executeQuery(query);
        while (result.next()) {
            Player player = new Player(result.getString("username"), result.getString("uuid"));
            playersList.add(player);
        }
        da.close();
        return playersList;
    }

    /**
     * Gets player name.
     *
     * @return the player name
     */
    public String getuuidPlayer() {
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
            if (player.getuuidPlayer().equalsIgnoreCase(this.getuuidPlayer())) {
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
