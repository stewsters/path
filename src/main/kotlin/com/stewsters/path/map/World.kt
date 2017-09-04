package com.stewsters.path.map

import com.stewsters.path.entity.Entity
import com.stewsters.util.math.Point2i


class World(val xSize: Int, val ySize: Int, var xFocus: Int, var yFocus: Int) {

    //    private var loadedMap : MapChunk
    private val tiles: Array<MapChunk>
    var player: Entity

    init {
        tiles = Array<MapChunk>(xSize * ySize, { index ->
            MapGenerator.generateMap(0, 0)
        })

        player = Entity(
                chunk = getCurrentMap(),
                pos = Point2i(MapGenerator.chunkSize / 2, MapGenerator.chunkSize / 2)
        )

    }

    fun getCurrentMap(): MapChunk {
        return getMapAt(xFocus, yFocus)
    }

    fun getMapAt(x: Int, y: Int): MapChunk {
        return tiles[x + y * xSize]
    }

    fun update() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}