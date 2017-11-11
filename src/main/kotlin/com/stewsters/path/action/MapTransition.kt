package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import veclib.Vec2

class MapTransition(entity: Entity, var movement: Vec2) : Action(entity) {

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
        if (world.outside(chunkMap.x + movement.x, chunkMap.y + movement.y)) {
            return ActionResult.FAILURE
        }

        val newChunk = world.getMapAt(chunkMap.x + movement.x, chunkMap.y + movement.y)

        println("transition from ${pawn.chunk.x},${pawn.chunk.y} to ${newChunk.x},${newChunk.y}")

        pawn.chunk = newChunk

        chunkMap.removePawn(pawn)
        if (movement.x < 0) {
            pawn.pos = Vec2.get(newChunk.highX - 1, pawn.pos.y)
        } else if (movement.x > 0) {
            pawn.pos = Vec2.get(0, pawn.pos.y)
        }

        if (movement.y < 0) {
            pawn.pos = Vec2.get(pawn.pos.x, newChunk.highY - 1)
        } else if (movement.y > 0) {
            pawn.pos = Vec2.get(pawn.pos.x, 0)
        }

        newChunk.addPawn(pawn)

        return ActionResult.SUCCESS

    }

}