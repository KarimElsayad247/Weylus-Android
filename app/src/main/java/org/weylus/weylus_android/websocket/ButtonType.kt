package org.weylus.weylus_android.websocket

enum class ButtonType(val value: Byte) {
    NONE(0b0000_0000),
    PRIMARY(0b0000_0001),
    SECONDARY(0b0000_0010),
    AUXILARY(0b0000_0100),
    FOURTH(0b0000_1000),
    FIFTH(0b0001_0000),
    ERASER(0b0010_0000),
}