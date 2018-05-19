package com.stewsters.path.screen

import com.stewsters.path.Game.saveFolder
import com.stewsters.path.action.Action
import com.stewsters.path.action.CloseAdjacentDoorsAction
import com.stewsters.path.action.DismountAction
import com.stewsters.path.action.HarvestAction
import com.stewsters.path.action.MountAction
import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.World
import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.builder.component.TextAreaBuilder
import com.valkryst.VTerminal.component.Screen
import com.valkryst.VTerminal.component.TextArea
import kaiju.math.Vec2
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameScreen(private val panel: Panel, private val screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {

    private val world = World(16, 16, 8, 8)

    private var messageBox: TextArea
    private val worldArea: WorldArea

    init {
        panel.addKeyListener(this)

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
        builder.columnIndex = 32
        builder.rowIndex = 0
        builder.width = 32
        builder.height = 10
        builder.isEditable = false
        messageBox = builder.build()
        messageBox.appendText("Testing")

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
        messageBox.appendText(action.toString())

        world.player.turnTaker?.setNextAction(action)
        world.update()
        if (!world.player.isAlive()) {
            panel.removeListener(this)
            panel.swapScreen(DeathScreen(panel, screenBuilder))
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
        for ((y, row) in worldArea.strings.withIndex()) {
            for ((x, character) in row.characters.withIndex()) {
                val entities = map.pawnInSquare(x, y)
                if (entities.isNotEmpty()) {

                    val entity = entities.minBy { it.displayOrder }
                    character.character = entity?.char ?: '?'
                    character.foregroundColor = entity?.color ?: Color.WHITE
                    character.backgroundColor = Color.BLACK
                } else {
                    val type = map.at(x, y).type
                    character.character = type.char
                    character.foregroundColor = type.foreground
                    character.backgroundColor = type.background

//                    character.isHidden = true
                    //TODO: add shaders
//                    character.shadeBackgroundColor(world.player.pos.getChebyshevDistance(x, y).toDouble() / 32)
                }
            }
        }

        panel.draw()

    }

}
