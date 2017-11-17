package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.MapChunk

// An entity does something. verbs
abstract class Action(var pawn: Entity,
                      var chunkMap: MapChunk = pawn.chunk,
                      var costInTurns: Int = 100) {

    open fun onPerform(): ActionResult {
        return ActionResult.FAILURE
    }

    override fun toString(): String {
        return javaClass.name.split('.').last()
    }

}