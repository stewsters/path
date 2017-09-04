package com.stewsters.path.entity

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