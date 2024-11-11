package com.stewsters.path.screen

import com.stewsters.path.Game
import com.valkryst.VTerminal.component.VPanel
import java.awt.event.KeyEvent

interface Veil {

    fun draw(screen: VPanel)
    fun keyboard(e: KeyEvent, game: Game)

}