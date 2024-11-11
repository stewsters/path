package com.stewsters.path.screen


import com.stewsters.path.Game
import com.valkryst.VTerminal.component.VPanel
import java.awt.event.KeyEvent
import kotlin.system.exitProcess

class MainMenuVeil : Veil {

//    val buttonNewGame: Button
//    val buttonExitGame: Button

//    init {
//
//        // Construct menu options:
////        val builder = ButtonBuilder()
////        builder.text = "New Game"
////        builder.xPosition = Game.screen.width / 3
////        builder.yPosition = Game.screen.height / 3
////        buttonNewGame = builder.build()
////
////        builder.text = "Exit"
////        builder.yPosition = builder.yPosition + 1
////        buttonExitGame = builder.build()
//
//        // Swap Screen:
////        screen.swapScreen(this)
//
////        buttonNewGame.setOnClickFunction({
////            screen.addComponent(GameScreen(screen))
////        })
////        buttonExitGame.setOnClickFunction({ System.exit(0) })
////
////        // Add components to Screen VIA Panel functions:
////        screen.addComponent(buttonNewGame)
////        screen.addComponent( buttonExitGame)
//
//    }

    override fun draw(screen: VPanel) {
        screen.clear()
        screen.drawString("Space - New Game", 10, 10)
        screen.drawString("x - Exit", 10, 20)

    }

    override fun keyboard(e: KeyEvent, game: Game) {
        when (e.keyCode) {
            KeyEvent.VK_N, KeyEvent.VK_SPACE -> {
                game.currentVeil = GameVeil()
            }

            KeyEvent.VK_X -> {
                exitProcess(0)
            }
        }
    }

}
