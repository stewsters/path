package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import kaiju.math.Vec2

class MapTransitionAction(entity: Entity, private var movement: Vec2) : Action(entity) {

    override fun onPerform(): ActionResult {

        // if we are on an edge, and moving off the map
        val currentPos = pawn.pos

        val nextPos = currentPos + movement

        if (chunkMap.inside(nextPos)) {
            return ActionResult.FAILURE // could also just walk, but they should get from walk to here
        }

        val world = chunkMap.world
        val nextChunkCoord = chunkMap.pos + movement
        if (world.outside(nextChunkCoord)) {
            return ActionResult.FAILURE
        }

        val newChunk = world.getMapAt(nextChunkCoord)

        println("world transition from ${pawn.chunk.pos} to ${newChunk.pos}")

        pawn.chunk = newChunk

        chunkMap.removePawn(pawn)

        // find next turn
        pawn.turnTaker?.gameTurn = (newChunk.pawnQueue.peek()?.gameTurn ?: 1) - 1

        // Update positions
        if (movement.x < 0) {
            pawn.pos = Vec2[newChunk.upper.x, pawn.pos.y]
        } else if (movement.x > 0) {
            pawn.pos = Vec2[0, pawn.pos.y]
        }

        if (movement.y < 0) {
            pawn.pos = Vec2[pawn.pos.x, newChunk.upper.y]
        } else if (movement.y > 0) {
            pawn.pos = Vec2[pawn.pos.x, 0]
        }

        newChunk.addPawn(pawn)

        return ActionResult.BREAKOUT

    }

}