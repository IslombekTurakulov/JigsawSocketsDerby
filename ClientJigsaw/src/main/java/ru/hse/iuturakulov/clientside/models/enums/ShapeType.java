package ru.hse.iuturakulov.clientside.models.enums;

import java.io.Serializable;

public enum ShapeType implements Serializable {
    BLOCK_I,
    BLOCK_J,
    BLOCK_L,
    BLOCK_S,
    BLOCK_Z,
    BLOCK_T,
    BLOCK_O,
    NONE;

    public String getStatus() {
        return this.name();
    }
}
