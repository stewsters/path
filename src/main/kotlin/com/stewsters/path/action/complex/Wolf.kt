package com.stewsters.path.action.complex

import com.stewsters.path.action.Action
import com.stewsters.path.action.WalkAction
import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.MapChunk
import kaiju.math.Vec3
import kaiju.math.getChebyshevDistance
import kaiju.math.limit

//class Wolf(pawn: Entity, chunkMap: MapChunk = pawn.chunk, costInTurns: Int = 100) :
//    Action(pawn, chunkMap, costInTurns) {
//}

val wolfAction: (MapChunk, Entity) -> Action? = { mapChunk, entity ->

    // find nearest hostile
    val sightRange = Vec3(5, 5, 5)
    val enemy = mapChunk.pawnInSquare(entity.pos - sightRange, entity.pos + sightRange)
        .asSequence()
        .filter { it.faction != entity.faction }
        .toList()
        .minByOrNull { getChebyshevDistance(it.pos, entity.pos) }

    if (enemy == null) {
        // go about business
        WalkAction(
            entity, Vec3(
                (-1 .. 1).random(),
                (-1 .. 1).random(),
                0
            )
        );
    } else {

        // bite if adjacent
        val playerX = enemy.globalX()
        val playerY = enemy.globalY()
        val xPos = entity.globalX()
        val yPos = entity.globalY()

        WalkAction(
            entity, Vec3(
                limit(playerX - xPos, -1, 1),
                limit(playerY - yPos, -1, 1),
                0
            )
        )
    }
}