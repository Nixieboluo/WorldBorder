package me.sagirii.worldborder.utility

import me.sagirii.worldborder.config.BorderConfig
import me.sagirii.worldborder.config.Rectangle
import org.bukkit.Location

object BoundUtility {

    def withinBound(loc: Location, border: BorderConfig): Boolean = {
        val withinShape = border.shape match {
        case Rectangle(options) =>
            !(
              loc.getX < options.xMin || loc.getX > options.xMax
                  || loc.getZ < options.zMin || loc.getZ > options.zMax
            )
        case _ => false
        }

        val withinHeight =
            loc.getY >= border.options.heightLimit.min && loc.getY <= border.options.heightLimit.max

        withinShape && withinHeight
    }

    def distanceToBound(loc: Location, border: BorderConfig): (Double, Double, Double) = {
        val (distanceX, distanceZ) = border.shape match {
        case Rectangle(options) =>
            val toXMin = loc.getX - options.xMin
            val toXMax = loc.getX - options.xMax
            val dx =
                // Inside the boundary
                if loc.getX > options.xMin && loc.getX < options.xMax then 0.0
                // Outside the boundary
                else if Math.abs(toXMin) > Math.abs(toXMax) then toXMax
                else toXMin

            val toZMin = loc.getZ - options.zMin
            val toZMax = loc.getZ - options.zMax
            val dz =
                // Inside the boundary
                if loc.getZ > options.zMin && loc.getZ < options.zMax then 0.0
                // Outside the boundary
                else if Math.abs(toZMin) > Math.abs(toZMax) then toZMax
                else toZMin

            (dx, dz)
        }

        val yPos = loc.getY
        val distanceY =
            if yPos > border.options.heightLimit.max then yPos - border.options.heightLimit.max
            else if yPos < border.options.heightLimit.min then yPos - border.options.heightLimit.min
            else 0

        (distanceX, distanceY, distanceZ)
    }

}
