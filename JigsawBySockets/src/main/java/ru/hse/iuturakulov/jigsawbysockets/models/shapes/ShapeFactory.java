package ru.hse.iuturakulov.jigsawbysockets.models.shapes;

import ru.hse.iuturakulov.jigsawbysockets.models.enums.*;
import ru.hse.iuturakulov.jigsawbysockets.utils.BlockHelper;

import java.util.Random;

/**
 * Shape factory. To generate figures.
 *
 * @author Islombek Turakulov
 * @version 1.0-SNAPSHOT
 * @see Tiles
 */
public class ShapeFactory {

    private final BlockHelper blockHelper = new BlockHelper();
    private final Random random = new Random();
    private ShapeType currentShape;

    public static ShapeFactory getInstance() {
        return SingletonHolder.SHAPE_FACTORY;
    }

    /**
     * Gets random figure.
     *
     * @return the random figure
     */
    public Shape getRandomFigure(BlockOrientation type, BlockSide blockSide, BlockType blockType, BlockPosition blockPosition) {
        Shape shape = switch (currentShape) {
            case BLOCK_I -> blockHelper.generateIBlock(blockPosition);
            case BLOCK_J -> blockHelper.generateJBlock(type, blockSide);
            case BLOCK_L -> blockHelper.generateLBlock(blockType, blockPosition, blockSide);
            case BLOCK_S -> blockHelper.generateSBlock(blockSide);
            case BLOCK_Z -> blockHelper.generateZBlock(blockSide);
            case BLOCK_T -> blockHelper.generateTBlock(blockPosition, blockType, blockSide);
            default -> new OneBlock();
        };
        shape.setShapeType(currentShape);
        return shape;
    }

    public void setCurrentShape(ShapeType currentShape) {
        this.currentShape = currentShape;
    }

    public void setRandomShape() {
        this.currentShape = ShapeType.values()[random.nextInt(7)];
    }

    public static class SingletonHolder {
        public static final ShapeFactory SHAPE_FACTORY = new ShapeFactory();
    }
}
