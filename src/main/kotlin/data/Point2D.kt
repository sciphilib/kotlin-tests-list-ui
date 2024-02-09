package data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.serialization.Serializable

@Serializable
class Point2D{
    var x: Int = 0
    var y: Int = 0

    @JsonCreator
    constructor(
        @JsonProperty("x")  x: Int,
        @JsonProperty("y") y: Int,

        ) {
        this.x = x
        this.y = y
    }

    constructor()

    fun Fraction() {}



    override fun toString(): String {
        return "Point2D{" +
                "X=" + x +
                ", Y=" + y +
                '}'
    }
}