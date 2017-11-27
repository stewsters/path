package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity

class HarvestAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (pawn.mount != null) {
            //on horse, dismount?
            return ActionResult(alternative = DismountAction(pawn))
        }

        val others = pawn.chunk.pawnInSquare(pawn.pos).filter {
            it != pawn && it.inventory != null
        }

        if (others.isEmpty()) {
            return ActionResult.FAILURE
        }

        val itemsPickedUp: List<Entity>? = others.first().inventory?.items
        others.first().inventory?.items?.clear()

        if (itemsPickedUp != null)
            pawn.inventory?.items?.addAll(itemsPickedUp)


        return ActionResult.SUCCESS
    }
}