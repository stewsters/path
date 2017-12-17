package krogueutil

import com.google.gson.Gson
import com.stewsters.path.ecs.component.Armor

interface Serializer<in T, out V> {
    fun serialize(t: T): V
}

interface Deserializer<in T, out V> {
    fun deserialize(t: T): V
}

infix fun <T : Any?, V> T.serializedAs(s: Serializer<T, V>): V = s.serialize(this)
//infix fun <T : Any?, V> T.deserializedAs(s: Deserializer<T, V>): V = s.deserialize(this)


val JSON = object : Serializer<Any?, String>/*, Deserializer<String, Any?>*/ {
    override fun serialize(t: Any?) = Gson().toJson(t)

//    override fun deserialize(t: String): Any? = Gson().fromJson(t)
//    override fun deserialize() = Gson().fr
}

infix fun <Y> String.deserialize(kclass: Class<out Y>): Y {
    return Gson().fromJson(this, kclass)
}

fun main(args: Array<String>) {
    val data = arrayOf(1, 2, 3) serializedAs JSON

    val armor = Armor(5)
    println(armor serializedAs JSON deserialize armor.javaClass)


    val armor2 = "{armor:6}" deserialize Armor::class.java
    println(armor2)
}