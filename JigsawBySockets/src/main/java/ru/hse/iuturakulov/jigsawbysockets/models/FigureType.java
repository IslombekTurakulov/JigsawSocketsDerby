package ru.hse.iuturakulov.jigsawbysockets.models;

import ru.hse.iuturakulov.jigsawbysockets.models.enums.*;

import java.util.Objects;


/**
 * The type Figure type.
 *
 * @author Islombek Turakulov
 * @version 1.0
 * @see BlockPosition
 * @see BlockSide
 * @see BlockType
 * @see ShapeType
 * @see BlockOrientation
 */
public class FigureType {
    private final BlockOrientation blockOrientation;
    private final BlockPosition blockPosition;
    private final BlockSide blockSide;
    private final BlockType blockType;
    private final ShapeType shapeType;

    /**
     * Instantiates a new Figure type.
     *
     * @param blockOrientation the block orientation
     * @param blockPosition    the block position
     * @param blockSide        the block side
     * @param blockType        the block type
     * @param shapeType        the shape type
     */
    public FigureType(BlockOrientation blockOrientation,
                      BlockPosition blockPosition,
                      BlockSide blockSide,
                      BlockType blockType,
                      ShapeType shapeType) {

        this.blockOrientation = blockOrientation;
        this.blockPosition = blockPosition;
        this.blockSide = blockSide;
        this.blockType = blockType;
        this.shapeType = shapeType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (FigureType) obj;
        return Objects.equals(this.blockOrientation, that.blockOrientation) &&
               Objects.equals(this.blockPosition, that.blockPosition) &&
               Objects.equals(this.blockSide, that.blockSide) &&
               Objects.equals(this.blockType, that.blockType) &&
               Objects.equals(this.shapeType, that.shapeType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockOrientation, blockPosition, blockSide, blockType, shapeType);
    }

    @Override
    public String toString() {
        return "FigureType[" +
               "blockOrientation=" + blockOrientation + ", " +
               "blockPosition=" + blockPosition + ", " +
               "blockSide=" + blockSide + ", " +
               "blockType=" + blockType + ", " +
               "shapeType=" + shapeType + ']';
    }

    /**
     * Block orientation.
     *
     * @return the block orientation
     */
    public BlockOrientation blockOrientation() {
        return blockOrientation;
    }

    /**
     * Block position.
     *
     * @return the block position
     */
    public BlockPosition blockPosition() {
        return blockPosition;
    }

    /**
     * Block side.
     *
     * @return the block side
     */
    public BlockSide blockSide() {
        return blockSide;
    }

    /**
     * Block type.
     *
     * @return the block type
     */
    public BlockType blockType() {
        return blockType;
    }

    /**
     * Shape type.
     *
     * @return the shape type
     */
    public ShapeType shapeType() {
        return shapeType;
    }

}
