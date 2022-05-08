package ru.hse.iuturakulov.serverjigsawsockets.models.shapes;

import javafx.scene.paint.Color;
import ru.hse.iuturakulov.serverjigsawsockets.models.CustomPoint;

/**
 * The type Shape.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see CustomPoint
 * @see IShape
 */
public class Shape implements IShape {
    /**
     * The Color.
     */
    protected final Color color;
    /**
     * The Squares.
     */
    protected CustomPoint[] squares;

    /**
     * Instantiates a new Shape.
     */
    public Shape() {
        color = Color.BLACK;
    }

    /**
     * Instantiates a new Shape.
     *
     * @param clr the clr
     */
    public Shape(Color clr) {
        color = clr;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Get squares custom point [ ].
     *
     * @return the custom point [ ]
     */
    @Override
    public CustomPoint[] getSquares() {
        return squares;
    }
}