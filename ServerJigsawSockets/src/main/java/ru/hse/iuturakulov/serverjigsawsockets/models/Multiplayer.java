package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.*;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.JSONSender;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game type - Multiplayer
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see GameLogic
 */
public class Multiplayer extends GameLogic {

    private final ArrayList<FigureType> generatedShapes = new ArrayList<FigureType>();
    /**
     * The Opponent.
     */
    public Client opponent;

    /**
     * Instantiates a new Multiplayer.
     *
     * @param _player   the player
     * @param _opponent the opponent
     */
    public Multiplayer(Client _player, Client _opponent) {
        super(_player);
        opponent = _opponent;
        randomStart();
        notifyGameStart();
    }

    private void notifyGameStart() {
        getOwnerOfGame().sendRequest(JSONSender.playAccepted(opponent.getPlayerName(), opponent.getUuidPlayer(), generatedShapes));
        opponent.sendRequest(JSONSender.playAccepted(getOwnerOfGame().getPlayerName(), getOwnerOfGame().getUuidPlayer(), generatedShapes));
    }

    @Override
    public void randomStart() {
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            generatedShapes.add(new FigureType(
                    BlockOrientation.values()[rand.nextInt(2)],
                    BlockPosition.values()[rand.nextInt(2)],
                    BlockSide.values()[rand.nextInt(2)],
                    BlockType.values()[rand.nextInt(2)],
                    ShapeType.values()[rand.nextInt(7)]
            ));
        }
    }

    public void play(Player player, int index) {
        getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getUuidPlayer(), index).toString());
        opponent.sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getUuidPlayer(), index).toString());
    }


    public boolean hasPlayer(String username) {
        return getOwnerOfGame().getPlayerName().equalsIgnoreCase(username) || opponent.getPlayerName().equalsIgnoreCase(username);
    }

    public void finishGame() {
        // opponent.sendRequest(JSONSender.getInstance().gameFinished("success", "Success").toString());
        opponent.sendRequest(JSONSender.getInstance().gameFinished("success", "Success").toString());
        getOwnerOfGame().removeGame();
    }

    /**
     * Gets other opponent.
     *
     * @param username the username
     * @return the other opponent
     */
    public Client getOtherOpponent(String username) {
        if (getOwnerOfGame().getPlayerName().equalsIgnoreCase(username))
            return opponent;
        else if (opponent.getPlayerName().equalsIgnoreCase(username))
            return getOwnerOfGame();
        return null;
    }
}
