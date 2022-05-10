package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Figure;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.ShapeFactory;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Timer;

import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.*;

public class Player {

    public static ObservableList<Player> playersList = FXCollections.observableArrayList();
    public static Rectangle[][] board;
    private static IntegerProperty placedBlocks;
    private Queue<ShapeType> move;
    private ShapeType currentShape;
    private StringProperty playerName;
    private BooleanProperty online;
    private Timer timer;

    public Player(String _username) {
        setPlayerName(_username);
        setPlacedBlocks(0);
    }

    public Player(String _username, int _points, boolean isOnline) {
        setPlayerName(_username);
        setPlacedBlocks(_points);
        setOnline(isOnline);
    }

    public static boolean isPossibleToPlace(Figure figure, int x, int y) {
        ArrayList<Integer> listAxisX = new ArrayList<>();
        ArrayList<Integer> listAxisY = new ArrayList<>();
        getApproxLayoutPlace(figure, x, y, listAxisX, listAxisY);
        int i = 0;
        while (i < listAxisX.size()) {
            if (listAxisY.get(i) < 0 || listAxisY.get(i) >= WIDTH_CELL || listAxisX.get(i) < 0 || listAxisX.get(i) >= HEIGHT_CELL || board[listAxisY.get(i)][listAxisX.get(i)].getFill() != Color.WHITE) {
                return false;
            }
            i++;
        }
        i = 0;
        for (; i < listAxisX.size(); i++) {
            board[listAxisY.get(i)][listAxisX.get(i)].setFill(Color.BLACK);
        }
        placedBlocks.set(placedBlocks.intValue() + 1);
        return true;
    }

    private static void getApproxLayoutPlace(Figure figure, int x, int y, ArrayList<Integer> listAxisX, ArrayList<Integer> listAxisY) {
        for (Rectangle rectangle : figure.getRectangleList()) {
            double columns = (x + rectangle.getX() - 2) / (SIZE + 2);
            double rows = (y + rectangle.getY() - 2) / (SIZE + 2);
            int approxX = (int) Math.round(columns);
            int approxY = (int) Math.round(rows);
            if (approxX >= 0 && approxX + 0.5 <= columns) {
                approxX++;
            }
            if (approxY >= 0 && approxY + 0.5 <= rows) {
                approxY++;
            }
            listAxisX.add(approxX);
            listAxisY.add(approxY);
        }
    }

    public BooleanProperty onlineProperty() {
        if (online == null) {
            online = new SimpleBooleanProperty(this, "online");
        }
        return online;
    }

    public Queue<ShapeType> getMove() {
        return move;
    }

    public void setNextShape() {
        currentShape = move.poll();
    }

    public ShapeType getCurrentShape() {
        return currentShape;
    }

    public Figure getNextFigure() {
        ShapeFactory shapeFactory = ShapeFactory.getInstance();
        shapeFactory.setCurrentShape(currentShape);
        return new Figure(shapeFactory.getRandomFigure());
    }

    public void setMove(Queue<ShapeType> move) {
        this.move = move;
    }

    public void setCollectionToMove(ArrayList<ShapeType> list) {
        move.addAll(list);
    }

    public void setOnlineOnArrayList(Boolean status) {
        for (Player player : playersList) {
            if (player.getPlayerName().equalsIgnoreCase(this.getPlayerName())) {
                player.setOnline(status);
                break;
            }
        }
    }

    public Boolean getOnline() {
        return onlineProperty().get();
    }

    public void setOnline(Boolean value) {
        onlineProperty().set(value);
    }

    public String getPlayerName() {
        return this.playerName.get();
    }

    public void setPlayerName(String playerName) {
        if (this.playerName == null) {
            this.playerName = new SimpleStringProperty(this, "username");
        }
        this.playerName.set(playerName);
    }

    public StringProperty playerNameProperty() {
        if (this.playerName == null) {
            this.playerName = new SimpleStringProperty(this, "username");
        }
        return this.playerName;
    }

    public int getPlacedBlocks() {
        return placedBlocks.get();
    }

    public void setPlacedBlocks(int placedBlocks) {
        if (Player.placedBlocks == null) {
            Player.placedBlocks = new SimpleIntegerProperty();
        }
        Player.placedBlocks.set(placedBlocks);
    }

    public void incrementPlacedBlocks(int times) {
        setPlacedBlocks(getPlacedBlocks() + times);
    }

    public IntegerProperty placedBlocksProperty() {
        if (placedBlocks == null) {
            placedBlocks = new SimpleIntegerProperty();
        }
        return placedBlocks;
    }
}
