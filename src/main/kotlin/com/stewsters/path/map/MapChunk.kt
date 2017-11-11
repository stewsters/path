package com.stewsters.path.map

import com.stewsters.path.entity.Entity
import com.stewsters.path.entity.TurnTaker
import veclib.Box
import veclib.Vec2
import java.util.*

class MapChunk(val world: World, val x: Int, val y: Int,
               xSize: Int, ySize: Int) : Box(xSize, ySize) {

    private val tiles = Array<Tile>(xSize * ySize, { Tile(TileType.GRASS) })
    val pawnQueue: PriorityQueue<TurnTaker> = PriorityQueue<TurnTaker>()

    private val spatialHash: SpatialHash = SpatialHash(xSize, ySize)

    fun at(p: Vec2): Tile = at(p.x, p.y)
    fun at(x: Int, y: Int): Tile {
        return tiles.get(x + y * highX)
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
        TODO("Not implemented yet")
    }

    fun restoreFromDisk() {
        TODO("Not implemented yet")
    }

    fun update(pawn: Entity) {

    }

}