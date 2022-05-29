package ru.hse.iuturakulov.serverside.models;

import ru.hse.iuturakulov.serverside.models.enums.*;
import ru.hse.iuturakulov.serverside.network.Client;
import ru.hse.iuturakulov.serverside.network.JSONSender;
import ru.hse.iuturakulov.serverside.utils.Constants;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Game type - Single-player
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see GameLogic
 */
public class Singleplayer extends GameLogic {

    private final ArrayList<FigureType> generatedShapes = new ArrayList<>();

    /**
     * Instantiates a new Singleplayer.
     *
     * @param player the player
     */
    public Singleplayer(Client player) {
        super(player);
        randomStart();
        getOwnerOfGame().sendRequest(JSONSender.getInstance().singleGameStarted(generatedShapes, Constants.timeCurrent));
        listOfGames.add(this);
    }

    /**
     * Generate extra shape.
     */
    public void generateExtraShape() {
        generatedShapes.clear();
        randomStart();
        Logger.getLogger(Singleplayer.class.getName()).log(Level.INFO, "Generating %d more shapes".formatted(generatedShapes.size()));
        getOwnerOfGame().sendRequest(JSONSender.getInstance().getShapesForGame(generatedShapes));
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
    }

    @Override
    public boolean hasPlayer(String username) {
        return getOwnerOfGame().getPlayerName().equalsIgnoreCase(username);
    }

    @Override
    public boolean hasUuid(String uuid) {
        return getOwnerOfGame().getUuidPlayer().equalsIgnoreCase(uuid);
    }

    @Override
    public void finishGame() {
        getOwnerOfGame().removeGame();
    }

    @Override
    public void play(Player player, int placed) {
        getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getUuidPlayer(), placed).toString());
    }
}
