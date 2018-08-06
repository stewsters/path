package com.stewsters.path.screen

import com.stewsters.path.Game
import com.valkryst.VTerminal.Screen
import java.awt.event.KeyEvent

interface Veil {

    fun draw(screen: Screen)
    fun keyboard(e: KeyEvent, game: Game)

}