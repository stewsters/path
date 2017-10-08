package veclib

import com.stewsters.util.math.Facing2d
import org.junit.Test

class VecTests {


    @Test
    fun testPoints() {
        for (x in -2..34) {
            for (y in -2..34) {
                Vec2.get(x, y)
            }
        }
    }

    @Test
    fun testMoving() {
        val location = Vec2.get(0, 0)
                .move(Facing2d.EAST)
                .move(Facing2d.NORTH)

        location.x == 1
        location.y == 1
    }
}