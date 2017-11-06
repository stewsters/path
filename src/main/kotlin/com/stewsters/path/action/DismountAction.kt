package com.stewsters.path.action

import com.stewsters.path.entity.Entity

class DismountAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {
        if (pawn.mount == null) {
            return ActionResult.FAILURE
        }

        //find a clear spot to put it
        val possible = pawn.pos.mooreNeighborhood().find {
            pawn.chunk.contains(it) &&
                    !pawn.chunk.at(it).type.blocks &&
                    pawn.chunk.pawnInSquare(it).none { it.blocks }
        }

        if (possible == null) {
            // Cannot dismount in crowded environments
            return ActionResult.FAILURE
        }

        pawn.mount?.let { mount ->
            mount.pos = possible
            mount.chunk = pawn.chunk
            pawn.chunk.addPawn(mount)
        }
        pawn.mount = null

        return ActionResult.SUCCESS
    }
}