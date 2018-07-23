package com.stewsters.path.ecs.system

private data class Message(val text:String)

object Msg{
    private val state = mutableListOf<Message>()

    fun log(message:String){
        state += Message(message);
    }

}