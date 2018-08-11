package com.stewsters.path.map

import java.awt.Color

enum class TileType(val word: String, val blocks: Boolean, val char: Char, val foreground: Color, val background: Color = Color.BLACK) {

    AIR("air", false, ' ', Color(0, 0, 0, 0), Color(0, 0, 0, 0)),

    GRASS("grass", false, '.', Color.BLACK, Color(20, 200, 20)),
    TREE("tree", true, '£', Color.BLACK, Color(20, 150, 20)),

    WALL("wall", true, 'X', Color.BLACK, Color.WHITE),
    STONE_FLOOR("stone floor", false, '.', Color.BLACK, Color.gray),

    OPEN_DOOR("open door", false, '-', Color.BLACK, Color.LIGHT_GRAY),
    CLOSED_DOOR("closed door", true, '+', Color.BLACK, Color.LIGHT_GRAY),

    WATER_SWAMP("swamp", false, ';', Color(255, 222, 173), Color(160, 82, 45)),
    WATER_LAKE("lake", false, '~', Color.CYAN, Color.BLUE)
}