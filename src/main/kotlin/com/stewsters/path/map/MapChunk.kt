package com.stewsters.path.map

import com.stewsters.path.entity.Entity
import com.stewsters.path.entity.TurnTaker
import java.util.*

class MapChunk constructor(val xSize: Int, val ySize: Int) {

    private val tiles = Array<Tile>(xSize * ySize, { Tile(TileType.GRASS) })
    val pawnQueue: Queue<TurnTaker> = PriorityQueue<TurnTaker>()

    private val spatialHash: SpatialHash = SpatialHash(xSize, ySize)

    fun at(x: Int, y: Int): Optional<Tile> {
        if (x < 0 || y < 0 || x >= xSize || y >= ySize)
            return Optional.empty<Tile>()
        return Optional.of(tiles.get(x + y * xSize))
    }

    fun contains(x: Int, y: Int): Boolean {
        return x > 0 && y > 0 && x < xSize && y < ySize
    }

    fun outside(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= xSize || y >= ySize)
    }

    fun updatePawnPos(pawn: Entity, xPos: Int, yPos: Int) {
        spatialHash.remove(pawn)
        pawn.pos.x = xPos
        pawn.pos.y = yPos
        spatialHash.add(pawn)
    }

    fun pawnInSquare(xPos: Int, yPos: Int, xPos2: Int = xPos, yPos2: Int = yPos): List<Entity> {
        return spatialHash.findEntitiesInSquare(xPos, yPos, xPos2, yPos2)
    }

    fun addPawn(entity: Entity) {
        spatialHash.add(entity)
        if (entity.turnTaker != null) {
            pawnQueue.add(entity.turnTaker)
        }
    }


    fun writeToDisk() {
        TODO("Not implemented yet")
    }

    fun restoreFromDisk() {
        TODO("Not implemented yet")
    }
}