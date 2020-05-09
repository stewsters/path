package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity

class PickupItemAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (pawn.inventory == null)
            return ActionResult.FAILURE

        val items = chunkMap.pawnInSquare(pawn.pos).filter { it.item != null }

        if (items.isEmpty())
            return ActionResult.FAILURE

        for (item in items) {
            chunkMap.removePawn(item)
            pawn.inventory?.items?.add(item)
        }

        //TODO: Auto Equip?

        return ActionResult.SUCCESS

    }
}