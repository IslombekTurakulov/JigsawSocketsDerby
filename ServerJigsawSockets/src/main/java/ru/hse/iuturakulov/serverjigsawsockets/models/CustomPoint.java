package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;

/**
 * The type Custom point.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Shape
 */
public class CustomPoint {

    /**
     * Coordinates of the point
     */
    private int x, y;

    /**
     * Constructor of the Point class specifying the coordinates of the point
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     */
    public CustomPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter for x
     *
     * @return the x coordinate of the point
     */
    public int getX() {
        return x;
    }

    /**
     * Setter for x
     *
     * @param x the new x coordinate of the point
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter for y
     *
     * @return the y coordinate of the point
     */
    public int getY() {
        return y;
    }

    /**
     * Setter for y
     *
     * @param y the new y coordinate of the point
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomPoint) {
            return x == ((CustomPoint) obj).x && y == ((CustomPoint) obj).y;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Point X: %d Y: %d".formatted(x, y);
    }
}