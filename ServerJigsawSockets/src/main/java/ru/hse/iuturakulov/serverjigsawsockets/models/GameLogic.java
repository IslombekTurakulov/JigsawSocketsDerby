package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;

import java.util.ArrayList;

import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.HEIGHT_CELL;
import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.WIDTH_CELL;

public abstract class GameLogic {
    public static ArrayList<GameLogic> listOfGames = new ArrayList<GameLogic>();
    protected final Rectangle[][] board = new Rectangle[WIDTH_CELL][HEIGHT_CELL];
    protected int gameID;
    protected Boolean gameOver;
    protected Shape nextTurn = new Shape();
    private String state;
    private Client ownerOfGame;

    public GameLogic(Client client) {
        ownerOfGame = client;
        randomTurn();
    }

    public abstract void randomStart();

    public abstract boolean hasPlayer(String username);

    public abstract void finishGame();

    public abstract void play(Player player, int x, int y);

    public void move(Player player) {
        if (!gameOver) {
            if (player.getMove().isEmpty()) {
                randomTurn();
            }
        }
    }

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

    private void randomTurn() {
       /* ShapeFactory.getInstance().setRandomShape();
        nextTurn = ShapeFactory.getInstance().getRandomFigure();*/
    }

    public Shape getCurrentTurn() {
        return nextTurn;
    }

    public Rectangle getIndexBoardCells(int i, int j) {
        return board[i][j];
    }

    public void setIndexBoardCell(int i, int j, Rectangle tile) {
        board[i][j] = tile;
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
