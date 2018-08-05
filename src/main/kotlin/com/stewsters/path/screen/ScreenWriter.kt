package com.stewsters.path.screen

import com.valkryst.VTerminal.Screen

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
