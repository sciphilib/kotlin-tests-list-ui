package data.factories

interface IFactory<T> {
    fun create(vararg args: Any?): T
    fun createRandom(): T
    fun getComparator(): Comparator<T>
}