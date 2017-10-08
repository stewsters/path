package com.stewsters.path.map

import com.stewsters.util.math.Point2i

open class Box(val xSize: Int, val ySize: Int) {

    fun contains(p: Point2i): Boolean = contains(p.x, p.y)
    fun contains(x: Int, y: Int): Boolean {
        return x > 0 && y > 0 && x < xSize && y < ySize
    }

    fun outside(p: Point2i): Boolean = outside(p.x, p.y)
    fun outside(x: Int, y: Int): Boolean {
        return (x < 0 || y < 0 || x >= xSize || y >= ySize)
    }


}