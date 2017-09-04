package com.stewsters.path.map

import java.awt.Color

enum class TileType(val word: String, val blocks: Boolean, val char: Char, val foreground: Color, val background: Color = Color.BLACK) {

    GRASS("grass", false, '.', Color.GREEN),
    TREE("tree", true, 'T', Color.GREEN),

    WALL("wall", true, 'X', Color.WHITE),
    STONE_FLOOR("stone floor", false, '.', Color.gray),

    OPEN_DOOR("open door", false, '-', Color.LIGHT_GRAY),
    CLOSED_DOOR("closed door", true, '+', Color.LIGHT_GRAY)
}