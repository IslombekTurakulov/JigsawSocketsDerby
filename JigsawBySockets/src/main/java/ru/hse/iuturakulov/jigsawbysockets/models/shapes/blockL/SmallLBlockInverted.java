package ru.hse.iuturakulov.jigsawbysockets.models.shapes.blockL;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.jigsawbysockets.models.CustomPoint;
import ru.hse.iuturakulov.jigsawbysockets.models.shapes.Shape;

/**
 * The type of block.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 * @see CustomPoint
 */
public class SmallLBlockInverted extends Shape {
    public SmallLBlockInverted() {
        super(Color.BURLYWOOD);
    }

    //    [ ]
    // [ ][X]
    public void createLeftShape() {
        squares = new CustomPoint[3];
        squares[0] = new CustomPoint(0, 1);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
    }

    // [ ]
    // [X][ ]
    public void createRightShape() {
        squares = new CustomPoint[3];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
    }
}
