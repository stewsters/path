package com.stewsters.path.screen


import com.stewsters.path.Game
import com.valkryst.VTerminal.component.VPanel

import java.awt.event.KeyEvent

class DeathVeil : Veil {

    override fun draw(screen: VPanel) {
        screen.clear()
        screen.drawString("You have died", 10, 10)

        screen.drawString("[Space] Back To Main", 10, 20)
    }

    override fun keyboard(e: KeyEvent, game: Game) {
        game.currentVeil = MainMenuVeil()
    }

}

