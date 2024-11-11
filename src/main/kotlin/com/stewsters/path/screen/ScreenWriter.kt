package com.stewsters.path.screen

import com.valkryst.VTerminal.component.VPanel
import java.awt.Color

fun VPanel.drawString(string: String, x: Int, y: Int) {

    string.forEachIndexed { index, c ->

        this.setCodePointAt(x + index, y, c.code)
//        with(getTileAt(x + index, y)) {
//            reset()
//            character = c
//        }
    }

}

fun VPanel.clear() {
    reset()
}


fun VPanel.horizontalLine(
    y: Int,
    x1: Int = 0,
    x2: Int = this.width - 1,
    char: Char = '#',
    color: Color = Color.WHITE,
    background: Color = Color.BLACK
) {

    for (x in x1..x2) {
        setForegroundAt(x, y, color)
        setBackgroundAt(x, y, background)
        setCodePointAt(x, y, char.code)
    }
}

fun VPanel.verticalLine(
    x: Int,
    y1: Int = 0,
    y2: Int = this.height - 1,
    char: Char = '#',
    color: Color = Color.WHITE,
    background: Color = Color.BLACK
) {
    for (y in y1..y2) {

        setForegroundAt(x, y, color)
        setBackgroundAt(x, y, background)
        setCodePointAt(x, y, char.code)
    }
}
