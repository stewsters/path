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

        // Render loop:
        //while (true) {
        //  panel.screen.update()
        panel.draw()
        //    Thread.sleep(100)
        //}

//        for (y in 0..panel.getHeightInCharacters() - 1) {
//            val string = panel.getScreen().getString(y)
//
//            for (x in 0..panel.getWidthInCharacters() - 1) {
//
//                chunk.at(x, y).ifPresent {
//                    string.setForegroundColor(it.type.foreground)
//                    string.setBackgroundColor(it.type.background)
//                    string.setCharacter(x, it.type.char)
//
//                }
//
//            }
//        }
//
        //panel.draw()
    }

}