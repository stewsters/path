package com.stewsters.path.screen

import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.World
import com.stewsters.util.math.Point2i
import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.component.Screen
import com.valkryst.VTerminal.printer.RectanglePrinter
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameScreen(var panel: Panel, screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {

    val world = World(16, 16, 8, 8)

    //    var messageBox: TextArea
    val worldArea: WorldArea

    init {
        panel.addKeyListener(this)

        val printer = RectanglePrinter()
        printer.width = 40
        printer.height = 4
        printer.title = "Player"
        printer.print(this, 33, 1)

        printer.width = 40
        printer.height = 4
        printer.title = "Target"
        printer.print(this, 33, 6)

        worldArea = WorldArea(WorldBuilder(
                world = world,
                width = 32,
                height = 32
        ))
        addComponent(worldArea)

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

    override fun keyPressed(e: KeyEvent) {}

    override fun keyReleased(e: KeyEvent) {
        var dx = 0
        var dy = 0

        when (e.keyCode) {
            KeyEvent.VK_UP, KeyEvent.VK_W -> {
                dy = -1
            }
            KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                dy = 1
            }
            KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                dx = -1
            }
            KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                dx = 1
            }
        }

        println("$dx $dy loc")
        world.player.turnTaker?.setNextAction(WalkAction(world.player, Point2i(dx, dy)))
        world.update()

        setForegroundColor(Color(255, 20, 20, 255))
        setBackgroundColor(Color(0, 0, 0, 255))

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
                    val type = map.at(x, y).get().type
                    character.character = type.char
                    character.foregroundColor = type.foreground
                    character.backgroundColor = type.background
                }
            }
        }

        panel.draw()

    }

}
