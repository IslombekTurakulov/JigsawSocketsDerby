/*
package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Figure;
import ru.hse.iuturakulov.serverjigsawsockets.network.Client;
import ru.hse.iuturakulov.serverjigsawsockets.network.JSONSender;

import java.util.ArrayList;

import static ru.hse.iuturakulov.serverjigsawsockets.models.Player.isPossibleToPlace;

public class Multiplayer extends GameLogic {
    private final ArrayList<ShapeType> generatedShapes = new ArrayList<ShapeType>();
    public Client opponent;

    public Multiplayer(Client _player, Client _opponent, Boolean record) {
        super(_player);
        opponent = _opponent;
        randomStart();
        notifyGameStart();
    }

    private void notifyGameStart() {
        getOwnerOfGame().sendRequest(JSONRequests.playAccepted(opponent.getUsername(), gameOwner.move, getCurrentTurn()).toString());
        opponent.sendRequest(JSONRequests.playAccepted(gameOwner.getUsername(), opponent.move, getCurrentTurn()).toString());
    }

    public void randomStart() {
        getOwnerOfGame().setCollectionToMove(generatedShapes);
        opponent.setCollectionToMove(generatedShapes);
    }

    public void play(Player player, int x, int y) {
        if (!isPossibleToPlace(player.getNextFigure(), x, y)) {
            ((Client) player).sendRequest(JSONSender.getInstance().play(false, "This Position is not Empty", player.getPlayerName(), 0, ShapeType.NONE).toString());
        } else {
            move(player, index);
            getOwnerOfGame().sendRequest(JSONSender.getInstance().play(true, "", player.getPlayerName(), index, player.move, getCurrentTurn()).toString());
            opponent.sendRequest(JSONSender.getInstance().play(true, "", player.getPlayerName(), index, player.move, getCurrentTurn()).toString());
            checkWin();
        }
    }


    public boolean hasPlayer(String username) {
        return getOwnerOfGame().getPlayerName().equalsIgnoreCase(username) || opponent.getPlayerName().equalsIgnoreCase(username);
    }

    public void finishGame() {
        if (getOwnerOfGame().isPlaying() && !opponent.isPlaying()) {
            getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("win", state).toString());
            opponent.sendRequest(JSONSender.getInstance().gameFinished("lose", "").toString());
        } else if (!getOwnerOfGame().isPlaying() && opponent.isPlaying()) {
            getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("lose", state).toString());
            opponent.sendRequest(JSONSender.getInstance().gameFinished("win", "").toString());
        } else if (!getOwnerOfGame().isPlaying() && !opponent.isPlaying()) {
            getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("draw", "").toString());
            opponent.sendRequest(JSONSender.getInstance().gameFinished("draw", "").toString());
        } else {
            if (getOwnerOfGame().getPlacedBlocks() > opponent.getPlacedBlocks()) {
                getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("win", state).toString());
                opponent.sendRequest(JSONSender.getInstance().gameFinished("lose", "").toString());
            } else {
                getOwnerOfGame().sendRequest(JSONSender.getInstance().gameFinished("lose", state).toString());
                opponent.sendRequest(JSONSender.getInstance().gameFinished("win", "").toString());
            }
        }
        getOwnerOfGame().removeGame();
    }

    public Client getOtherOpponent(String username) {
        if (getOwnerOfGame().getPlayerName().equalsIgnoreCase(username))
            return opponent;
        else if (opponent.getPlayerName().equalsIgnoreCase(username))
            return getOwnerOfGame();
        return null;
    }


    public void sendMessage(Player sender, String message) {
        Client opponent = getOtherOpponent(sender.getUsername());
        if (opponent != null)
            opponent.sendRequest(JSONRequests.sendMessage(message, sender.getUsername()).toString());
    }
}
*/
