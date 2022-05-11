package ru.hse.iuturakulov.jigsawbysockets.models.shapes;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.jigsawbysockets.models.CustomPoint;

/**
 * The type of block.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 * @see CustomPoint
 */
public class OneBlock extends Shape {
    // [ ]
    public OneBlock() {
        super(Color.ORANGERED);
        squares = new CustomPoint[1];
        squares[0] = new CustomPoint(0, 0);
    }
}
