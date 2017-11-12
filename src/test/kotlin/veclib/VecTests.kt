package veclib

import com.stewsters.util.math.Facing2d
import org.junit.Test

class VecTests {


    @Test
    fun testPoints() {
        for (x in -2..34) {
            for (y in -2..34) {
                val vec2 = Vec2.get(x, y)

                if (vec2.x != x && vec2.y != y) {
                    println("$x:${vec2.x} $y:${vec2.y}")
                    assert(false)
                }
            }
        }
    }

    @Test
    fun testMoving() {
        val location = Vec2.get(0, 0) + Facing2d.EAST + Facing2d.NORTH

        assert(location.x == 1)
        assert(location.y == 1)
    }
}