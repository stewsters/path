package krogueutil.two

import com.stewsters.util.math.Point2i

open class Rect {

    private val lowX: Int
    private val lowY: Int
    private val highX: Int
    private val highY: Int

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

    constructor(one: Vec2Immutable, two: Vec2Immutable) {
        lowX = Integer.min(one.x, two.x)
        lowY = Integer.min(one.y, two.y)
        highX = Integer.max(one.x, two.x)
        highY = Integer.max(one.y, two.y)
    }

    fun contains(p: Point2i): Boolean = contains(p.x, p.y)
    fun contains(p: Vec2Immutable): Boolean = contains(p.x, p.y)
    fun contains(x: Int, y: Int): Boolean = x >= lowX && y >= lowY && x <= highX && y <= highY


    fun outside(p: Point2i): Boolean = outside(p.x, p.y)
    fun outside(p: Vec2Immutable): Boolean = outside(p.x, p.y)
    fun outside(x: Int, y: Int): Boolean = (x < lowX || y < lowY || x > highX || y > highY)

}