package com.stewsters.path.ecs.component

// Damage we can take before dying
data class Life(var max: Int, var cur: Int = max) {
    fun damage(i: Int) {
        cur = Math.max(0, cur - i)
    }

    fun heal(i: Int) {
        cur = Math.min(cur + i, max)
    }

    fun isAlive(): Boolean = cur > 0

}