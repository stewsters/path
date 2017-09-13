package com.stewsters.path.map

open class Box(val xSize: Int, val ySize: Int) {

    fun contains(x: Int, y: Int): Boolean {
        return x > 0 && y > 0 && x < xSize && y < ySize
    }

    fun outside(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= xSize || y >= ySize)
    }
}