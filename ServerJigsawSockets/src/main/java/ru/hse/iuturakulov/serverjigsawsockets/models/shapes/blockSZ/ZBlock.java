package ru.hse.iuturakulov.serverjigsawsockets.models.shapes.blockSZ;

import ru.hse.iuturakulov.serverjigsawsockets.models.CustomPoint;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;
import javafx.scene.paint.Color;

/**
 * The type of block.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 * @see CustomPoint
 */
public class ZBlock extends Shape {

    public ZBlock() {
        super(Color.INDIGO);
    }

    // [ ]
    // [X][ ]
    //    [ ]
    public void createLeftShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
        squares[3] = new CustomPoint(2, 1);
    }

    //    [ ]
    // [X][ ]
    // [ ]
    public void createRightShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(0, 1);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
        squares[3] = new CustomPoint(2, 0);
    }
}