package com.stewsters.path.action

import com.stewsters.path.ecs.entity.Entity
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class DismountAction(pawn: Entity) : Action(pawn) {

    override fun onPerform(): ActionResult {
        if (pawn.mount == null) {
            return ActionResult.FAILURE
        }

        //find a clear spot to put it
        val possible = pawn.pos.mooreNeighborhood()
                .filter {
                    pawn.chunk.contains(it)
                            && !pawn.chunk.at(it).type.blocks
                            && pawn.chunk.pawnInSquare(it).none { it.blocks }
                }
                .random()

        if (possible == null) {
            // Cannot dismount in crowded environments
            return ActionResult.FAILURE
        }

        pawn.mount?.let { mount ->
            mount.pos = possible
            mount.chunk = pawn.chunk
            pawn.chunk.addPawn(mount)
        }
        pawn.mount = null

        return ActionResult.SUCCESS
    }
}

private fun <E> List<E>.random(): E = this[ThreadLocalRandom.current().nextInt(this.size)]
private fun <E> List<E>.random(seed: Long): E = this[Random(seed).nextInt(this.size)]

