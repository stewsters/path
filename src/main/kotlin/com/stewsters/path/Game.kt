package com.stewsters.path

import com.stewsters.path.screen.MainMenuScreen
import com.valkryst.VTerminal.builder.PanelBuilder
import com.valkryst.VTerminal.builder.component.ScreenBuilder
import com.valkryst.VTerminal.font.FontLoader
import java.io.File

object Game {
    val saveFolder = File("saves")

    @JvmStatic
    fun main(args: Array<String>) {
        saveFolder.mkdirs()

        val font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1)


        val panelBuilder = PanelBuilder()
        panelBuilder.font = font
        panelBuilder.widthInCharacters = 80
        panelBuilder.heightInCharacters = 40

        val panel = panelBuilder.build()

        Thread.sleep(50)

        val screenBuilder = ScreenBuilder()
        screenBuilder.width = panelBuilder.widthInCharacters
        screenBuilder.height = panelBuilder.heightInCharacters

        panel.swapScreen(MainMenuScreen(panel, screenBuilder))
        panel.draw()
    }

}