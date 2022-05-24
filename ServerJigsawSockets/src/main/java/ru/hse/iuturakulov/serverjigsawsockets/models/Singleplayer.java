package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.*;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.JSONSender;
import ru.hse.iuturakulov.serverjigsawsockets.utils.Constants;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The type Singleplayer.
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
        Logger.getLogger(Singleplayer.class.getName()).log(Level.INFO, "Generating 10 more shapes");
        generatedShapes.clear();
        randomStart();
        getOwnerOfGame().sendRequest(JSONSender.getInstance().getShapesForGame(generatedShapes));
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

    @Override
    public boolean hasPlayer(String username) {
        return getOwnerOfGame().getPlayerName().equalsIgnoreCase(username);
    }

    @Override
    public void finishGame() {
        getOwnerOfGame().removeGame();
    }

    @Override
    public void play(Player player, int placed) {
        getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "Placed blocks", player.getPlayerName(), player.getuuidPlayer(), placed).toString());
    }
}
