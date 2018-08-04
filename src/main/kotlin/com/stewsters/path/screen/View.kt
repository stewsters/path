package com.stewsters.path.screen

import com.valkryst.VTerminal.Screen
import com.valkryst.VTerminal.component.Layer
import java.awt.Dimension

open class View(screen: Screen) : Layer(Dimension(screen.getWidth(), screen.getHeight()))