package com.stewsters.path.action

import com.stewsters.path.entity.Entity

class HarvestAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (pawn.mount != null) {
            //on horse, dismount?
            return ActionResult(DismountAction(pawn))
        }

        var others = pawn.chunk.pawnInSquare(pawn.pos).filter {
            it != pawn && it.inventory != null
        }

        if (others.size == 0) {
            return ActionResult.FAILURE
        }

        val itemsPickedUp: List<Entity>? = others.first().inventory?.items
        others.first().inventory?.items?.clear()

        if (itemsPickedUp != null)
            pawn.inventory?.items?.addAll(itemsPickedUp)


        return ActionResult.SUCCESS
    }
}