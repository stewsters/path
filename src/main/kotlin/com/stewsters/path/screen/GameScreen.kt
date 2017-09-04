package com.stewsters.path.screen

import com.stewsters.path.action.WalkAction
import com.stewsters.path.map.World
import com.stewsters.util.math.Point2i
import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.builder.component.TextAreaBuilder
import com.valkryst.VTerminal.component.Screen
import com.valkryst.VTerminal.component.TextArea
import com.valkryst.VTerminal.printer.RectanglePrinter
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class GameScreen(panel: Panel, screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {

    val map = World(16, 16, 8, 8)

    var messageBox: TextArea

    init {
        panel.addKeyListener(this)

        val printer = RectanglePrinter()
        printer.width = 40
        printer.height = 4
        printer.title = "Player"
        printer.print(this, 0, 2)

        printer.width = 40
        printer.height = 4
        printer.title = "Target"
        printer.print(this, 0, 6)


        val builder = TextAreaBuilder()
        builder.radio = panel.radio
        builder.columnIndex = 0
        builder.rowIndex = 30
        builder.width = 80
        builder.height = 10
        builder.isEditable = false
        messageBox = builder.build()

        addComponent(messageBox)
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
        map.player.nextAction = WalkAction(map.player, Point2i(dx, dy))
    }


}
