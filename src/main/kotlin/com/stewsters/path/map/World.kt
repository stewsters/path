package com.stewsters.path.map

import com.stewsters.path.action.Action
import com.stewsters.path.action.ActionResult
import com.stewsters.path.action.RestAction
import com.stewsters.path.action.WalkAction
import com.stewsters.path.entity.*
import com.stewsters.path.map.generator.MapGenerator
import com.stewsters.util.math.MatUtils
import veclib.Box
import veclib.Vec2
import veclib.getChebyshevDistance


class World(xSize: Int, ySize: Int, xFocus: Int, yFocus: Int, skip: Boolean = false) : Box(xSize, ySize) {

    private val tiles: Array<MapChunk>
    var player: Entity

    init {
        assert(xFocus in 0..(xSize - 1))
        assert(yFocus in 0..(ySize - 1))

//        val r: Random = Random(2323)
        val seed = "candy".hashCode().toLong() //  r.nextLong()

        val worldWidth: Double = (MapGenerator.chunkSize * xSize).toDouble()

        val shapes = listOf({ x: Int, y: Int ->
            val xPercent = ((x / worldWidth) - 0.5) * 2
            val yPercent = ((y / worldWidth) - 0.5) * 2

            maxOf(xPercent * xPercent, yPercent * yPercent)
        })

        tiles = Array(xSize * ySize, { index ->
            MapGenerator.generateChunk(this, shapes, Vec2.get(index % xSize, index / ySize), seed, skip)
        })

        if (!skip) {
            // Construction
            for (tile in tiles) {
                for (x in 0 until MapGenerator.chunkSize) {
                    for (y in 0 until MapGenerator.chunkSize) {

                        if (x == 6 && y <= 10 && y >= 6) {
                            if (y == 8)
                                tile.at(x, y).type = TileType.CLOSED_DOOR
                            else
                                tile.at(x, y).type = TileType.WALL
                        }
                    }
                }
            }
        }

        player = Entity(
                name = "Player",
                chunk = getMapAt(xFocus, yFocus),
                pos = Vec2.get(MapGenerator.chunkSize / 2, MapGenerator.chunkSize / 2),
                faction = Faction.HUMAN,
                displayOrder = DisplayOrder.PLAYER,
                turnTaker = TurnTaker(0, { _, _ -> null }),
                life = Life(10),
                doorOpener = true,
                inventory = Inventory(ArrayList())
        )
        getCurrentMap().addPawn(player)

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
                pos = Vec2.get(player.pos.x + 2, player.pos.y),
                faction = Faction.HUMAN,
                turnTaker = TurnTaker(0, { chunk, entity ->
                    val playerX = player.globalX()
                    val playerY = player.globalY()
                    val horseX = entity.globalX()
                    val horseY = entity.globalY()

                    if (getChebyshevDistance(player.pos, entity.pos) > 5) {
                        WalkAction(entity, Vec2.get(
                                MatUtils.limit(playerX - horseX, -1, 1),
                                MatUtils.limit(playerY - horseY, -1, 1)))
                    } else
                        RestAction(entity)
                }),
                life = Life(100),
                mountable = true
        )
        getCurrentMap().addPawn(horse)

        if (!skip) {
            for (mapChunk in tiles) {
                for (i in 1..5) {
                    val x = MatUtils.getIntInRange(0, mapChunk.highX - 1)
                    val y = MatUtils.getIntInRange(0, mapChunk.highY - 1)

                    if (mapChunk.at(x, y).type.blocks)
                        continue

                    if (mapChunk.pawnInSquare(x, y).isNotEmpty())
                        continue


                    val wolf = Entity(
                            name = "Wolf",
                            char = 'w',
                            chunk = player.chunk,
                            pos = Vec2.get(x, y),
                            life = Life(1),
                            faction = Faction.MONSTER,
                            displayOrder = DisplayOrder.OPPONENT,
                            turnTaker = TurnTaker(0, { chunk, entity ->
                                val playerX = player.globalX()
                                val playerY = player.globalY()
                                val xPos = entity.globalX()
                                val yPos = entity.globalY()

                                WalkAction(entity, Vec2.get(
                                        MatUtils.limit(playerX - xPos, -1, 1),
                                        MatUtils.limit(playerY - yPos, -1, 1)))
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
//                                    chunk.update(it)
                                }

                            }
                    )
                    mapChunk.addPawn(wolf)

                }

            }
        }
    }

    fun getCurrentMap(): MapChunk = player.chunk

    fun getMapAt(pos: Vec2): MapChunk = getMapAt(pos.x, pos.y)
    fun getMapAt(x: Int, y: Int): MapChunk = tiles[x + y * highX]

    fun update() {
        val map: MapChunk = getCurrentMap()
        while (player.isAlive()) {
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


}