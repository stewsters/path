package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.TileType

class CloseAdjacentDoorsAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE
        }

        val z = pawn.pos.z
        for (x in pawn.pos.x - 1..pawn.pos.x + 1) {
            for (y in pawn.pos.y - 1..pawn.pos.y + 1) {
                if (chunkMap.at(x, y, z).type == TileType.OPEN_DOOR
                    // && !chunkMap.pawnInSquare(x,y).any { it.blocks }
                    && chunkMap.pawnInSquare(x, y, z).isEmpty()
                ) {

                    chunkMap.at(x, y, z).type = TileType.CLOSED_DOOR
                    return ActionResult.SUCCESS
                }
            }
        }

        return ActionResult.FAILURE

    }

}
