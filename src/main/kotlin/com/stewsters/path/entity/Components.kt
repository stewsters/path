package com.stewsters.path.entity

import com.stewsters.path.action.Action
import com.stewsters.path.map.MapChunk

// Damage we can take before dying
data class Life(var max: Int, var cur: Int = max) {
    fun damage(i: Int) {
        cur = Math.max(0, cur - i)
    }

    fun heal(i: Int) {
        cur = Math.min(cur + i, max)
    }
}

// Bag of items
data class Inventory(val items: List<Entity>)

// This controls Entities that do stuff on turns
data class TurnTaker(var gameTurn: Int,
                     var ai: (MapChunk, Entity) -> Action?,
                     var parent: Entity? = null) {

    private var action: Action? = null

    fun nextAction(mapChunk: MapChunk, entity: Entity): Action? {
        // if we have a planned action, do that, otherwise query AI
        return action ?: ai(mapChunk, entity)
    }

    fun setNextAction(action: Action?) {
        this.action = action
    }

}