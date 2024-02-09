package dataStructures

import java.util.Comparator

interface IList<T> {
        fun add(data: T)
        fun add(data: T, index: Int)
        fun remove(index: Int)
       // fun forEach(a: Action<T>?)
        //fun sort(comp: Comparator<T>?)
        fun get(index: Int): T
        fun size(): Int
}