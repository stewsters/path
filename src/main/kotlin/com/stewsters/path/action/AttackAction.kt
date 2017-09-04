package com.stewsters.path.action;

import com.stewsters.path.entity.Entity

class AttackAction(pawn: Entity, internal var target: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        if (target.life == null) {
            return ActionResult.FAILURE
        }

        target.life?.damage(1)

        if (target.life?.cur ?: 0 <= 0) {
            target.deathFunction(target)
        }

        return ActionResult.SUCCESS
    }

}
