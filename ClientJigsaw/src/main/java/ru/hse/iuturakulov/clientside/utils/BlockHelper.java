package ru.hse.iuturakulov.clientside.utils;

import ru.hse.iuturakulov.clientside.models.enums.BlockOrientation;
import ru.hse.iuturakulov.clientside.models.enums.BlockPosition;
import ru.hse.iuturakulov.clientside.models.enums.BlockSide;
import ru.hse.iuturakulov.clientside.models.enums.BlockType;
import ru.hse.iuturakulov.clientside.models.shapes.Shape;
import ru.hse.iuturakulov.clientside.models.shapes.ShapeFactory;
import ru.hse.iuturakulov.clientside.models.shapes.blockI.IBlock;
import ru.hse.iuturakulov.clientside.models.shapes.blockI.IBlockInverted;
import ru.hse.iuturakulov.clientside.models.shapes.blockJ.JBlockHor;
import ru.hse.iuturakulov.clientside.models.shapes.blockJ.JBlockVert;
import ru.hse.iuturakulov.clientside.models.shapes.blockL.*;
import ru.hse.iuturakulov.clientside.models.shapes.blockSZ.SBlock;
import ru.hse.iuturakulov.clientside.models.shapes.blockSZ.ZBlock;
import ru.hse.iuturakulov.clientside.models.shapes.blockT.TBlock;
import ru.hse.iuturakulov.clientside.models.shapes.blockT.TBlockInverted;

/**
 * Class Block helper. To produce blocks.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see ShapeFactory
 */
public class BlockHelper {

    /**
     * Instantiates a new Block helper.
     */
    public BlockHelper() {
    }

    /**
     * Generates BlockI shape.
     *
     * @param blockPosition the block position
     * @return the shape
     */
    public Shape generateIBlock(BlockPosition blockPosition) {
        if (blockPosition == BlockPosition.DEFAULT) {
            return new IBlock();
        } else {
            return new IBlockInverted();
        }
    }

    /**
     * Generate BlockJ shape.
     *
     * @param type      the type
     * @param blockSide the block side
     * @return the shape
     */
    public Shape generateJBlock(BlockOrientation type, BlockSide blockSide) {
        if (type == BlockOrientation.HORIZONTAL) {
            JBlockHor jBlockHor = new JBlockHor();
            if (blockSide == BlockSide.LEFT) {
                jBlockHor.createLeftShape();
            } else {
                jBlockHor.createRightShape();
            }
            return jBlockHor;
        } else {
            JBlockVert jBlockVert = new JBlockVert();
            if (blockSide == BlockSide.LEFT) {
                jBlockVert.createLeftShape();
            } else {
                jBlockVert.createRightShape();
            }
            return jBlockVert;
        }
    }

    /**
     * Generate BlockL shape.
     *
     * @param blockType     the block type
     * @param blockPosition the block position
     * @param blockSide     the block side
     * @return the shape
     */
    public Shape generateLBlock(BlockType blockType, BlockPosition blockPosition, BlockSide blockSide) {
        if (blockType == BlockType.BIG) {
            if (blockPosition == BlockPosition.INVERTED) {
                var figure = new BigLBlockInverted();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            } else {
                var figure = new BigLBlock();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            }
        } else if (blockType == BlockType.SMALL) {
            if (blockPosition == BlockPosition.INVERTED) {
                var figure = new SmallLBlockInverted();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            } else {
                var figure = new SmallLBlock();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            }
        } else {
            if (blockPosition == BlockPosition.DEFAULT) {
                var figure = new LBlock();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            } else {
                var figure = new LBlockInverted();
                if (blockSide == BlockSide.LEFT) {
                    figure.createLeftShape();
                } else {
                    figure.createRightShape();
                }
                return figure;
            }
        }
    }

    /**
     * Generate BlockS shape.
     *
     * @param blockSide the block side
     * @return the shape
     */
    public Shape generateSBlock(BlockSide blockSide) {
        var figure = new SBlock();
        if (blockSide == BlockSide.LEFT) {
            figure.createLeftShape();
        } else {
            figure.createRightShape();
        }
        return figure;
    }

    /**
     * Generate BlockZ shape.
     *
     * @param blockSide the block side
     * @return the shape
     */
    public Shape generateZBlock(BlockSide blockSide) {
        var figure = new ZBlock();
        if (blockSide == BlockSide.LEFT) {
            figure.createLeftShape();
        } else {
            figure.createRightShape();
        }
        return figure;
    }

    /**
     * Generate BlockT shape.
     *
     * @param blockPosition the block position
     * @param blockType     the block type
     * @param blockSide     the block side
     * @return the shape
     */
    public Shape generateTBlock(BlockPosition blockPosition, BlockType blockType, BlockSide blockSide) {
        if (blockPosition == BlockPosition.DEFAULT) {
            var figure = new TBlock();
            if (blockType == BlockType.BIG) {
                if (blockSide == BlockSide.LEFT) {
                    figure.createBigLeftShape();
                } else {
                    figure.createBigRightShape();
                }
            } else {
                if (blockSide == BlockSide.LEFT) {
                    figure.createSmallLeftShape();
                } else {
                    figure.createSmallRightShape();
                }
            }
            return figure;
        } else {
            var figure = new TBlockInverted();
            if (blockType == BlockType.BIG) {
                if (blockSide == BlockSide.LEFT) {
                    figure.createBigLeftShape();
                } else {
                    figure.createBigRightShape();
                }
            } else {
                if (blockSide == BlockSide.LEFT) {
                    figure.createSmallLeftShape();
                } else {
                    figure.createSmallRightShape();
                }
            }
            return figure;
        }
    }
}
