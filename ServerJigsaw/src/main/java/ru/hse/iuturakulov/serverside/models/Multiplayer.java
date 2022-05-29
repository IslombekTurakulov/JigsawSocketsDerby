package ru.hse.iuturakulov.serverside.models;

import ru.hse.iuturakulov.serverside.models.enums.*;
import ru.hse.iuturakulov.serverside.network.Client;
import ru.hse.iuturakulov.serverside.network.JSONSender;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Game type - Multiplayer
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see GameLogic
 */
public class Multiplayer extends GameLogic {

    private static int firstClient;
    private static int secondClient;
    private static int overallSize;
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
        firstClient = 10;
        secondClient = 10;
    }


    public void generateExtraShape(String name, String uuid) {
        if (opponent.getUuidPlayer().equalsIgnoreCase(uuid)) {
            if (firstClient >= overallSize) {
                randomStart();
            } else {
                firstClient += 10;
            }
            Logger.getLogger(Multiplayer.class.getName()).log(Level.INFO, "Generating %d more shapes for %s %s".formatted(overallSize - firstClient + 10, name, uuid));
            opponent.sendRequest(JSONSender.getInstance().getShapesForGame(new ArrayList<>(generatedShapes.stream().skip(firstClient - 10).limit(10).toList())));
        } else {
            if (secondClient >= overallSize) {
                randomStart();
            } else {
                secondClient += 10;
            }
            Logger.getLogger(Multiplayer.class.getName()).log(Level.INFO, "Generating %d more shapes for %s %s".formatted(overallSize - secondClient + 10, name, uuid));
            getOwnerOfGame().sendRequest(JSONSender.getInstance().getShapesForGame(new ArrayList<>(generatedShapes.stream().skip(secondClient - 10).limit(10).toList())));
        }
    }

    private void notifyGameStart() {
        getOwnerOfGame().sendRequest(JSONSender.playAccepted(opponent.getPlayerName(), opponent.getUuidPlayer(), generatedShapes));
        opponent.sendRequest(JSONSender.playAccepted(getOwnerOfGame().getPlayerName(), getOwnerOfGame().getUuidPlayer(), generatedShapes));
    }

    @Override
    public void randomStart() {
        Random rand = new Random();
        for (int i = 0; i < 20; i++) {
            generatedShapes.add(new FigureType(
                    BlockOrientation.values()[rand.nextInt(2)],
                    BlockPosition.values()[rand.nextInt(2)],
                    BlockSide.values()[rand.nextInt(2)],
                    BlockType.values()[rand.nextInt(2)],
                    ShapeType.values()[rand.nextInt(7)]
            ));
        }
        overallSize = generatedShapes.size();
    }

    public void play(Player player, int index) {
        getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getUuidPlayer(), index).toString());
        opponent.sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getUuidPlayer(), index).toString());
    }


    public boolean hasPlayer(String username) {
        return getOwnerOfGame().getPlayerName().equalsIgnoreCase(username) || opponent.getPlayerName().equalsIgnoreCase(username);
    }

    @Override
    public boolean hasUuid(String uuid) {
        return getOwnerOfGame().getUuidPlayer().equals(uuid) || opponent.getUuidPlayer().equals(uuid);
    }

    public void finishGame() {
        getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("success", "Success").toString());
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
