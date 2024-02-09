package data.factories

import data.Point2D
import java.util.*
import kotlin.math.sqrt

class Point2DFactory() : IFactory<Point2D?> {
    private val rand: Random = Random()

    override fun create(vararg args: Any?): Point2D {
        if (((args.size == 2
                    ) && args.get(0) is Int
                    && args.get(1) is Int)
        ) {
            return Point2D(args.get(0) as Int, args.get(1) as Int)
        }
        throw IllegalArgumentException("Invalid arguments for creating a Point2D")
    }

    override fun createRandom(): Point2D {
        val x: Int = rand.nextInt(100)
        val y: Int = rand.nextInt(100)
        return Point2D(x, y)
    }

    override fun getComparator():   Comparator<Point2D?> {
        return  Comparator<Point2D?> { p1, p2 ->
            val d1 = sqrt((p1.x * p1.x + p1.y * p1.y).toDouble())
            val d2 = sqrt((p2.x * p2.x + p2.y * p2.y).toDouble())
            java.lang.Double.compare(d1, d2)
        }
    }
}