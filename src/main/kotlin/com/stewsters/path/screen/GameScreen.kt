package com.stewsters.path.screen

import com.stewsters.path.action.*
import com.stewsters.path.map.World
import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.component.Screen
import veclib.Vec2
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameScreen(var panel: Panel, screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {

    val world = World(16, 16, 8, 8)

    //    var messageBox: TextArea
    val worldArea: WorldArea

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

        display()
//        val builder = TextAreaBuilder()
//        builder.radio = panel.radio
//        builder.columnIndex = 0
//        builder.rowIndex = 30
//        builder.width = 80
//        builder.height = 10
//        builder.isEditable = false
//        messageBox = builder.build()
//
//        addComponent(messageBox)
    }

    override fun keyTyped(e: KeyEvent) {}

    override fun keyPressed(e: KeyEvent) {
        var action: Action? = null
        when (e.keyCode) {
            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                action = WalkAction(world.player, Vec2.get(0, -1))
            }
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                action = WalkAction(world.player, Vec2.get(0, 1))
            }
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                action = WalkAction(world.player, Vec2.get(-1, 0))
            }
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                action = WalkAction(world.player, Vec2.get(1, 0))
            }
            KeyEvent.VK_C -> {
                action = CloseAdjacentDoors(world.player)
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

        world.player.turnTaker?.setNextAction(action)
        world.update()

        display()

    }

    override fun keyReleased(e: KeyEvent) {
    }

    private fun display() {
//        setForegroundColor(Color(255, 20, 20, 255))
//        setBackgroundColor(Color(0, 0, 0, 255))

        val map = world.getCurrentMap()

        // Random Characters, Flips, and Underlines:
        for ((y, row) in worldArea.strings.withIndex()) {
            for ((x, character) in row.characters.withIndex()) {
                val entities = map.pawnInSquare(x, y)
                if (entities.size > 0) {
                    character.character = entities.first().char
                    character.foregroundColor = entities.first().color
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
