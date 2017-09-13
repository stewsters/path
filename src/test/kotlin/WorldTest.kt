import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.TileType
import com.stewsters.path.map.World
import com.stewsters.util.math.Point2i
import org.junit.Test

class WorldTest {

    @Test
    fun walkThroughWorldTest() {

        val world = World(8, 8, 4, 4)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(1, 0)))

        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)

        for (i in 1..10) {
            world.update()
        }

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(0, 1)))

        for (i in 1..10) {
            world.update()
        }
        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 17)

    }


    @Test
    fun walkIntoWallsTest() {
        val world = World(1, 1, 0, 0)

        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)

        world.getCurrentMap().at(18, 16).get().type = TileType.WALL

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(1, 0)))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        println("Walking into wall")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(1, 0)))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        println("Done with that")
    }

    @Test
    fun walkThroughDoorTest() {
        val world = World(1, 1, 0, 0)
        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)


        world.getCurrentMap().at(17, 16).get().type = TileType.CLOSED_DOOR

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(1, 0)))
        world.update()

        assert(world.getCurrentMap().at(17, 16).get().type == TileType.OPEN_DOOR)

        println("Continue walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(1, 0)))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

    }

    @Test
    fun indexTest() {
        val world = World(8, 8, 2, 2)

        for (x in (0..7)) {
            for (y in (0..7)) {
                val chunk = world.getMapAt(x, y)
                assert(chunk.x == x)
                assert(chunk.y == y)
            }
        }

    }


}