package com.stewsters.path.map

import com.stewsters.path.ecs.entity.Entity
import kaiju.math.matrix3dOf

class SpatialHash(xSize: Int, ySize: Int, zSize: Int) {

    private val hash = matrix3dOf<ArrayList<Entity>>(xSize, ySize, zSize) { x, y, z -> arrayListOf() }

    fun get(x: Int, y: Int, z: Int): List<Entity> {
        return hash[x, y, z]
    }

    fun findEntitiesInSquare(xPos: Int, yPos: Int, zPos: Int, xPos2: Int, yPos2: Int, zPos2: Int): List<Entity> {
        val result = ArrayList<Entity>()
        for (x in (xPos..xPos2)) {
            for (y in (yPos..yPos2)) {
                for (z in (zPos..zPos2)) {
                    result.addAll(get(x, y, z))
                }
            }
        }
        return result
    }

    fun remove(pawn: Entity) {
        hash[pawn.pos].remove(pawn)
    }

    fun add(pawn: Entity) {
        hash[pawn.pos].add(pawn)
    }

}