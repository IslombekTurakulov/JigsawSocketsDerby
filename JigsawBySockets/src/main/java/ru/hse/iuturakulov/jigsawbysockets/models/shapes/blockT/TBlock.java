package ru.hse.iuturakulov.jigsawbysockets.models.shapes.blockT;

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
public class TBlock extends Shape {

    public TBlock() {
        super(Color.ORCHID);
    }

    //    [ ]
    //    [ ]
    // [ ][X][ ]
    public void createBigLeftShape() {
        squares = new CustomPoint[5];
        squares[0] = new CustomPoint(0, 1);
        squares[1] = new CustomPoint(1, 1);
        squares[2] = new CustomPoint(2, 0);
        squares[3] = new CustomPoint(2, 1);
        squares[4] = new CustomPoint(2, 2);
    }

    //    [ ]
    // [ ][X][ ]
    public void createSmallLeftShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(0, 1);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
        squares[3] = new CustomPoint(1, 2);
    }

    // [ ][X][ ]
    //    [ ]
    //    [ ]
    public void createBigRightShape() {
        squares = new CustomPoint[5];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(0, 1);
        squares[2] = new CustomPoint(0, 2);
        squares[3] = new CustomPoint(1, 1);
        squares[4] = new CustomPoint(2, 1);
    }
    // [ ][X][ ]
    //    [ ]
    public void createSmallRightShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(0, 1);
        squares[2] = new CustomPoint(0, 2);
        squares[3] = new CustomPoint(1, 1);
    }

}
