package ru.hse.iuturakulov.jigsawbysockets.models.shapes;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.serverjigsawsockets.models.enums.ShapeType;

import java.util.ArrayList;

import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.SIZE;

public class Figure extends Pane {

    private final ArrayList<Rectangle> rectangleList = new ArrayList<>();
    private final ShapeType type;
    private final int sizeOfFigure;

    public Figure(Shape shape) {
        type = shape.getShapeType();
        for (int i = 0; i < shape.getSquares().length; i++) {
            int y = shape.getSquares()[i].getY();
            int x = shape.getSquares()[i].getX();
            Tiles tileModel = new Tiles(y, x, new Rectangle(SIZE, SIZE));
            rectangleList.add(tileModel.tileCreation(shape.getColor()));
        }
        sizeOfFigure = rectangleList.size();
    }

    public int getSizeOfFigure() {
        return sizeOfFigure;
    }

    /**
     * Gets rectangle list.
     *
     * @return the rectangle list
     */
    public ArrayList<Rectangle> getRectangleList() {
        return rectangleList;
    }

    public ShapeType getType() {
        return type;
    }
}
