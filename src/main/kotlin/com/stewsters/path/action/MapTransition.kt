package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import com.stewsters.util.math.Point2i

class MapTransition(entity: Entity, var movement: Point2i) : Action(entity) {

    override fun onPerform(): ActionResult {

        // if we are on an edge, and moving off the map
        val xCur = pawn.pos.x
        val yCur = pawn.pos.y
        // val zCur = pawn.pos.z

        val xPos = xCur + movement.x
        val yPos = yCur + movement.y
        // val zPos = zCur

        if (chunkMap.contains(xPos, yPos)) {
            return ActionResult.FAILURE // could also just walk
        }

        val world = chunkMap.world
        if (world.outside(world.xFocus + movement.x, world.yFocus + movement.y)) {
            return ActionResult.FAILURE
        }


        val newChunk = world.getMapAt(chunkMap.x + movement.x, chunkMap.y + movement.y)

        world.xFocus = newChunk.x
        world.yFocus = newChunk.y

        chunkMap.removePawn(pawn)
        if (movement.x < 0) {
            pawn.pos.x = newChunk.xSize - 1
        } else if (movement.x > 0) {
            pawn.pos.x = 0
        }

        if (movement.y < 0) {
            pawn.pos.y = newChunk.ySize - 1
        } else if (movement.y > 0) {
            pawn.pos.y = 0
        }

        newChunk.addPawn(pawn)

        return ActionResult.SUCCESS


    }

}