package ecs



class ComponentManager<T:Component>() {

    val data: MutableMap<Long, T>

    init {
        data = HashMap<Long, T>()
    }

    fun get(e: Entity): T? = data[e.id]


    fun set(e: Entity, component:T ): Unit {
        data[e.id] = component
    }
}