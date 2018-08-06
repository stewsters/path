package com.stewsters.path.screen

import com.valkryst.VTerminal.Screen
import java.awt.Color

fun Screen.drawString(string: String, x: Int, y: Int) {

    string.forEachIndexed { index, c ->
        with(getTileAt(x + index, y)) {
            reset()
            character = c
        }
    }

}

fun Screen.clear() {
    for (y in 0 until height) {
        for (x in 0 until width) {
            getTileAt(x, y).reset()
        }
    }
}


fun Screen.horizontalLine(y: Int, x1: Int = 0, x2: Int = this.width - 1, char: Char = '#', color: Color = Color.WHITE, background: Color = Color.BLACK) {
    for (x in x1..x2) {
        with(this.getTileAt(x, y)) {
            reset()
            character = char
            foregroundColor = color
            backgroundColor = background
        }
    }
}

fun Screen.verticalLine(x: Int, y1: Int = 0, y2: Int = this.height - 1, char: Char = '#', color: Color = Color.WHITE, background: Color = Color.BLACK) {
    for (y in y1..y2) {
        with(this.getTileAt(x, y)) {
            reset()
            character = char
            foregroundColor = color
            backgroundColor = background
        }
    }
}
