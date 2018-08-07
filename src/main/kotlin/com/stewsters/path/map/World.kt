package com.stewsters.path.map

import com.stewsters.path.Game.saveFolder
import com.stewsters.path.action.Action
import com.stewsters.path.action.ActionResult
import com.stewsters.path.action.RestAction
import com.stewsters.path.action.WalkAction
import com.stewsters.path.ecs.component.Equipment
import com.stewsters.path.ecs.component.Inventory
import com.stewsters.path.ecs.component.Item
import com.stewsters.path.ecs.component.Life
import com.stewsters.path.ecs.component.TurnTaker
import com.stewsters.path.ecs.component.Weapon
import com.stewsters.path.ecs.entity.Entity
import com.stewsters.path.ecs.enums.DisplayOrder
import com.stewsters.path.ecs.enums.Faction
import com.stewsters.path.ecs.enums.Slot
import com.stewsters.path.map.generator.TerrainGenerator
import com.stewsters.util.math.MatUtils
import kaiju.math.RectangularPrism
import kaiju.math.Vec3
import kaiju.math.getChebyshevDistance
import java.awt.Color
import java.io.File
import java.util.*


class World(xSize: Int, ySize: Int, zSize: Int,
            xFocus: Int, yFocus: Int, zFocus: Int,
            val gameName: String = UUID.randomUUID().toString(),
            skip: Boolean = false) : RectangularPrism(Vec3[0, 0, 0], Vec3[xSize, ySize, zSize]) {

    private val tiles: Array<MapChunk>
    var player: Entity

    init {
        assert(xFocus in 0..(xSize - 1))
        assert(yFocus in 0..(ySize - 1))

        val seed = gameName.hashCode().toLong()

        val worldWidth: Double = (TerrainGenerator.chunkSize * xSize).toDouble()

        val shapes = listOf({ x: Int, y: Int ->
            val xPercent = ((x / worldWidth) - 0.5) * 2
            val yPercent = ((y / worldWidth) - 0.5) * 2

            maxOf(xPercent * xPercent, yPercent * yPercent)
        })

        tiles = Array(xSize * ySize * zSize) { index ->
            // todo:3d
            TerrainGenerator.generateChunk(this, shapes, Vec3[index % xSize, index / ySize], seed, skip) // TODO: z
        }

        if (!skip) {
            // Construction
            for (tile in tiles) {
                for (x in 0 until TerrainGenerator.chunkSize) {
                    for (y in 0 until TerrainGenerator.chunkSize) {
                        for (z in 0 until TerrainGenerator.chunkSize) {

                            if (x == 6 && y <= 10 && y >= 6) {
                                if (y == 8)
                                    tile.at(x, y, z).type = TileType.CLOSED_DOOR
                                else
                                    tile.at(x, y, z).type = TileType.WALL
                            }
                        }
                    }
                }
            }
        }

        val currentMap = getMapAt(xFocus, yFocus, zFocus)
        player = Entity(
                name = "Player",
                chunk = currentMap,
                pos = Vec3[TerrainGenerator.chunkSize / 2, TerrainGenerator.chunkSize / 2, TerrainGenerator.chunkSize / 2],
                faction = Faction.HUMAN,
                displayOrder = DisplayOrder.PLAYER,
                turnTaker = TurnTaker(0, { _, _ -> null }),
                life = Life(10),
                doorOpener = true,
                inventory = Inventory(ArrayList())
        )
        currentMap.addPawn(player)

        player.inventory?.items?.add(Entity(
                name = "Rusted Saber",
                description = "An old sword, it has seen its fair share of combat.",
                chunk = player.chunk,
                pos = player.pos,
                char = '/',
                displayOrder = DisplayOrder.ITEM,
                item = Item(
                        weapon = Weapon(damage = 5),
                        equipment = Equipment(Slot.WEAPON, isEquipped = true)
                )
        ))


        val horse = Entity(
                name = "Roach",
                char = 'h',
                displayOrder = DisplayOrder.ALLY,
                chunk = player.chunk,
                pos = player.pos + Vec3[2, 0, 0],
                faction = Faction.HUMAN,
                turnTaker = TurnTaker(1, { _, entity ->
                    val playerX = player.globalX()
                    val playerY = player.globalY()
                    val horseX = entity.globalX()
                    val horseY = entity.globalY()

                    if (getChebyshevDistance(player.pos, entity.pos) > 5) {
                        WalkAction(entity, Vec3[
                                MatUtils.limit(playerX - horseX, -1, 1),
                                MatUtils.limit(playerY - horseY, -1, 1),
                                0
                        ]
                        )
                    } else
                        RestAction(entity)
                }),
                life = Life(100),
                mountable = true
        )
        currentMap.addPawn(horse)

        if (!skip) {
            for (mapChunk in tiles) {
                for (i in 1..5) {
                    val x = MatUtils.getIntInRange(0, mapChunk.upper.x - 1)
                    val y = MatUtils.getIntInRange(0, mapChunk.upper.y - 1)
                    val z = MatUtils.getIntInRange(0, mapChunk.upper.z - 1)

                    if (mapChunk.at(x, y, z).type.blocks)
                        continue

                    if (mapChunk.pawnInSquare(x, y, z).isNotEmpty())
                        continue


                    val wolf = Entity(
                            name = "Wolf",
                            char = 'w',
                            chunk = mapChunk,
                            pos = Vec3[x, y, z],
                            life = Life(1),
                            faction = Faction.MONSTER,
                            displayOrder = DisplayOrder.OPPONENT,
                            turnTaker = TurnTaker(2 + i, { _, entity ->
                                val playerX = player.globalX()
                                val playerY = player.globalY()
                                val xPos = entity.globalX()
                                val yPos = entity.globalY()

                                WalkAction(entity, Vec3[
                                        MatUtils.limit(playerX - xPos, -1, 1),
                                        MatUtils.limit(playerY - yPos, -1, 1),
                                        0
                                ])
                            }),
                            deathFunction = {
                                with(it) {
                                    println("$name died.")
                                    turnTaker = null
                                    displayOrder = DisplayOrder.BODY
                                    faction = null
                                    life = null
                                    char = '%'
                                    blocks = false
                                    color = Color.RED
//                                    chunk.update(it)
                                }

                            }
                    )
                    mapChunk.addPawn(wolf)

                }

            }
        }
    }

    fun getMapAt(pos: Vec3): MapChunk = getMapAt(pos.x, pos.y, pos.z)
    fun getMapAt(x: Int, y: Int, z: Int): MapChunk = tiles[x + y * upper.x]  // todo z

    fun update() {

        while (player.isAlive()) {

            val map: MapChunk = player.chunk

            println("processing for ${map.pos}")
            // Break early if there is no one to work on
            if (map.pawnQueue.size <= 0)
                return

            var currentTurnTaker: TurnTaker = map.pawnQueue.peek()

            if (currentTurnTaker.parent?.turnTaker == null) {
                // Clear out the dead, easier to do it in one location
                map.pawnQueue.remove(currentTurnTaker)
                println("Auto clearing ${currentTurnTaker.parent?.name}")
                continue
            }

            var action: Action? = currentTurnTaker.nextAction(map, currentTurnTaker.parent!!)

            // Do action until it succeeds
            while (true) {
                if (action == null) {
                    //   gravitySystem.processSystem()
                    //   snipeSystem.processSystem()
                    println("Looks like the player needs to choose what to do")
                    return
                }

                val result: ActionResult = action.onPerform()

                print("${action.pawn.chunk.pos}: ")

                if (!result.succeeded) {
                    println("${action.pawn.name} failed at $action")
                    if (currentTurnTaker.parent == player) {
                        // player, they need time to select a new move
                        // cancel action, Do nothing
                        currentTurnTaker.setNextAction(null)
                        return
                    }
                    break // AI still do something, this is prevent an ai from making bad choices and looping
                }

                if (result.alternative != null) {
                    // Alternative actions we should try
                    println("${action.pawn.name} is trying ${result.alternative} instead of $action")
                    action = result.alternative

                } else if (result.breakout) {
                    println("Brokeout ${action.pawn.name}")
                    return

                } else {
                    println("${action.pawn.name} did $action${if (result.nextAction != null) ". Trying ${result.nextAction} next." else ""}")
                    // We should automatically do another action after this one
                    currentTurnTaker.setNextAction(result.nextAction)
                    break
                }

            }

            if (map.pawnQueue.size == 0)
                return

            currentTurnTaker = map.pawnQueue.poll()
            // increment time,
            currentTurnTaker.gameTurn += action?.costInTurns ?: 0
            map.pawnQueue.add(currentTurnTaker)
        }


    }

    fun saveGame(saveDir: File) {
        val gameSaveFolder = File(saveFolder, gameName)
        gameSaveFolder.mkdirs()
        tiles.forEach {
            it.writeToDisk(gameSaveFolder)
        }
    }

}