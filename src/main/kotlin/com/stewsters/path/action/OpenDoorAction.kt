package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.TileType
import veclib.Vec2


class OpenDoorAction(pawn: Entity, private val pos: Vec2) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (!pawn.doorOpener) {
            return ActionResult.FAILURE
        }
        if (chunkMap.at(pos).type !== TileType.CLOSED_DOOR) {
            return ActionResult.FAILURE
        }

        chunkMap.at(pos).type = TileType.OPEN_DOOR

        return ActionResult.SUCCESS

    }
}