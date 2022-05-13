package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;

import java.util.ArrayList;

public abstract class GameLogic {
    public static ArrayList<GameLogic> listOfGames = new ArrayList<GameLogic>();
    protected int gameID;
    protected Boolean gameOver;
    private String state;
    private Client ownerOfGame;

    public GameLogic(Client client) {
        ownerOfGame = client;
    }

    public abstract void randomStart();

    public abstract boolean hasPlayer(String username);

    public abstract void finishGame();

    public abstract void play(Player player, int placed);

    public void checkWin(Player player, boolean isSingleplayer) {
        if (isSingleplayer)
        if (gameOver) {
            return;
        }
        if (!player.getOnline()) {
            gameOver = true;
        } else {

        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Client getOwnerOfGame() {
        return ownerOfGame;
    }

    public void setOwnerOfGame(Client ownerOfGame) {
        this.ownerOfGame = ownerOfGame;
    }
}
