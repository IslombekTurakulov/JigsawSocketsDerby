package ru.hse.iuturakulov.jigsawbysockets.models.shapes;

import ru.hse.iuturakulov.jigsawbysockets.models.enums.*;

public record FigureType(BlockOrientation blockOrientation, BlockPosition blockPosition, BlockSide blockSide,
                         BlockType blockType, ShapeType shapeType) {

}
