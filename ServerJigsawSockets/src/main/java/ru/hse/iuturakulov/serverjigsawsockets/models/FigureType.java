package ru.hse.iuturakulov.serverjigsawsockets.models;

import ru.hse.iuturakulov.serverjigsawsockets.models.enums.*;

import java.util.Objects;

public class FigureType {
    private BlockOrientation blockOrientation;
    private BlockPosition blockPosition;
    private BlockSide blockSide;
    private BlockType blockType;
    private ShapeType shapeType;

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

    public BlockOrientation blockOrientation() {
        return blockOrientation;
    }

    public BlockPosition blockPosition() {
        return blockPosition;
    }

    public BlockSide blockSide() {
        return blockSide;
    }

    public BlockType blockType() {
        return blockType;
    }

    public ShapeType shapeType() {
        return shapeType;
    }

}
