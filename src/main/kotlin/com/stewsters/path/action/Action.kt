package com.stewsters.path.action

import com.stewsters.path.entity.Entity
import com.stewsters.path.map.MapChunk

// An entity does something. verbs
abstract class Action(var pawn: Entity, var worldMap: MapChunk = pawn.chunk) {

    protected fun Action(pawn: Entity) {
        this.pawn = pawn
        this.worldMap = pawn.chunk
    }

    open fun onPerform(): ActionResult? {
        return null
    }

}