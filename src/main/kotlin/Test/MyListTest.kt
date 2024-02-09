package org.example.Test
import dataStructures.MyList
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class MyListTest {
    var size = 4
    lateinit var oldList: MyList<Int>
    lateinit var newList: MyList<Int>

    @BeforeEach
    fun setup() {
        oldList = MyList()
        newList = MyList()
    }

    //Исходный набор содержит одинаковые значения
    @Test
    fun SortEquialTest() {
        for (i in 0..size) {
            oldList.add(1)
            newList.add(1)
        }
        newList!!.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит неотсторитрованные значения
    @Test
    fun UnSortedListTest() {
        for (i in 0..size) {
            oldList.add(i)
        }
        newList.add(4)
        newList.add(2)
        newList.add(1)
        newList.add(0)
        newList.add(3)
        newList!!.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит отсторитрованные значения
    @Test
    fun SortedListTest() {
        for (i in 0..size) {
            oldList.add(i)
            newList.add(i)
        }

        newList!!.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит обратно отсторитрованные значения
    @Test
    fun ReverseSortedListTest() {
        for (i in 0..size) {
            oldList.add(i)

        }
        for (i in size downTo 0) {
            newList.add(i)

        }
        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит повторяющиеся значения
    @Test
    fun RepeatableListTest() {
        for (i in 0..size) {
            oldList.add(i)

        }
        oldList.add(4)

        newList.add(4)
        newList.add(4)
        newList.add(2)
        newList.add(1)
        newList.add(0)
        newList.add(3)
        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит группы повторяющихся значений
    @Test
    fun RepeatableGroupListTest() {
        for (i in 0..size) {
            oldList.add(i)
            oldList.add(i)

        }
        newList.add(4)
        newList.add(4)
        newList.add(1)
        newList.add(1)
        newList.add(2)
        newList.add(2)
        newList.add(0)
        newList.add(0)
        newList.add(3)
        newList.add(3)
        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит экстримальные значения в середине
    @Test
    fun ExtremeValueInTheMiddleTest() {
        oldList.add(0)
        oldList.add(1)
        oldList.add(2)
        oldList.add(3)
        oldList.add(2023)

        newList.add(1)
        newList.add(3)
        newList.add(2023)
        newList.add(2)
        newList.add(0)

        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит экстримальные значения в начале
    @Test
    fun ExtremeValueInTheStartTest() {
        oldList.add(0)
        oldList.add(1)
        oldList.add(2)
        oldList.add(3)
        oldList.add(2023)

        newList.add(2023)
        newList.add(1)
        newList.add(3)
        newList.add(2)
        newList.add(0)

        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит экстримальные значения в конце
    @Test
    fun ExtremeValueInTheEndTest() {
        oldList.add(0)
        oldList.add(1)
        oldList.add(2)
        oldList.add(3)
        oldList.add(2023)

        newList.add(1)
        newList.add(3)
        newList.add(2)
        newList.add(0)
        newList.add(2023)

        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Исходный набор содержит повторяющиеся экстримальные значения
    @Test
    fun RepeatableExtremeValueTest() {
        oldList.add(0)
        oldList.add(1)
        oldList.add(2)
        oldList.add(3)
        oldList.add(2023)
        oldList.add(2023)
        oldList.add(2023)

        newList.add(2023)
        newList.add(1)
        newList.add(3)
        newList.add(2)
        newList.add(2023)
        newList.add(0)
        newList.add(2023)

        newList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
        assertTrue(newList.isEqual(oldList))
    }

    //Удаление последнего элемента
    @Test
    fun DeleteTest() {


        for (i in 0..size) {
            oldList.add(i)


        }
        for (i in 0..size + 1) {
            newList.add(i)
        }
        newList.remove(size + 1)
        assertTrue(newList.isEqual(oldList))


    }

    //Очистка списка
    @Test
    fun ClearListTest() {
        for (i in 0..size) {
            newList.add(i)
        }
        newList.clear()
        assertTrue(newList.isEqual(oldList))


    }

    //Проверка скорости сортировки с шагом 10^5
    @Test
    fun SortListTimeTest() {
        var i = 10000
        val filePath = "TestSort.txt"
        val file = File(filePath)
        File(filePath).writeText("")
        var testString = ""
        while (i <= 50000) {
            oldList.clear()
            for (j in 0..i) {
                oldList.add(Random.nextInt(1, i))
            }
            var executionTime = measureTimeMillis {
                oldList.sort(Comparator.naturalOrder<Int>() as Comparator<Int>)
            }

            try {
                testString =
                    testString + ("Для списка из " + i + " элементов время вставки: " + executionTime / 1000.0 + "\n") // Записываем текст в файл
                println("Текст успешно записан в файл.")
            } catch (e: Exception) {
                println("Ошибка при записи текста в файл: ${e.message}")
            }


            i += 10000
        }
        file.writeText(testString)

    }

        @Test
        fun AddElementListTimeTest() {
            var i = 100000
            val filePath = "TestAdd.txt"
            val file = File(filePath)
            File(filePath).writeText("")
            var testString = ""
            while (i <= 10000000) {
                oldList.clear()
                for (j in 0..i) {
                    oldList.add(Random.nextInt(1, i))
                }
                var executionTime = measureTimeMillis {
                    oldList.add(12, i / 2)
                }

                try {
                    testString =
                        testString + ("Для списка из " + i + " элементов время вставки: " + executionTime / 1000.0 + "\n") // Записываем текст в файл

                    println("Текст успешно записан в файл.")
                } catch (e: Exception) {
                    println("Ошибка при записи текста в файл: ${e.message}")
                }


                i *= 10
            }
            file.writeText(testString)
        }

    @Test
    fun DeleteElementListTimeTest() {
        var i = 100000
        val filePath = "TestDelete.txt"
        val file = File(filePath)
        File(filePath).writeText("")
        var testString = ""
        while (i <= 10000000) {
            oldList.clear()
            for (j in 0..i) {
                oldList.add(Random.nextInt(1, i))
            }
            var executionTime = measureTimeMillis {
                oldList.remove(i/2)
            }

            try {
                testString =
                    testString + ("Для списка из " + i + " элементов время удаления: " + executionTime / 1000.0 + "\n") // Записываем текст в файл

                println("Текст успешно записан в файл.")
            } catch (e: Exception) {
                println("Ошибка при записи текста в файл: ${e.message}")
            }


            i *= 10
        }
        file.writeText(testString)
    }
}