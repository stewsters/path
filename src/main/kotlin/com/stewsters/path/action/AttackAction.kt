package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity

class AttackAction(pawn: Entity, private var target: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {

        val targetLife = target.life

        if (targetLife == null) {
            return ActionResult.FAILURE
        }

        targetLife.damage(1)

        if (targetLife.cur <= 0) {
            target.deathFunction(target)
        }

        return ActionResult.SUCCESS
    }

}
