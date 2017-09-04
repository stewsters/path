package com.stewsters.path.action

class ActionResult {

    /// An alternate [Action] that should be performed instead of the one that failed.
    val alternative: Action?
    val nextAction: Action?

    /// `true` if the [Action] was successful and energy should be consumed.
    val succeeded: Boolean

    internal constructor(succeeded: Boolean) {
        this.succeeded = succeeded
        alternative = null
        nextAction = null
    }

    constructor(succeeded: Boolean, nextAction: Action) {
        this.succeeded = succeeded
        this.alternative = null
        this.nextAction = nextAction
    }


    constructor(alternative: Action) {
        this.alternative = alternative
        this.succeeded = true
        this.nextAction = null
    }

    companion object {

        val SUCCESS = ActionResult(true)
        val FAILURE = ActionResult(false)
    }

}