package com.stewsters.path.action

class ActionResult(
    val succeeded: Boolean = true,    // true if the [Action] was successful and energy should be consumed.
    val breakout: Boolean = false,    // should break out of the loop.  Used if player is no longer there.
    val alternative: Action? = null, // An alternate [Action] that should be performed instead of the one that failed.
    val nextAction: Action? = null // An [Action] that should be immediately follow this one.
) {

    companion object {
        val SUCCESS = ActionResult(true)
        val FAILURE = ActionResult(false)
        val BREAKOUT = ActionResult(true, true)
    }
}