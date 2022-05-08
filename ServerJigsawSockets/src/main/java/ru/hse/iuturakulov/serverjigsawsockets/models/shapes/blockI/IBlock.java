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
public class IBlock extends Shape {

    // [ ][X][ ]
    public IBlock() {
        super(Color.YELLOW);
        squares = new CustomPoint[3];
        squares[0] = new CustomPoint(0, 0);
        squares[1] = new CustomPoint(0, 1);
        squares[2] = new CustomPoint(0, 2);
    }
}
