package ru.hse.iuturakulov.serverjigsawsockets.models.shapes.blockI;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.serverjigsawsockets.models.CustomPoint;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;

/**
 * The type of block.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 * @see CustomPoint
 */
public class IBlockInverted extends Shape {

    // [ ]
    // [X]
    // [ ]
    public IBlockInverted() {
        super(Color.ORANGE);
        squares = new CustomPoint[3];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(1, 0);
        squares[2] = new CustomPoint(2, 0);
    }
}
