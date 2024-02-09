package dataStructures
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable
import javax.swing.DefaultListModel

@Serializable
class MyList<T : Any> : IList<T> {
    var head: Node<T>? = null
        private set
    private var tail: Node<T>? = null
    var length: Int = 0
        private set

    @Serializable
    class Node<T>(v: Any?) {
        var data: T = v as T
        var next: Node<T>?
        var prev: Node<T>? = null

        init {
            next = prev
        }
    }

    constructor()

    @JsonCreator
    constructor(@JsonProperty("elements") elements: List<T>) {
        for (element in elements) {
            add(element)
        }
    }

    @get:JsonProperty("elements")
    val elements: List<T>
        get() {
            val elements: MutableList<T> = ArrayList()
            var current = head
            while (current != null) {
                elements.add(current.data)
                current = current.next
            }
            return elements
        }

    fun addAll(loadedList: DefaultListModel<Any>): DefaultListModel<Any> {
        var current = head

        while (current != null) {
            loadedList.addElement(current.data)
            current = current.next
        }
        return loadedList
    }


    override fun get(index: Int): T {
        return getNode(index)!!.data
    }


    override fun remove(index: Int) {
        val tmp: Node<T>? = getNode(index)
        if (tmp !== head) {
            tmp?.prev?.next = tmp?.next
        } else {
            head = tmp?.next
        }
        if (tmp !== tail) {
            tmp?.next?.prev = tmp?.prev
        } else {
            tail = tmp?.prev
        }
        tmp?.prev = null
        tmp?.next = tmp?.prev
        length--
    }


    override fun size(): Int {
        return length
    }

    fun clear() {
        head = null
        tail = null
        length = 0
    }

    override fun add(v: T) {
        if (head == null) {
            head = Node(v)
            tail = head
            length++
            return
        }
        val newTail = Node<T>(v)
        newTail.prev = tail
        tail!!.next = newTail
        tail = newTail
        length++
    }

    override fun add(data: T, index: Int) {
        val newNode = Node<T>(data)

        if (length == 0) {
            add(data);
            return;
        }
        if (index == 0) {
            if (head == null) {
                head = Node(data)
                tail = head
                length++
                return
            }
            newNode.next = head
            newNode.prev = null
            head!!.prev = newNode
            head = newNode
            length++
            return
        }

        if (index == length) {
            add(data)
            return
        }

        val tmp = getNode(index)
        if (tmp !== head) {
            tmp!!.prev!!.next = newNode
            newNode.prev = tmp.prev
        } else {
            head = newNode
        }
        newNode.next = tmp
        tmp!!.prev = newNode
        length++
    }


    fun toList(): List<T> {
        val list: MutableList<T> = ArrayList()
        var current = head
        while (current != null) {
            list.add(current.data)
            current = current.next
        }
        return list
    }

    fun sort(comparator: Comparator<T>) {
        if (head == null || head?.next == null) {
            return
        }

        var swapped = true

        while (swapped) {
            swapped = false
            var current = head

            while (current?.next != null) {
                if (comparator.compare(current.data, current.next!!.data) > 0) {
                    val temp = current.data
                    current.data = current.next!!.data
                    current.next!!.data = temp
                    swapped = true
                }

                current = current.next
            }
        }
    }


    private fun getNode(index: Int): Node<T>? {
        println(length)
        // if (index < 0 || index >= length)
        if (index == 0) return head
        if (index < 0 || index > length) throw IndexOutOfBoundsException()
        var tmp: Node<T>? = head

        for (i in 0 until index) {
            tmp = tmp?.next
        }
        return tmp
    }


    val isEmpty: Boolean
        get() = if (this.length == 0) true
        else false


    fun isEqual(otherList: MyList<T>): Boolean {
        var currentNode = head
        var otherCurrentNode = otherList.head
        if(this.length!=otherList.length){
        return false
        }
        while (currentNode != null && otherCurrentNode != null) {
            if (currentNode.data != otherCurrentNode.data) {
                return false
            }
            currentNode = currentNode.next
            otherCurrentNode = otherCurrentNode.next
        }

        // Если один из списков закончился раньше, то они не равны
        if (currentNode != null || otherCurrentNode != null) {
            return false
        }

        return true
    }
}