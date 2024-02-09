package data.factories

import data.Fraction
import data.Point2D
import java.util.*
import kotlin.math.sqrt

class FractionFactory() : IFactory<Fraction?> {
    private val rand: Random = Random()

    override fun create(vararg args: Any?): Fraction {
        if (((args.size == 3
                    ) && args.get(0) is Int
                    && args.get(1) is Int
                    && args.get(2) is Int)
        ) {
            return Fraction(args.get(0) as Int, args.get(1) as Int, args.get(2) as Int)
        }
        throw IllegalArgumentException("Invalid arguments for creating a ProperFraction")
    }

    override fun createRandom(): Fraction {
        val numerator: Int = rand.nextInt(100)
        val denominator: Int = numerator + rand.nextInt(100) + 1
        val wholePart: Int = rand.nextInt(10)
        return Fraction(wholePart, numerator, denominator)
    }

    override fun getComparator(): Comparator<Fraction?> {
        return  Comparator<Fraction?> { p1, p2 ->
            if(p1.wholePart==p2.wholePart)
            {
                val d1 =(p1.numerator/p1.denominator).toDouble()
                val d2 = (p2.numerator/p2.denominator).toDouble()
                java.lang.Double.compare(d1, d2)
            }
            else {
                java.lang.Double.compare(p1.wholePart.toDouble(), p2.wholePart.toDouble())
            }
        }
    }

}
