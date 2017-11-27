package com.stewsters.path.ecs.component

import com.stewsters.path.ecs.enums.Slot

data class Equipment(val slot: Slot, var isEquipped: Boolean = false)
