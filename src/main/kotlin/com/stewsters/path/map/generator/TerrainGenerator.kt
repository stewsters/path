package com.stewsters.path.map.generator

import com.stewsters.path.map.MapChunk
import com.stewsters.path.map.TileType
import com.stewsters.path.map.World
import kaiju.math.Vec3
import kaiju.noise.OpenSimplexNoise

object TerrainGenerator {
    const val chunkSize = 32

    fun generateChunk(
        world: World,
        shapeMods: List<(x: Int, y: Int) -> Double>,
        chunkPos: Vec3,
        seed: Long,
        skip: Boolean
    ): MapChunk {

        val chunk = MapChunk(world, chunkPos, chunkSize, chunkSize, chunkSize)
        val el = OpenSimplexNoise(seed)

        // Map type
        for (x in 0 until chunkSize) {
            for (y in 0 until chunkSize) {


                val nx: Int = chunkPos.x * chunkSize + x
                val ny: Int = chunkPos.y * chunkSize + y

                var ridginess = fbm(el, nx.toDouble(), ny.toDouble(), 6, 1.0 / 320.0, 1.0, 2.0, 0.5)
                ridginess = Math.abs(ridginess) * -1

                val elevation = Math.max(
                    fbm(el, nx.toDouble(), ny.toDouble(), 6, 1.0 / 200.0, 1.0, 2.0, 0.5),
                    ridginess
                ) + shapeMods.sumOf { it(nx, ny) }

                val groundHeight = (elevation * chunkSize).toInt()

                var type: TileType = TileType.AIR

                for (z in 0 until chunkSize) {

                    if (z < elevation) {
                        type = TileType.WALL

                    } else if (z == groundHeight) {

                        when {
                            elevation < -0.2 -> type = TileType.WATER_LAKE
                            elevation < 0 -> type = TileType.WATER_SWAMP
                            elevation < 0.50 -> type =
                                if (el.random2D(nx.toDouble(), ny.toDouble()) < elevation - 0.4) {
                                    TileType.TREE
                                } else
                                    TileType.GRASS

                            else -> type = TileType.WALL
                        }

                    } else {
                        type = TileType.AIR
                    }

                    chunk.at(x, y, z).type = type
                }

//                if (skip) {
//                    type = TileType.GRASS
//                }


            }
        }

        return chunk
    }

    fun fbm(
        el: OpenSimplexNoise,
        x: Double,
        y: Double,
        octaves: Int,
        frequency: Double,
        amplitude: Double,
        lacunarity: Double,
        gain: Double
    ): Double {
        var freq = frequency
        var amp = amplitude
        var total = 0.0
        for (i in 0 until octaves) {
            total += el.random2D(x * freq, y * freq) * amp
            freq *= lacunarity
            amp *= gain
        }
        return total
    }

}