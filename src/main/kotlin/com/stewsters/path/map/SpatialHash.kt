package com.stewsters.path.map

import com.stewsters.path.entity.Entity

class SpatialHash(val xSize: Int, val ySize: Int) {

    private val hash = Array<ArrayList<Entity>>(xSize * ySize, { arrayListOf() })

    fun get(x: Int, y: Int): List<Entity> {
        return hash.get(index(x, y))
    }

    fun findEntitiesInSquare(xPos: Int, yPos: Int, xPos2: Int, yPos2: Int): List<Entity> {
        val result = ArrayList<Entity>()
        for (x in (xPos..xPos2)) {
            for (y in (yPos..yPos2)) {
                result.addAll(get(x, y))
            }
        }
        return result
    }

    fun remove(pawn: Entity) {
        hash.get(index(pawn.pos.x, pawn.pos.y)).remove(pawn)
    }

    fun add(pawn: Entity) {
        hash.get(index(pawn.pos.x, pawn.pos.y)).add(pawn)
    }

    private fun index(x: Int, y: Int): Int {
        return x + y * xSize
    }

}