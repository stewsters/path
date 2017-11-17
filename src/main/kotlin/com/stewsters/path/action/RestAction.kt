package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity

class RestAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {
        if (pawn.life != null) {
            pawn.life?.heal(1)
        }
        return ActionResult.SUCCESS
    }
}
