package path

import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.TileType
import com.stewsters.path.map.World
import org.junit.Test
import krogueutil.two.Vec2
import java.io.File

class WorldTest {

    @Test
    fun walkThroughWorldTest() {

        val world = World(xSize = 8, ySize = 8, xFocus = 4, yFocus = 4, skip = true)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))

        assert(world.player.pos.x == 16)
        assert(world.player.pos.y == 16)

        for (i in 1..10) {
            world.update()
        }

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[0, 1]))

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

        world.player.chunk.at(18, 16).type = TileType.WALL

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))
        world.update()

        assert(world.player.pos.x == 17)
        assert(world.player.pos.y == 16)

        println("Walking into wall")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))
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


        world.player.chunk.at(17, 16).type = TileType.CLOSED_DOOR

        println("Start walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))
        world.update()

        assert(world.player.chunk.at(17, 16).type == TileType.OPEN_DOOR)

        println("Continue walking")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))
        world.update()

        assert(world.player.pos == Vec2[17, 16])
    }

    @Test
    fun testAreaTransition() {
        val world = World(xSize = 8, ySize = 8, xFocus = 2, yFocus = 2, skip = true)
        assert(world.player.chunk.pos == Vec2[2, 2])

        for (x in (16..32)) {
            world.player.turnTaker?.setNextAction(WalkAction(world.player, Vec2[1, 0]))
            world.update()
        }
        assert(world.player.chunk.pos == Vec2[3, 2])
        assert(world.player.pos == Vec2[0, 16])
    }


    @Test
    fun indexTest() {
        val world = World(8, 8, 2, 2)

        for (x in (0..7)) {
            for (y in (0..7)) {
                val chunk = world.getMapAt(x, y)
                assert(chunk.pos == Vec2[x, y])
            }
        }

    }

    @Test
    fun saveWorld() {

        val world = World(8, 8, 2, 2)
        val saveDir = File.createTempFile("pathTest-", "")

        world.saveGame(saveDir)

    }
}