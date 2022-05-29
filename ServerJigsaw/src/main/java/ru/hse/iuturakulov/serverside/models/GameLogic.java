package ru.hse.iuturakulov.serverside.models;

import ru.hse.iuturakulov.serverside.network.Client;

import java.util.ArrayList;

/**
 * The type main Game logic.
 *
 * @author Islombek Turakulov
 * @version 1.0
 */
public abstract class GameLogic {
    /**
     * List of games.
     */
    public static ArrayList<GameLogic> listOfGames = new ArrayList<GameLogic>();
    private final Client ownerOfGame;
    /**
     * The Game id.
     */
    protected int gameID;

    /**
     * Instantiates a new Game logic.
     *
     * @param client the client
     */
    public GameLogic(Client client) {
        ownerOfGame = client;
    }

    /**
     * Random start.
     */
    public abstract void randomStart();

    /**
     * Has player boolean.
     *
     * @param username the username
     * @return the boolean
     */
    public abstract boolean hasPlayer(String username);

    public abstract boolean hasUuid(String uuid);

    /**
     * Finish game.
     */
    public abstract void finishGame();

    /**
     * Play.
     *
     * @param player the player
     * @param placed the placed
     */
    public abstract void play(Player player, int placed);

    /**
     * Gets owner of game.
     *
     * @return the owner of game
     */
    public Client getOwnerOfGame() {
        return ownerOfGame;
    }
}
