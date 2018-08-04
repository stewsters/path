package com.stewsters.path.screen


import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.builder.ButtonBuilder
import com.valkryst.VTerminal.component.Button
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class DeathScreen(private val screen: Screen) : View(screen), KeyListener {


    val buttonBackToMain: Button
    val buttonExitGame: Button

    init {
        // Construct menu options:
        val builder = ButtonBuilder()
        builder.text = "Back to Main"
        builder.xPosition = screen.width / 3
        builder.yPosition = screen.height / 3
        buttonBackToMain = builder.build()

        builder.text = "Exit"
        builder.yPosition = builder.yPosition + 1
        buttonExitGame = builder.build()

        // Swap Screen:
//        panel.swapScreen(this)

        buttonBackToMain.setOnClickFunction({
// TODO           panel.swapScreen(MainMenuScreen(panel, screenBuilder))
        })

        buttonExitGame.setOnClickFunction({ System.exit(0) })

        // Add components to Screen VIA Panel functions:
        screen.addComponent(buttonBackToMain)
        screen.addComponent(buttonExitGame)

    }

    override fun keyPressed(e: KeyEvent?) {

    }

    override fun keyReleased(e: KeyEvent?) {
    }

    override fun keyTyped(e: KeyEvent?) {
    }

}