package com.stewsters.path.screen

import com.valkryst.VTerminal.builder.ComponentBuilder
import com.valkryst.VTerminal.component.Component
import java.awt.Dimension
import java.awt.Point

class WorldArea(worldBuilder: WorldBuilder) : Component(Dimension(worldBuilder.width,worldBuilder.height), Point(0,0))

class WorldBuilder(width: Int, height: Int) : ComponentBuilder<WorldArea>() {

    init {
        this.width = width
        this.height = height
        this.xPosition = 0
        this.yPosition = 0
    }

}