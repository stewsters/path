package com.stewsters.path.screen


import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.builder.ButtonBuilder

import com.valkryst.VTerminal.component.Button
import java.awt.event.KeyEvent
import java.awt.event.KeyListener

class MainMenuScreen(val screen:Screen) : View(screen), KeyListener {
    val buttonNewGame: Button
    val buttonExitGame: Button

    init {

        screen.addListener(this)
//        panel.addKeyListener(this)
        // Construct menu options:
        val builder = ButtonBuilder()
        builder.text = "New Game"
        builder.xPosition = screen.width / 3
        builder.yPosition = screen.height / 3
        buttonNewGame = builder.build()

        builder.text = "Exit"
        builder.yPosition = builder.yPosition + 1
        buttonExitGame = builder.build()

        // Swap Screen:
//        screen.swapScreen(this)

        buttonNewGame.setOnClickFunction({

            screen.addComponent(GameScreen(screen))
        })
        buttonExitGame.setOnClickFunction({ System.exit(0) })

        // Add components to Screen VIA Panel functions:
        screen.addComponent(buttonNewGame)
        screen.addComponent( buttonExitGame)

    }

    override fun keyTyped(e: KeyEvent) {
    }

    override fun keyPressed(e: KeyEvent) {
        when (e.keyCode) {
            KeyEvent.VK_N, KeyEvent.VK_SPACE -> {
                screen.removeListener(this)
                // todo: swap panel.swapScreen(GameScreen(panel, screenBuilder))
            }
            KeyEvent.VK_X -> {
                screen.removeListener(this)
                System.exit(0)
            }
        }
    }

    override fun keyReleased(e: KeyEvent) {

    }

}
