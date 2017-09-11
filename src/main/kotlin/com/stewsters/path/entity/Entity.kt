package com.stewsters.path.entity

import com.stewsters.path.map.MapChunk
import com.stewsters.util.math.Point2i
import java.awt.Color

class Entity(
        val chunk: MapChunk,
        var pos: Point2i,

        var char: Char = '@',
        var color: Color = Color.WHITE,

        var turnTaker: TurnTaker? = null,

        var life: Life? = null,
        var deathFunction: (Entity) -> Unit = {},

        val doorOpener: Boolean = true,

        val xSize: Int = 1,
        val ySize: Int = 1
) {

    init {
        turnTaker?.parent = this
    }

    fun canTraverse(xCur: Int, yCur: Int, xPos: Int, yPos: Int): Boolean {
        return !chunk.at(xPos, yPos).get().type.blocks
    }

}


