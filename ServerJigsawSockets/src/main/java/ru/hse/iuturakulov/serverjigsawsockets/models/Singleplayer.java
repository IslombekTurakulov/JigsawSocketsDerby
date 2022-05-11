package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.*;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.FigureType;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.JSONSender;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Singleplayer extends GameLogic {

    private final ArrayList<FigureType> generatedShapes = new ArrayList<>();

    public Singleplayer(Client player) {
        super(player);
        randomStart();
        getOwnerOfGame().sendRequest(JSONSender.getInstance().singleGameStarted(generatedShapes).toString());
        listOfGames.add(this);
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
        getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("win", "").toString());
        getOwnerOfGame().removeGame();
    }

    @Override
    public void play(Player player, int x, int y) {
        move(player);
        getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "", player.getPlayerName(), player.getCurrentShape()).toString());
        checkWin(getOwnerOfGame(), true);
    }
}
