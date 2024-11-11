package com.stewsters.path.ecs.entity

import com.stewsters.path.ecs.component.Inventory
import com.stewsters.path.ecs.component.Item
import com.stewsters.path.ecs.component.Life
import com.stewsters.path.ecs.component.TurnTaker
import com.stewsters.path.ecs.enums.DisplayOrder
import com.stewsters.path.ecs.enums.Faction
import com.stewsters.path.map.MapChunk
import kaiju.math.Vec3
import java.awt.Color

class Entity(
        var chunk: MapChunk,
        var pos: Vec3,

        var name: String,
        var description: String? = null,

        var char: Int = '@'.code,
        var color: Color = Color.WHITE,
        var displayOrder: DisplayOrder = DisplayOrder.LAST,

        var turnTaker: TurnTaker? = null,
        var faction: Faction? = null,

        var life: Life? = null,
        var deathFunction: (Entity) -> Unit = {},

        val item: Item? = null,
        var inventory: Inventory? = null,

        val doorOpener: Boolean = false,

        val mountable: Boolean = false,
        var mount: Entity? = null,

        val xSize: Int = 1,
        val ySize: Int = 1,

        var blocks: Boolean = false
) {

    init {
        turnTaker?.parent = this
    }

    // TODO: this should probably be moved to a function
    fun canTraverse(cur: Vec3, next: Vec3) = canTraverse(cur.x, cur.y, cur.z, next.x, next.y, next.z)

    fun canTraverse(xCur: Int, yCur: Int, zCur: Int, xPos: Int, yPos: Int, zPos: Int): Boolean {
        return !chunk.at(xPos, yPos, zPos).type.blocks
    }

    fun globalX(): Int {
        return chunk.pos.x * chunk.upper.x + pos.x
    }

    fun globalY(): Int {
        return chunk.pos.y * chunk.upper.y + pos.y
    }

    fun isAlive(): Boolean = life?.isAlive() ?: false


}
