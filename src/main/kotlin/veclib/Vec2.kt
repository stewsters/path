package veclib

import com.stewsters.util.math.Facing2d

data class Vec2(override val x: Int, override val y: Int) : Vec2Immutable {

    operator fun plus(dir: Facing2d): Vec2 = get(x + dir.x, y + dir.y)

    operator fun plus(dir: Vec2): Vec2 = get(x + dir.x, y + dir.y)

    operator fun minus(dir: Facing2d): Vec2 = get(x - dir.x, y - dir.y)

    operator fun minus(dir: Vec2): Vec2 = get(x - dir.x, y - dir.y)

    companion object {
        private val size: Int = 32
        private val pool = Array(size * size, { i -> Vec2(i % size, i / size) })

        fun get(x: Int, y: Int): Vec2 {
            return if (x >= 0 && x < size && y >= 0 && y < size) {
                pool[size * y + x]
            } else {
                // return a generated one
                println("new $x $y")
                Vec2(x, y) // maybe cache in hashtable?
            }
        }
    }

    fun mooreNeighborhood(): List<Vec2> = List(8, { index ->
        if (index >= 5)
            Vec2.get((index + 1) % 3 - 1, (index + 1) / 3)
        else
            Vec2.get(index % 3 - 1, index / 3)
    })

    override fun toString(): String {
        return "($x, $y)"
    }
}

fun getChebyshevDistance(pos1: Vec2, pos2: Vec2): Int = getChebyshevDistance(pos1.x, pos1.y, pos2.x, pos2.y)

fun getChebyshevDistance(x1: Int, y1: Int, x2: Int, y2: Int): Int = Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2))
