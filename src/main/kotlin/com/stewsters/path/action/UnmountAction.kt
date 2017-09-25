package com.stewsters.path.action

import com.stewsters.path.entity.Entity

class UnmountAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {
        if(pawn.mount==null)
            return ActionResult.FAILURE

        pawn.mount==null

        return ActionResult.SUCCESS
    }
}