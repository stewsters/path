package com.stewsters.path.map

import com.stewsters.path.Game.saveFolder
import com.stewsters.path.ecs.component.TurnTaker
import com.stewsters.path.ecs.entity.Entity
import veclib.Box
import veclib.Vec2
import java.io.File
import java.util.*

class MapChunk(val world: World, val pos: Vec2,
               xSize: Int, ySize: Int) : Box(xSize, ySize) {

    private val tiles = Array(xSize * ySize, { Tile(TileType.GRASS) })
    val pawnQueue: PriorityQueue<TurnTaker> = PriorityQueue()

    private val spatialHash: SpatialHash = SpatialHash(xSize, ySize)

    fun at(p: Vec2): Tile = at(p.x, p.y)
    fun at(x: Int, y: Int): Tile {
        return tiles[x + y * highX]
    }

    fun updatePawnPos(pawn: Entity, xPos: Int, yPos: Int) {
        spatialHash.remove(pawn)
        pawn.pos = Vec2.get(xPos, yPos)
        spatialHash.add(pawn)
    }

    fun pawnInSquare(p: Vec2): List<Entity> = pawnInSquare(p.x, p.y)

    fun pawnInSquare(xPos: Int, yPos: Int, xPos2: Int = xPos, yPos2: Int = yPos): List<Entity> {
        return spatialHash.findEntitiesInSquare(xPos, yPos, xPos2, yPos2)
    }

    fun addPawn(entity: Entity) {
        spatialHash.add(entity)
        if (entity.turnTaker != null) {
            pawnQueue.add(entity.turnTaker)
        }
    }

    fun removePawn(pawn: Entity) {
        spatialHash.remove(pawn)
        if (pawn.turnTaker != null) {
            pawnQueue.remove(pawn.turnTaker)
        }
    }

    fun writeToDisk() {
        File(saveFolder, "${pos.x}:${pos.y}.map")
                .writeBytes(tiles.map { it.type.ordinal as Byte }.toByteArray())

        // TODO: serialize data

    }

    fun restoreFromDisk() {
        val values = TileType.values()
        File(saveFolder, "${pos.x}:${pos.y}.map")
                .readBytes()
                .map { values[it as Int] }
                .forEachIndexed { index, tileType -> tiles[index].type = tileType }

        // TODO: restore entities
    }

}