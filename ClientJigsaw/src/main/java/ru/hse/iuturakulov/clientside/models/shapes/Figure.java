package ru.hse.iuturakulov.clientside.models.shapes;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import ru.hse.iuturakulov.clientside.models.FigureType;
import ru.hse.iuturakulov.clientside.models.Game;
import ru.hse.iuturakulov.clientside.utils.Constants;

import java.util.ArrayList;
import java.util.logging.Level;

import static ru.hse.iuturakulov.clientside.utils.Constants.SIZE;

/**
 * The type Figure.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 */
public class Figure extends Pane {

    private final ArrayList<Rectangle> rectangleList = new ArrayList<>();
    private final int sizeOfFigure;
    private final double initialX;
    private final double initialY;

    /**
     * Instantiates a new Figure.
     *
     * @param layoutX the layout x
     * @param layoutY the layout y
     */
    public Figure(double layoutX, double layoutY) {
        super();
        // Set original layouts for figure.
        this.setLayoutY(layoutY);
        this.setLayoutX(layoutX);
        initialX = layoutX;
        initialY = layoutY;
        FigureType figureType = Game.getNextShape();
        ShapeFactory.getInstance().setCurrentShape(figureType.shapeType());
        Shape fromServerFigure = ShapeFactory.getInstance().getRandomFigure(
                figureType.blockOrientation(),
                figureType.blockSide(),
                figureType.blockType(),
                figureType.blockPosition());
        for (int i = 0; i < fromServerFigure.getSquares().length; i++) {
            int y = fromServerFigure.getSquares()[i].getX();
            int x = fromServerFigure.getSquares()[i].getY();
            Tiles tileModel = new Tiles(x, y, new Rectangle(SIZE, SIZE));
            rectangleList.add(tileModel.tileCreation(fromServerFigure.getColor()));
        }
        sizeOfFigure = rectangleList.size();
        this.getChildren().addAll(rectangleList);
        // Initializing events.
        handleEventsOnFigure(layoutX, layoutY);
    }

    private void handleEventsOnFigure(double layoutX, double layoutY) {
        this.setOnMouseDragged(mouseEvent -> {
            if (!Game.isIsNeedToPlace()) {
                this.setLayoutX(mouseEvent.getX() + this.getLayoutX());
                this.setLayoutY(mouseEvent.getY() + this.getLayoutY());
            } else {
                this.setLayoutX(layoutX);
                this.setLayoutY(layoutY);
            }
        });
        this.setOnMouseReleased(mouseEvent -> {
            if (!Game.isIsNeedToPlace()) {
                if (!Game.isPossibleToPlace(this)) {
                    // If not possible, the figure comes to the initial (original place)
                    this.setLayoutX(layoutX);
                    this.setLayoutY(layoutY);
                } else {
                    try {
                        // Checks if all figures are disabled.
                        Game.checkForNewFigure();
                    } catch (IndexOutOfBoundsException | NullPointerException exception) {
                        Constants.LOGGER.log(Level.SEVERE, exception.getMessage());
                    }
                }
            } else {
                this.setLayoutX(layoutX);
                this.setLayoutY(layoutY);
            }
        });
    }

    /**
     * Gets points.
     *
     * @return the points
     */
    public int getPoints() {
        return sizeOfFigure;
    }

    /**
     * Gets rectangle list.
     *
     * @return the rectangle list
     */
    public ArrayList<Rectangle> getRectangleList() {
        return rectangleList;
    }

    public double getInitialX() {
        return initialX;
    }

    public double getInitialY() {
        return initialY;
    }
}
