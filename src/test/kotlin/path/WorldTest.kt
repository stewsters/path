package path

import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.TileType
import com.stewsters.path.map.World
import kaiju.math.Vec3
import org.junit.Test
import java.io.File

class WorldTest {

    @Test
    fun walkThroughWorldTest() {

        val world = World(Vec3(8, 8, 1), Vec3(4, 4, 1), skip = true)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))

        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)

        for (i in 1..10) {
            world.update()
        }

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(0, 1, 0)))

        for (i in 1..10) {
            world.update()
        }
        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 17)

    }


    @Test
    fun walkIntoWallsTest() {
        val world = World(Vec3(1, 1, 1), Vec3(0, 0, 0))

        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)

        world.player.chunk.at(18, 16, 0).type = TileType.WALL

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        println("Walking into wall")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        println("Done with that")
    }

    @Test
    fun walkThroughDoorTest() {
        val world = World(Vec3(1, 1, 1), Vec3(0, 0, 0))
        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)


        world.player.chunk.at(17, 16, 0).type = TileType.CLOSED_DOOR

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))
        world.update()

        assert(world.player.chunk.at(17, 16, 0).type == TileType.OPEN_DOOR)

        println("Continue walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))
        world.update()

        assert(world.player.pos == Vec3(17, 16, 0))
    }

    @Test
    fun testAreaTransition() {
        val world = World(Vec3(8, 8, 1), Vec3(2, 2, 0), skip = true)
        assert(world.player.chunk.pos == Vec3(2, 2, 0))

        for (x in (16..32)) {
            world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec3(1, 0, 0)))
            world.update()
        }
        assert(world.player.chunk.pos == Vec3(3, 2, 0))
        assert(world.player.pos == Vec3(0, 16, 0))
    }


    @Test
    fun indexTest() {
        val world = World(Vec3(8, 8, 1), Vec3(2, 2, 0))

        for (x in (0..7)) {
            for (y in (0..7)) {
                val chunk = world.getMapAt(x, y, 0)
                assert(chunk.pos == Vec3(x, y, 0))
            }
        }

    }

    @Test
    fun saveWorld() {

        val world = World(Vec3(8, 8, 1), Vec3(2, 2, 0))
        val saveDir = File.createTempFile("pathTest-", "")
        world.saveGame(saveDir)

    }
}