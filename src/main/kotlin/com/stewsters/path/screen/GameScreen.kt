package com.stewsters.path.screen

import com.stewsters.path.Game.saveFolder
import com.stewsters.path.action.Action
import com.stewsters.path.action.CloseAdjacentDoorsAction
import com.stewsters.path.action.DismountAction
import com.stewsters.path.action.HarvestAction
import com.stewsters.path.action.MountAction
import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.World
import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.builder.TextAreaBuilder
import com.valkryst.VTerminal.component.TextArea
import kaiju.math.Vec2
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameScreen(private val screen: Screen) : View(screen), KeyListener {

    private val world = World(16, 16, 8, 8)

    private var messageBox: TextArea
    private val worldArea: WorldArea

    init {
        screen.addListener(this)

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

        worldArea = WorldArea(WorldBuilder(
                width = 32,
                height = 32
        ))
        addComponent(worldArea)

        val builder = TextAreaBuilder()
        builder.xPosition = 32
        builder.yPosition = 0
//        builder.columnIndex = 32
//        builder.rowIndex = 0
        builder.width = 32
        builder.height = 10
        builder.isEditable = false
        messageBox = builder.build()
//        messageBox.appendText("Testing")

        addComponent(messageBox)

        display()

    }

    override fun keyTyped(e: KeyEvent) {}

    override fun keyPressed(e: KeyEvent) {
        var action: Action? = null
        when (e.keyCode) {
            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                action = WalkAction(world.player, Vec2[0, -1])
            }
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                action = WalkAction(world.player, Vec2[0, 1])
            }
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                action = WalkAction(world.player, Vec2[-1, 0])
            }
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                action = WalkAction(world.player, Vec2[1, 0])
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
            screen.removeListener(this)
          // todo swap  screen.swapScreen(DeathScreen(panel, screenBuilder))
        }

        display()
        world.player.chunk.writeToDisk(saveFolder)

    }

    override fun keyReleased(e: KeyEvent) {
    }

    private fun display() {
//        setForegroundColor(Color(255, 20, 20, 255))
//        setBackgroundColor(Color(0, 0, 0, 255))

        val map = world.player.chunk

        // Random Characters, Flips, and Underlines:

        for (y in (0 .. worldArea.tiles.height)) {
            for (x in (0..worldArea.tiles.width)) {
                val entities = map.pawnInSquare(x, y)
                if (entities.isNotEmpty()) {

                    val entity = entities.minBy { it.displayOrder }
                    with(worldArea.tiles.getTileAt(x,y)){
                        character = entity?.char ?: '?'
                        foregroundColor = entity?.color ?: Color.WHITE
                        backgroundColor = Color.BLACK
                    }

                } else {
                    val type = map.at(x, y).type
                    with(worldArea.tiles.getTileAt(x,y)){
                        character = type.char
                        foregroundColor = type.foreground
                        backgroundColor = type.background
                    }

//                    character.isHidden = true
                    //TODO: add shaders
//                    character.shadeBackgroundColor(world.player.pos.getChebyshevDistance(x, y).toDouble() / 32)
                }
            }
        }

        screen.draw()

    }

}
