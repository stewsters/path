package com.stewsters.path.screen


import com.stewsters.path.Game
import com.valkryst.VTerminal.component.VPanel
import java.awt.event.KeyEvent
import kotlin.system.exitProcess

class MainMenuVeil : Veil {

    override fun draw(screen: VPanel) {
        screen.clear()
        screen.drawString("Path", 10, 5)
        screen.drawString("Space - New Game", 10, 15)
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
