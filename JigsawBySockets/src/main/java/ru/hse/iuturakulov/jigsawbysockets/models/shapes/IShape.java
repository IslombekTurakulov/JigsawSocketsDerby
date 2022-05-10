package ru.hse.iuturakulov.jigsawbysockets.models.shapes;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.jigsawbysockets.models.CustomPoint;

/**
 * The interface of class Shape.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see CustomPoint
 */
public interface IShape {
    /**
     * Gets color.
     *
     * @return the color
     */
    Color getColor();

    /**
     * Get squares custom point [ ].
     *
     * @return the custom point [ ]
     */
    CustomPoint[] getSquares();
}
