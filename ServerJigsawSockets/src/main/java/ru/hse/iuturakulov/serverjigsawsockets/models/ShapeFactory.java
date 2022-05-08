package ru.hse.iuturakulov.serverjigsawsockets.models;


import ru.hse.iuturakulov.serverjigsawsockets.models.enums.*;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.OneBlock;
import ru.hse.iuturakulov.serverjigsawsockets.models.shapes.Shape;
import ru.hse.iuturakulov.serverjigsawsockets.utils.BlockHelper;

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

    public static ShapeFactory getInstance() {
        return SingletonHolder.SHAPE_FACTORY;
    }

    /**
     * Gets random figure.
     *
     * @return the random figure
     */
    public Shape getRandomFigure() {
        ShapeType shapeType = ShapeType.values()[random.nextInt(7)];
        BlockOrientation type = BlockOrientation.values()[random.nextInt(2)];
        BlockSide blockSide = BlockSide.values()[random.nextInt(2)];
        BlockType blockType = BlockType.values()[random.nextInt(3)];
        BlockPosition blockPosition = BlockPosition.values()[random.nextInt(2)];
        return switch (shapeType) {
            case BLOCK_I -> blockHelper.generateIBlock(blockPosition);
            case BLOCK_J -> blockHelper.generateJBlock(type, blockSide);
            case BLOCK_L -> blockHelper.generateLBlock(blockType, blockPosition, blockSide);
            case BLOCK_S -> blockHelper.generateSBlock(blockSide);
            case BLOCK_Z -> blockHelper.generateZBlock(blockSide);
            case BLOCK_T -> blockHelper.generateTBlock(blockPosition, blockType, blockSide);
            case BLOCK_O -> new OneBlock();
        };
    }

    public static class SingletonHolder {
        public static final ShapeFactory SHAPE_FACTORY = new ShapeFactory();
    }
}
