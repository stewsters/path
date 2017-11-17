package com.stewsters.path.ecs.component

import com.stewsters.path.action.Action
import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.map.MapChunk

// This controls Entities that do stuff on turns
data class TurnTaker(var gameTurn: Int,
                     var ai: (MapChunk, Entity) -> Action?,
                     var parent: Entity? = null) : Comparable<TurnTaker> {

    private var action: Action? = null

    fun nextAction(mapChunk: MapChunk, entity: Entity): Action? {
        // if we have a planned action, do that, otherwise query AI
        return action ?: ai(mapChunk, entity)
    }

    fun setNextAction(action: Action?) {
        this.action = action
    }

    override fun compareTo(other: TurnTaker): Int {
        return Integer.compare(gameTurn, other.gameTurn)
    }

}