package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.TileType
import krogueutil.Vec2

class WalkAction(pawn: Entity, private val offset: Vec2) : Action(pawn) {


    override fun onPerform(): ActionResult {

        if (offset.x == 0 && offset.y == 0) {
            return ActionResult(alternative = RestAction(pawn))
        }

        val curPos = pawn.pos
        val nextPos = curPos + offset

        if (chunkMap.outside(nextPos)) {
            if (pawn.chunk.world.player == pawn) {
                return ActionResult(alternative = MapTransitionAction(pawn, offset))
            } else {

                return ActionResult.FAILURE
            }
        }

        //See if there is an actor there
        val targetList = chunkMap.pawnInSquare(
                nextPos.x, nextPos.y,
                nextPos.x + pawn.xSize - 1, nextPos.y + pawn.ySize - 1)

        if (targetList.isNotEmpty()) {

            for (target in targetList) {
                if (target == pawn) {
                    continue
                } else if ((pawn.faction == target.faction) && target.mountable && pawn.mount == null) {
                    return ActionResult(alternative = MountAction(pawn))
                } else if ((pawn.faction != target.faction) && target.isAlive())
                    return ActionResult(alternative = AttackAction(pawn, target))
                else if (pawn.blocks) {
                    // I hope this doesnt happen
                    return ActionResult.FAILURE
                }
            }
        }

        // Open Door
        val targetTileType = chunkMap.at(nextPos).type
        if (targetTileType === TileType.CLOSED_DOOR) {
            return ActionResult(alternative = OpenDoorAction(pawn, nextPos))
        }

//        if (targetTileType === TileType.GLASS) {
//            return ActionResult(ShatterGlassAction(pawn, Point3i(xPos, yPos, zPos)))
//        }

        // See if we can walk there.
        if (!pawn.canTraverse(curPos.x, curPos.y, nextPos.x, nextPos.y)) {
            return ActionResult.FAILURE
        }

//        println("move from ${pawn.globalX()},${pawn.globalY()}")

        // At this point we know that we can walk, lets do it
        pawn.chunk.updatePawnPos(pawn, nextPos.x, nextPos.y)

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
