package com.stewsters.path.screen

import com.valkryst.VTerminal.Panel
import com.valkryst.VTerminal.builder.component.ButtonBuilder
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.component.Button
import com.valkryst.VTerminal.component.Screen
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class DeathScreen(private val panel: Panel, private val screenBuilder: ScreenBuilder) : Screen(screenBuilder), KeyListener {


    val buttonBackToMain: Button
    val buttonExitGame: Button

    init {
        // Construct menu options:
        val builder = ButtonBuilder()
        builder.text = "Back to Main"
        builder.columnIndex = panel.widthInCharacters / 3
        builder.rowIndex = panel.heightInCharacters / 3
        buttonBackToMain = builder.build()

        builder.text = "Exit"
        builder.rowIndex = builder.rowIndex + 1
        buttonExitGame = builder.build()

        // Swap Screen:
        panel.swapScreen(this)

        buttonBackToMain.setOnClickFunction({
            panel.swapScreen(MainMenuScreen(panel, screenBuilder))
        })

        buttonExitGame.setOnClickFunction({ System.exit(0) })

        // Add components to Screen VIA Panel functions:
        panel.addComponents(buttonBackToMain, buttonExitGame)

    }

    override fun keyPressed(e: KeyEvent?) {

    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun keyTyped(e: KeyEvent?) {
    }

}