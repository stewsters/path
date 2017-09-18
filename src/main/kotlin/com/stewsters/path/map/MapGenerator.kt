package com.stewsters.path.map

import com.stewsters.util.noise.OpenSimplexNoise
import java.util.*


object MapGenerator {
    val chunkSize = 32

    fun generateChunk(world: World, chunkX: Int, chunkY: Int, seed: Long): MapChunk {

        val chunk = MapChunk(world, chunkX, chunkY, chunkSize, chunkSize)
        val el = OpenSimplexNoise(seed);

        // Map type
        for (x in 0..chunkSize - 1) {
            for (y in 0..chunkSize - 1) {

                val nx: Int = chunkX * chunkSize + x
                val ny: Int = chunkY * chunkSize + y

                var ridginess = fbm(el, nx.toDouble(), ny.toDouble(), 6, 1.0 / 320.0, 1.0 , 2.0, 0.5)
                ridginess = Math.abs(ridginess) * -1

                var elevation = Math.max(fbm(el, nx.toDouble(), ny.toDouble(), 6, 1.0 / 200.0, 1.0, 2.0, 0.5), ridginess)

//                for (mod in shapeMods) {
//                    elevation += mod.modify(nx, ny)
//                }

                var type = TileType.GRASS
                if(elevation<0){
                    type=TileType.WATER_OCEAN
                }

                if (x == 6 && y <= 10 && y >= 6) {
                    if (y == 8)
                        type = TileType.CLOSED_DOOR
                    else
                        type = TileType.WALL
                }
                chunk.at(x, y).type = type

            }
        }

        return chunk
    }

    fun fbm(el: OpenSimplexNoise, x: Double, y: Double, octaves: Int, frequency: Double, amplitude: Double, lacunarity: Double, gain: Double): Double {
        var freq = frequency
        var amp = amplitude
        var total = 0.0
        for (i in 0..octaves - 1) {
            total += el.eval(x * freq, y * freq) * amp
            freq *= lacunarity
            amp *= gain
        }
        return total
    }

}