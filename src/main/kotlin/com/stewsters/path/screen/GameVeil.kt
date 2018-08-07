package com.stewsters.path.screen

import com.stewsters.path.Game
import com.stewsters.path.Game.saveFolder
import com.stewsters.path.action.Action
import com.stewsters.path.action.CloseAdjacentDoorsAction
import com.stewsters.path.action.DismountAction
import com.stewsters.path.action.HarvestAction
import com.stewsters.path.action.MountAction
import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.World
import com.stewsters.path.map.generator.TerrainGenerator
import com.valkryst.VTerminal.Screen
import kaiju.math.Rectangle
import kaiju.math.Vec2
import kaiju.math.Vec3
import java.awt.Color
import java.awt.event.KeyEvent

class GameVeil : Veil {
    init {
    }

    private val world = World(16, 16,1, 8, 8,0)
    private val displayArea = Rectangle(Vec2[32, 1], Vec2[32 + TerrainGenerator.chunkSize - 1, TerrainGenerator.chunkSize])

    override fun keyboard(e: KeyEvent, game: Game) {
        var action: Action? = null
        when (e.keyCode) {
            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                action = WalkAction(world.player, Vec3[0, -1, 0])
            }
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                action = WalkAction(world.player, Vec3[0, 1, 0])
            }
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                action = WalkAction(world.player, Vec3[-1, 0, 0])
            }
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                action = WalkAction(world.player, Vec3[1, 0, 0])
            }
            KeyEvent.VK_C -> {
                action = CloseAdjacentDoorsAction(world.player)
            }
            KeyEvent.VK_H -> {
                action = HarvestAction(world.player)
            }
            KeyEvent.VK_M -> {
                action = MountAction(world.player)
            }
            KeyEvent.VK_U -> {
                action = DismountAction(world.player)
            }
        }
        //  messageBox.appendText(action.toString())

        world.player.turnTaker?.setNextAction(action)
        world.update()
        if (!world.player.isAlive()) {
            game.currentVeil = DeathVeil()
        }

        world.player.chunk.writeToDisk(saveFolder)

    }

    override fun draw(screen: Screen) {

        screen.clear()
        val map = world.player.chunk

        screen.drawString("Name: ${world.player.name}", 0, 1)

        val life = world.player.life
        if (life != null) {
            screen.drawString("Hp: ${life.cur}/${life.max}", 0, 2)
        }
        val mounted = world.player.mount
        if (mounted != null)
            screen.drawString("Mounted on ${mounted.name}", 0, 3)

        // Random Characters, Flips, and Underlines:
        screen.horizontalLine(0, char = ' ', background = Color.GRAY)
        screen.horizontalLine(33, char = ' ', background = Color.GRAY)
        screen.verticalLine(x = 31, y2 = 32, char = ' ', background = Color.GRAY)
        screen.verticalLine(64, y2 = 32, char = ' ', background = Color.GRAY)

        for (sy in (displayArea.lower.y..displayArea.upper.y)) {
            for (sx in (displayArea.lower.x..displayArea.upper.x)) {
                val x = sx - displayArea.lower.x
                val y = sy - displayArea.lower.y
                val z = world.player.pos.z

                val entities = map.pawnInSquare(x, y, z)
                if (entities.isNotEmpty()) { // Render that entity
                    val entity = entities.minBy { it.displayOrder }
                    with(screen.getTileAt(sx, sy)) {
                        character = entity?.char ?: '?'
                        foregroundColor = entity?.color ?: Color.WHITE
                        backgroundColor = Color.BLACK
                    }

                } else { // render ground
                    val type = map.at(x, y, z).type
                    with(screen.getTileAt(sx, sy)) {
                        character = type.char
                        foregroundColor = type.foreground
                        backgroundColor = type.background
                    }

                }
            }
        }

        Game.screen.draw()
    }


//        screen.addListener(this)

//        val printer = RectanglePrinter()
//        printer.width = 40
//        printer.height = 4
//        printer.title = "Player"
//        printer.print(this, 33, 1)
//
//        printer.width = 40
//        printer.height = 4
//        printer.title = "Target"
//        printer.print(this, 33, 6)
//        addComponent(worldArea)

//        val builder = TextAreaBuilder()
//        builder.xPosition = 32
//        builder.yPosition = 0
////        builder.columnIndex = 32
////        builder.rowIndex = 0
//        builder.width = 32
//        builder.height = 10
//        builder.isEditable = false
//        messageBox = builder.build()
//        messageBox.appendText("Testing")


}

