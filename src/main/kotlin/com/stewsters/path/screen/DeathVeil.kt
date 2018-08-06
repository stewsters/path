package com.stewsters.path.screen


import com.stewsters.path.Game
import com.valkryst.VTerminal.Screen
import java.awt.event.KeyEvent

class DeathVeil : Veil {

    override fun draw(screen: Screen) {
        screen.clear()
        screen.drawString("You have died", 10, 10)

        screen.drawString("[Space] Back To Main", 10, 20)
    }

    override fun keyboard(e: KeyEvent, game: Game) {
        game.currentVeil = MainMenuVeil()
    }

//    val buttonBackToMain: Button
//    val buttonExitGame: Button

    init {
//        // Construct menu options:
//        val builder = ButtonBuilder()
//        builder.text = "Back to Main"
//        builder.xPosition = Game.screen.width / 3
//        builder.yPosition = Game.screen.height / 3
//        buttonBackToMain = builder.build()
//
//        builder.text = "Exit"
//        builder.yPosition = builder.yPosition + 1
//        buttonExitGame = builder.build()
//
//        // Swap Screen:
////        panel.swapScreen(this)
//
//        buttonBackToMain.setOnClickFunction({
//            // TODO           panel.swapScreen(MainMenuScreen(panel, screenBuilder))
//        })
//
//        buttonExitGame.setOnClickFunction({ System.exit(0) })
//
//        // Add components to Screen VIA Panel functions:
//        Game.screen.addComponent(buttonBackToMain)
//        Game.screen.addComponent(buttonExitGame)

    }

}

