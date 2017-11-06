package veclib

import com.stewsters.util.math.Facing2d

data class Vec2(override val x: Int, override val y: Int) : Vec2Immutable {

    fun move(dir: Facing2d): Vec2 {
        return get(x + dir.x, y + dir.y)
    }

    companion object {
        private val size: Int = 32
        private val pool = Array<Vec2>(size * size, { i -> Vec2(i % size, i / size) })

        fun get(x: Int, y: Int): Vec2 {
            if (x >= 0 && x < size && y >= 0 && y < size) {
                // return a static one
                println("pool $x $y")
                return pool[size * x + y]
            } else {
                // return a generated one
                println("new $x $y")
                return Vec2(x, y); // maybe cache in hashtable?
            }

        }
    }
}