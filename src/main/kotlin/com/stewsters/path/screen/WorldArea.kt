package com.stewsters.path.screen

import com.valkryst.VTerminal.builder.component.ComponentBuilder
import com.valkryst.VTerminal.component.Component

class WorldArea(worldBuilder: WorldBuilder) : Component(worldBuilder)

class WorldBuilder(width: Int, height: Int) : ComponentBuilder<WorldArea>() {

    init {
        this.width = width
        this.height = height
        this.columnIndex = 0
        this.rowIndex = 0
    }

}