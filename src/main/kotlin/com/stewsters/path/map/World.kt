package com.stewsters.path.map

import com.stewsters.path.action.Action
import com.stewsters.path.action.ActionResult
import com.stewsters.path.entity.Entity
import com.stewsters.path.entity.Life
import com.stewsters.path.entity.TurnTaker
import com.stewsters.util.math.Point2i


class World(xSize: Int, ySize: Int, var xFocus: Int, var yFocus: Int) : Box(xSize, ySize) {

    private val tiles: Array<MapChunk>
    var player: Entity

    init {
        assert(xFocus < xSize && xFocus >= 0)
        assert(yFocus < ySize && yFocus >= 0)

        tiles = Array<MapChunk>(xSize * ySize, { index ->
            MapGenerator.generateMap(this, index % xSize, index / ySize)
        })

        player = Entity(
                chunk = getCurrentMap(),
                pos = Point2i(MapGenerator.chunkSize / 2, MapGenerator.chunkSize / 2),
                turnTaker = TurnTaker(0, { _, _ -> null }),
                life = Life(10)
        )

        getCurrentMap().addPawn(player)

    }

    fun getCurrentMap(): MapChunk {
        return getMapAt(xFocus, yFocus)
    }

    fun getMapAt(x: Int, y: Int): MapChunk {
        return tiles[x + y * xSize]
    }

    fun update() {
        val map: MapChunk = getCurrentMap()
        while (player.life?.cur ?: 0 > 0) {
            // Break early if there is no one to work on
            if (map.pawnQueue.size <= 0)
                return

            var currentTurnTaker: TurnTaker = map.pawnQueue.peek()

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
                    println("${action.pawn.char} failed at ${action}")
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
                    println("${action.pawn.char} is trying ${result.alternative} instead of ${action}")
                    action = result.alternative

                } else {
                    println("${action.pawn.char} did ${action}${if (result.nextAction != null) ". Trying ${result.nextAction} next." else ""}")
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