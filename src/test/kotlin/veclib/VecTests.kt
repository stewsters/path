package veclib

import com.stewsters.util.math.Facing2d
import org.junit.Test
import java.util.*

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

    @Test
    fun testNeighborhood() {

        val center = Vec2.get(5, 5)
        val neighbors = center.mooreNeighborhood()

        assert(neighbors.size == 8)

        neighbors.forEach { println(it) }

        assert(neighbors.containsAll(Arrays.asList(
                Vec2.get(4, 4),
                Vec2.get(5, 4),
                Vec2.get(6, 4),
                Vec2.get(4, 5),
                Vec2.get(6, 5),
                Vec2.get(4, 6),
                Vec2.get(5, 6),
                Vec2.get(6, 6)
        ))
        )
    }
}