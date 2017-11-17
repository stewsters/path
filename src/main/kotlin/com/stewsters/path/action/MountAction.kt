package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity

class MountAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (pawn.mount != null)
            return ActionResult.FAILURE

        // find all mountable creatures at range 1, choose best.
        val entities: List<Entity> = chunkMap.pawnInSquare(pawn.pos.x - 1, pawn.pos.y - 1, pawn.pos.x + 1, pawn.pos.y + 1)
        println(entities)

        val mount = entities.firstOrNull { it.mountable }
        println(mount)

        if (mount == null) {
            return ActionResult.FAILURE
        }

        pawn.mount = mount

        pawn.chunk.removePawn(mount)

        println("${pawn.name} mounts ${pawn.mount?.name}")

        // add it to entity's mount slot

        return ActionResult.SUCCESS
    }
}