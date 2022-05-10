package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.ShapeFactory;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.JSONSender;

import java.util.ArrayList;
import java.util.Random;

import static ru.hse.iuturakulov.serverjigsawsockets.models.Player.isPossibleToPlace;

public class Singleplayer extends GameLogic {

    private final ArrayList<ShapeType> generatedShapes = new ArrayList<ShapeType>();

    public Singleplayer(Client player) {
        super(player);
        randomStart();
        getOwnerOfGame().sendRequest(JSONSender.getInstance().singleGameStarted(generatedShapes).toString());
        listOfGames.add(this);
    }

    @Override
    public void randomStart() {
        Random rand = new Random();
        ShapeFactory.getInstance().setRandomShape();
        for (int i = 0; i < 50; i++) {
            generatedShapes.add(ShapeType.values()[rand.nextInt(7)]);
        }
        getOwnerOfGame().setCollectionToMove(generatedShapes);
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
        if (!isPossibleToPlace(player.getNextFigure(), x, y)) {
            ((Client) player).sendRequest(JSONSender.getInstance().play(false, "This Position is not Empty", player.getPlayerName(), ShapeType.NONE).toString());
        } else {
            move(player);
            getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "", player.getPlayerName(), player.getCurrentShape()).toString());
            checkWin(getOwnerOfGame(), true);
        }
    }
}
