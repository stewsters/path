package com.stewsters.path.entity

data class Item(
        val weight: Int = 0,
        val weapon: Weapon? = null,
        val armor: Armor? = null,
        val equipment: Equipment? = null
)


data class Weapon(val damage: Int)
data class Armor(val armor: Int)
data class Equipment(val slot: Slot, var isEquipped: Boolean = false)

enum class Slot {
    WEAPON,
    CLOAK,
}