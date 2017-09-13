package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import com.stewsters.path.map.TileType
import com.stewsters.util.math.Point2i


class OpenDoorAction(pawn: Entity, private val pos: Point2i) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE
        }
        if (chunkMap.at(pos.x, pos.y).get().type !== TileType.CLOSED_DOOR) {
            return ActionResult.FAILURE
        }

        chunkMap.at(pos.x, pos.y).get().type = TileType.OPEN_DOOR

        return ActionResult.SUCCESS

    }
}