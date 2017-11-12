package com.stewsters.path.screen

import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ButtonBuilder
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.component.Button
import com.valkryst.VTerminal.component.Screen
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class MainMenuScreen(val panel: Panel, val screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {
    val buttonNewGame: Button
    val buttonExitGame: Button

    init {

        panel.addKeyListener(this)
        // Construct menu options:
        val builder = ButtonBuilder()
        builder.text = "New Game"
        builder.columnIndex = panel.widthInCharacters / 3
        builder.rowIndex = panel.heightInCharacters / 3
        buttonNewGame = builder.build()

        builder.text = "Exit"
        builder.rowIndex = builder.rowIndex + 1
        buttonExitGame = builder.build()

        // Swap Screen:
        panel.swapScreen(this)

        buttonNewGame.setOnClickFunction({
            panel.swapScreen(GameScreen(panel, screenBuilder))
        })
        buttonExitGame.setOnClickFunction({ System.exit(0) })

        // Add components to Screen VIA Panel functions:
        panel.addComponents(buttonNewGame, buttonExitGame)

    }

    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_N, KeyEvent.VK_SPACE -> {
                panel.removeListener(this)
                panel.swapScreen(GameScreen(panel, screenBuilder))
            }
            KeyEvent.VK_X -> {
                panel.removeListener(this)
                System.exit(0)
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {

    }

}
