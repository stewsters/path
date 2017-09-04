package com.stewsters.path.map

import com.stewsters.path.entity.Entity
import java.util.*

class MapChunk constructor(val xSize: Int, val ySize: Int) {

    private val tiles = Array<Tile>(xSize * ySize, { Tile(TileType.GRASS) })

    fun at(x: Int, y: Int): Optional<Tile> {
        if (x < 0 || y < 0 || x >= xSize || y >= ySize)
            return Optional.empty<Tile>()
        return Optional.of(tiles.get(x + y * xSize))
    }

    fun contains(x: Int, y: Int): Boolean {
        return !(x < 0 || y < 0 || x >= xSize || y >= ySize)
    }

    fun outside(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= xSize || y >= ySize)
    }

    fun updatePawnPos(pawn: Entity, xPos: Int, yPos: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun pawnInSquare(xPos: Int, yPos: Int, xPos2: Int = xPos, yPos2: Int = yPos): Entity? {
        TODO("Not implemented")
    }

    //TODO: write to disk
    //TODO: restore from disk
}