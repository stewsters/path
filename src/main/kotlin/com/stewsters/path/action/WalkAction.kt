package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import com.stewsters.path.map.TileType
import com.stewsters.util.math.Point2i

class WalkAction(pawn: Entity, val offset: Point2i) : Action(pawn) {


    override fun onPerform(): ActionResult {

        if (offset.x == 0 && offset.y == 0) {
            return ActionResult(RestAction(pawn))
        }

        val xCur = pawn.pos.x
        val yCur = pawn.pos.y
        // val zCur = pawn.pos.z

        val xPos = xCur + offset.x
        val yPos = yCur + offset.y
        // val zPos = zCur


        if (worldMap.outside(xPos, yPos)) {
            return ActionResult.FAILURE
        }

        //See if there is an actor there
        val targetList = worldMap.pawnInSquare(xPos, yPos, xPos + pawn.xSize - 1, yPos + pawn.ySize - 1)

        if (targetList.size > 0) {

            for (target in targetList) {
                if (target == pawn) {
                    continue
                } else if ((pawn.turnTaker != null) != (target.turnTaker != null))
                    return ActionResult(AttackAction(pawn, target))
                else
                    return ActionResult.FAILURE
            }
        }

        // Open Door
        val targetTileType = worldMap.at(xPos, yPos).get().type
        if (targetTileType === TileType.CLOSED_DOOR) {
            return ActionResult(OpenDoorAction(pawn, Point2i(xPos, yPos)))
        }

//        if (targetTileType === TileType.GLASS) {
//            return ActionResult(ShatterGlassAction(pawn, Point3i(xPos, yPos, zPos)))
//        }

        // See if we can walk there.
        if (!pawn.canTraverse(xCur, yCur, xPos, yPos)) {
            return ActionResult.FAILURE
        }

        // At this point we know that we can walk, lets do it
        pawn.chunk.updatePawnPos(pawn, xPos, yPos)


        // See if the hero stepped on anything interesting that would cause them to react.
//        if (targetTileType === TileType.UP_STAIR) {
//            return ActionResult(true, AscendAction(pawn))
//        }
//        if (targetTileType === TileType.DOWN_STAIR) {
//            return ActionResult(true, DescendAction(pawn))
//        }

        return ActionResult.SUCCESS
    }
}
