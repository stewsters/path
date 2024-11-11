package com.stewsters.path

//import com.valkryst.VTerminal.Screen
//import com.valkryst.VTerminal.font.FontLoader
import com.stewsters.path.screen.MainMenuVeil
import com.stewsters.path.screen.Veil
import com.valkryst.VTerminal.component.VFrame
import com.valkryst.VTerminal.component.VPanel
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel
import java.awt.Color
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.io.File
import java.util.concurrent.ThreadLocalRandom
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException


object Game : KeyListener {

    val saveFolder = File("saves")
   private val frame = VFrame(
            100, 64
    )
    var currentVeil: Veil = MainMenuVeil()


    @JvmStatic
    fun main(args: Array<String>) {

        saveFolder.mkdirs()
        try {
            UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance(24))
        } catch (e: UnsupportedLookAndFeelException) {
            e.printStackTrace()
        }


        frame.isVisible = true
        frame.pack()
        frame.setLocationRelativeTo(null)



//        SwingUtilities.invokeLater {
//            val frame: VFrame = VFrame(40, 20)
//            frame.setVisible(true)
//            frame.pack()
//            frame.setLocationRelativeTo(null)
//
//            val panel = frame.getContentPane()
//            for (y in 0 until panel.getHeightInTiles()) {
//                for (x in 0 until panel.getWidthInTiles()) {
//                    panel.setCodePointAt(x, y, getRandomCodePoint())
//                    panel.setBackgroundAt(x, y, getRandomColor())
//                    panel.setForegroundAt(x, y, getRandomColor())
////                    panel.setSequentialImageOpAt(x, y, SequentialOp(GaussianFilter(3f)))
//                }
//            }
//            Executors.newSingleThreadScheduledExecutor().schedule({
//                panel.reset()
//                SwingUtilities.invokeLater(panel::repaint)
//            }, 2, TimeUnit.SECONDS)
//        }
//        screen.isVisible = true
        frame.addKeyListener(this)

//        screen.font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1.0)

//        screen.addCanvasToFrame()
//        screen.addListener(this)

        currentVeil.draw(frame.contentPane)
        frame.repaint()
    }

    override fun keyPressed(e: KeyEvent) {
        println(e)
        currentVeil.keyboard(e, this)
        currentVeil.draw(frame.contentPane)
//        screen.repaint()
        frame.repaint()
    }

    override fun keyReleased(e: KeyEvent) {

    }

    override fun keyTyped(e: KeyEvent) {

    }


    private fun getRandomCodePoint(): Int {
        return ThreadLocalRandom.current().nextInt(33, 127)
    }

    private fun getRandomColor(): Color {
        return when (ThreadLocalRandom.current().nextInt(0, 6)) {
            0 -> {
                Color.MAGENTA
            }

            1 -> {
                Color.GREEN
            }

            2 -> {
                Color.YELLOW
            }

            3 -> {
                Color.BLUE
            }

            4 -> {
                Color.RED
            }

            5 -> {
                Color.ORANGE
            }

            else -> {
                Color.WHITE
            }
        }
    }
}