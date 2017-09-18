package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import com.stewsters.path.map.TileType

class CloseAdjacentDoors(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE
        }

        for (x in pawn.pos.x - 1..pawn.pos.x + 1) {
            for (y in pawn.pos.y - 1..pawn.pos.y + 1) {
                if (chunkMap.at(x, y).type == TileType.OPEN_DOOR
                        // && !chunkMap.pawnInSquare(x,y).any { it.blocks }
                        && chunkMap.pawnInSquare(x, y).size == 0) {

                    chunkMap.at(x, y).type = TileType.CLOSED_DOOR
                    return ActionResult.SUCCESS
                }
            }
        }

        return ActionResult.FAILURE

    }

}
