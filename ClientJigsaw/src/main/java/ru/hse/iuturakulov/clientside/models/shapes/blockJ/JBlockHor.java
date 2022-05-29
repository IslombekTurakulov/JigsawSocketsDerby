package ru.hse.iuturakulov.clientside.models.shapes.blockJ;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.clientside.models.CustomPoint;
import ru.hse.iuturakulov.clientside.models.shapes.Shape;

/**
 * The type of block.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 * @see CustomPoint
 */
public class JBlockHor extends Shape {

    public JBlockHor() {
        super(Color.ROSYBROWN);
    }

    // [ ]
    // [X][ ][ ]
    public void createLeftShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(1, 0);
        squares[1] = new CustomPoint(0, 0);
        squares[2] = new CustomPoint(1, 1);
        squares[3] = new CustomPoint(1, 2);
    }

    //       [X]
    // [ ][ ][ ]
    public void createRightShape() {
        squares = new CustomPoint[4];
        squares[0] = new CustomPoint(0, 2);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(1, 1);
        squares[3] = new CustomPoint(1, 2);
    }
}