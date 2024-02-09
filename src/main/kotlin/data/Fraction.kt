package data
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
class Fraction {
    var wholePart: Int = 0
    var numerator: Int =0
    var denominator: Int = 0

    @JsonCreator
    constructor(
        @JsonProperty("wholePart") wholePart: Int,
        @JsonProperty("numerator")numerator: Int,
        @JsonProperty("denominator") denominator: Int
    ) {
        this.wholePart = wholePart
        this.numerator = numerator
        this.denominator = denominator
    }

    constructor()

    fun Fraction() {}



    override fun toString(): String {
        return "ProperFraction{" +
                "wholePart=" + wholePart +
                ", numerator=" + numerator +
                ", denominator=" + denominator +
                '}'
    }
}