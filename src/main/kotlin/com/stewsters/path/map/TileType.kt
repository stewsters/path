package com.stewsters.path.map

import java.awt.Color

enum class TileType(val word: String, val blocks: Boolean, val char: Int, val foreground: Color, val background: Color = Color.BLACK) {

    GRASS("grass", false, '.'.code, Color.BLACK, Color(20, 200, 20)),
    TREE("tree", true, '£'.code, Color.BLACK, Color(20, 150, 20)),

    WALL("wall", true, 'X'.code, Color.BLACK, Color.WHITE),
    STONE_FLOOR("stone floor", false, '.'.code, Color.BLACK, Color.gray),

    OPEN_DOOR("open door", false, '-'.code, Color.BLACK, Color.LIGHT_GRAY),
    CLOSED_DOOR("closed door", true, '+'.code, Color.BLACK, Color.LIGHT_GRAY),

    WATER_SWAMP("swamp", false, ';'.code, Color(255, 222, 173), Color(160, 82, 45)),
    WATER_LAKE("lake", false, '~'.code, Color.CYAN, Color.BLUE)
}