package com.stewsters.path.map

import com.stewsters.path.ecs.component.TurnTaker
import com.stewsters.path.ecs.entity.Entity
import kaiju.math.Matrix2d
import kaiju.math.Matrix3d
import kaiju.math.Rectangle
import kaiju.math.RectangularPrism
import kaiju.math.Vec2
import kaiju.math.Vec3

import java.io.File
import java.util.*

class MapChunk(val world: World, val pos: Vec3,
               xSize: Int, ySize: Int, zSize:Int) : RectangularPrism(Vec3[0, 0,0], Vec3[xSize - 1, ySize - 1, zSize-1]) {

    private val tiles = Matrix3d<Tile>(xSize, ySize,zSize) { x, y, z -> Tile(TileType.GRASS) }
    val pawnQueue: PriorityQueue<TurnTaker> = PriorityQueue()

    private val spatialHash: SpatialHash = SpatialHash(xSize, ySize,zSize)

    fun at(p: Vec3): Tile = at(p.x, p.y,p.z)
    fun at(x: Int, y: Int,z:Int): Tile = tiles[x, y,z]

    fun updatePawnPos(pawn: Entity, xPos: Int, yPos: Int, zPos:Int) {
        spatialHash.remove(pawn)
        pawn.pos = Vec3[xPos, yPos, zPos]
        spatialHash.add(pawn)
    }

    fun pawnInSquare(p: Vec3): List<Entity> = pawnInSquare(p.x, p.y,p.z)

    fun pawnInSquare(xPos: Int, yPos: Int, zPos:Int, xPos2: Int = xPos, yPos2: Int = yPos, zPos2: Int = zPos): List<Entity> {
        return spatialHash.findEntitiesInSquare(xPos, yPos,zPos, xPos2, yPos2, zPos2)
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

    fun writeToDisk(gameSaveFolder: File) {
        // Save map
//        File(gameSaveFolder, "${pos.x}:${pos.y}.map")
//                .writeBytes(tiles.data.map { it.type.ordinal.toByte() }.toByteArray())

        val entitySave = File(gameSaveFolder, "${pos.x}:${pos.y}:${pos.z}.ent")

        // TODO: serialize Entities:
        spatialHash.findEntitiesInSquare(0, 0, 0,upper.x - 1, upper.y - 1, upper.z-1).forEach {
            //            entitySave.writeText(JSON.Companion.stringify(it))
        }

    }

    fun restoreFromDisk(gameSaveFolder: File) {
        val values = TileType.values()
//        File(gameSaveFolder, "${pos.x}:${pos.y}.map")
//                .readBytes()
//                .map { values[it.toInt()] }
//                .forEachIndexed { index, tileType ->
//                    tiles.data[index].type = tileType
//                }

        // TODO: restore entities
    }

}