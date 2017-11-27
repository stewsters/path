package krogueutil

import com.stewsters.util.math.Point2i

open class Box(val highX: Int, val highY: Int) {

    fun contains(p: Point2i): Boolean = contains(p.x, p.y)
    fun contains(p: Vec2Immutable): Boolean = contains(p.x, p.y)
    fun contains(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < highX && y < highY

    fun outside(p: Point2i): Boolean = outside(p.x, p.y)
    fun outside(p: Vec2Immutable): Boolean = outside(p.x, p.y)
    fun outside(x: Int, y: Int): Boolean = (x < 0 || y < 0 || x >= highX || y >= highY)

}