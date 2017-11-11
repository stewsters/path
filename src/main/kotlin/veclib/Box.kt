package veclib

import com.stewsters.util.math.Point2i

open class Box {

    val lowX: Int
    val lowY: Int
    val highX: Int
    val highY: Int

    constructor(xHigh: Int, yHigh: Int) {
        lowX = 0
        lowY = 0
        highX = xHigh
        highY = yHigh
    }

    constructor(xLow: Int, yLow: Int, xHigh: Int, yHigh: Int) {
        lowX = xLow
        lowY = yLow
        highX = xHigh
        highY = yHigh
    }

    fun contains(p: Point2i): Boolean = contains(p.x, p.y)
    fun contains(p: Vec2Immutable): Boolean = contains(p.x, p.y)
    fun contains(x: Int, y: Int): Boolean = x >= lowX && y >= lowY && x <= highX && y <= highY


    fun outside(p: Point2i): Boolean = outside(p.x, p.y)
    fun outside(p: Vec2Immutable): Boolean = outside(p.x, p.y)
    fun outside(x: Int, y: Int): Boolean = (x < lowX || y < lowY || x > highX || y > highY)

}