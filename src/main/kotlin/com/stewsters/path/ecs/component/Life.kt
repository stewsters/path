package com.stewsters.path.ecs.component

import kotlin.math.max
import kotlin.math.min

// Damage we can take before dying
data class Life(var max: Int, var cur: Int = max) {
    fun damage(i: Int) {
        cur = max(0, cur - i)
    }

    fun heal(i: Int) {
        cur = min(cur + i, max)
    }

    fun isAlive(): Boolean = cur > 0

}