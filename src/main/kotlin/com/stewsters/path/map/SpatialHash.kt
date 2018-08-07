package com.stewsters.path.map

import com.stewsters.path.ecs.entity.Entity

class SpatialHash(private val xSize: Int, private val ySize: Int, private val zSize:Int) {

    private val hash = Array<ArrayList<Entity>>(xSize * ySize*zSize) { arrayListOf() }

    fun get(x: Int, y: Int,z:Int): List<Entity> {
        return hash[index(x, y,z)]
    }

    fun findEntitiesInSquare(xPos: Int, yPos: Int,zPos:Int, xPos2: Int, yPos2: Int, zPos2: Int): List<Entity> {
        val result = ArrayList<Entity>()
        for (x in (xPos..xPos2)) {
            for (y in (yPos..yPos2)) {
                for (z in (zPos..zPos2)) {
                    result.addAll(get(x, y,z))
                }
            }
        }
        return result
    }

    fun remove(pawn: Entity) {
        hash[index(pawn.pos.x, pawn.pos.y, pawn.pos.z)].remove(pawn)
    }

    fun add(pawn: Entity) {
        hash[index(pawn.pos.x, pawn.pos.y, pawn.pos.z)].add(pawn)
    }

    private fun index(x: Int, y: Int, z:Int): Int {
        return x + y * xSize
    }

}