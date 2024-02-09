package dataStructures

fun interface Action<T> {
    fun toDo(data: T)
}
