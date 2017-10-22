package com.stewsters.path.entity

import com.stewsters.path.map.MapChunk
import com.stewsters.util.math.Point2i
import java.awt.Color

class Entity(
        var chunk: MapChunk,
        var pos: Point2i,

        var name: String,
        var description:String?=null,

        var char: Char = '@',
        var color: Color = Color.WHITE,

        var turnTaker: TurnTaker? = null,
        var faction: Faction?=null,

        var life: Life? = null,
        var deathFunction: (Entity) -> Unit = {},

        val item: Item? = null,
        var inventory: Inventory? = null,

        val doorOpener: Boolean = false,

        val mountable: Boolean = false,
        var mount: Entity? = null,

        val xSize: Int = 1,
        val ySize: Int = 1,

        val blocks: Boolean = false
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

    fun isAlive(): Boolean {
        return if(life!=null){life?.cur?:0 >= 0} else false// (life?.cur) ? (true) : false
    }

}
