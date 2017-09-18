package com.stewsters.path.entity

import com.stewsters.path.map.MapChunk
import com.stewsters.util.math.Point2i
import java.awt.Color

data class Entity(
        var chunk: MapChunk,
        var pos: Point2i,

        var char: Char = '@',
        var color: Color = Color.WHITE,

        var turnTaker: TurnTaker? = null,

        var life: Life? = null,
        var deathFunction: (Entity) -> Unit = {},

        var inventory: Inventory?=null,
        val doorOpener: Boolean = true,

        val xSize: Int = 1,
        val ySize: Int = 1
) {

    init {
        turnTaker?.parent = this
    }

    // TODO: this should probably be moved to a function
    fun canTraverse(xCur: Int, yCur: Int, xPos: Int, yPos: Int): Boolean {
        return !chunk.at(xPos, yPos).type.blocks
    }

    fun globalX(): Int {
        return chunk.x * chunk.xSize + pos.x
    }

    fun globalY(): Int {
        return chunk.y * chunk.ySize + pos.y
    }


}
