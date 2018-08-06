package com.stewsters.path

import com.stewsters.path.screen.MainMenuVeil
import com.stewsters.path.screen.Veil
import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.font.FontLoader
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File

object Game : KeyListener {

    val saveFolder = File("saves")
    val screen: Screen = Screen(
            100, 64,
            FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1.0)
    )
    var currentVeil: Veil = MainMenuVeil()


    @JvmStatic
    fun main(args: Array<String>) {

        saveFolder.mkdirs()
        screen.addCanvasToFrame()
        screen.addListener(this)

        currentVeil.draw(screen)
        screen.draw()
    }

    override fun keyPressed(e: KeyEvent) {
        println(e)
        currentVeil.keyboard(e, this)
        currentVeil.draw(screen)
        screen.draw()
    }

    override fun keyReleased(e: KeyEvent) {

    }

    override fun keyTyped(e: KeyEvent) {

    }
}