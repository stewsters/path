package com.stewsters.path.map

object MapGenerator {
    val chunkSize = 32

    fun generateMap(world: World, x: Int, y: Int): MapChunk {
        val chunk = MapChunk(world, x, y, chunkSize, chunkSize)

        for (x in 0..chunkSize - 1) {
            for (y in 0..chunkSize - 1) {
                val tile = chunk.at(x, y)

                if (tile.isPresent)
                    chunk.at(x, y).get().type = TileType.GRASS
                else {
                    println("$x $y")
                }
            }
        }

        return chunk
    }

}