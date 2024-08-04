package me.sagirii.worldborder.config

case class BorderOptions(heightLimit: BorderHeightLimit)

case class BorderHeightLimit(min: Int, max: Int)

object BorderHeightLimit {

    def apply(min: Int, max: Int): BorderHeightLimit = {
        require(min < max, "min must be less than max")
        new BorderHeightLimit(min, max)
    }

}
