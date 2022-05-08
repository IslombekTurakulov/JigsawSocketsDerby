package ru.hse.iuturakulov.serverjigsawsockets.models;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static ru.hse.iuturakulov.serverjigsawsockets.utils.Constants.*;

public class Tiles {
    final int coordinatesX;
    final int coordinatesY;
    final Rectangle rectangle;
    final Color strokeColor;
    final double arcWidth;
    final double arcHeight;

    public Tiles(int coordinatesOfX, int coordinatesOfY, Rectangle rectangle) {
        this.coordinatesX = coordinatesOfX * SIZE + coordinatesOfX * 2 + 2;
        this.coordinatesY = coordinatesOfY * SIZE + coordinatesOfY * 2 + 2;
        this.rectangle = rectangle;
        strokeColor = Color.BLACK;
        arcWidth = WIDTH_CELL * 1.25;
        arcHeight = HEIGHT_CELL * 1.25;
    }

    public int getCoordinatesOfX() {
        return coordinatesX;
    }

    public int getCoordinatesOfY() {
        return coordinatesY;
    }

    public double getArcWidth() {
        return arcWidth;
    }

    public double getArcHeight() {
        return arcHeight;
    }

    public Rectangle tileCreation(Color backgroundColor) {
        rectangle.setArcHeight(getArcHeight());
        rectangle.setArcWidth(getArcWidth());
        rectangle.setStroke(strokeColor);
        rectangle.setFill(backgroundColor);
        rectangle.setX(getCoordinatesOfX());
        rectangle.setY(getCoordinatesOfY());
        return rectangle;
    }
}
