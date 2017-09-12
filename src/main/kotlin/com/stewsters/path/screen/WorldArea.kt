package com.stewsters.path.screen

import com.stewsters.path.map.World
import com.valkryst.VTerminal.builder.component.ComponentBuilder
import com.valkryst.VTerminal.component.Component

class WorldArea(worldBuilder: WorldBuilder) : Component(worldBuilder) {


}

class WorldBuilder(val world: World, width: Int, height: Int) : ComponentBuilder<WorldArea>() {

    init {
        this.width = width
        this.height = height
        this.columnIndex = 0
        this.rowIndex = 0
    }

}