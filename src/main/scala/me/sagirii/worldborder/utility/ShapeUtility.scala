package me.sagirii.worldborder.utility

import me.sagirii.worldborder.config.BorderShape
import me.sagirii.worldborder.config.Rectangle
import org.bukkit.Location

object ShapeUtility:

    def withinShape(loc: Location, border: BorderShape): Boolean =
        border match
        case Rectangle(options) =>
            !(loc.getX < options.xMin || loc.getX > options.xMax ||
                loc.getZ < options.zMin || loc.getZ > options.zMax)
        case _ => false
        end match
    end withinShape

    def distanceToShape(loc: Location, border: BorderShape): (Double, Double) =
        border match
        case Rectangle(options) =>
            val toXMin = loc.getX - options.xMin
            val toXMax = loc.getX - options.xMax
            val distanceX =
                // Inside the boundary
                if loc.getX > options.xMin && loc.getX < options.xMax then 0.0
                // Outside the boundary
                else if Math.abs(toXMin) > Math.abs(toXMax) then toXMax
                else toXMin

            val toZMin = loc.getZ - options.zMin
            val toZMax = loc.getZ - options.zMax
            val distanceZ =
                // Inside the boundary
                if loc.getZ > options.zMin && loc.getZ < options.zMax then 0.0
                // Outside the boundary
                else if Math.abs(toZMin) > Math.abs(toZMax) then toZMax
                else toZMin

            (distanceX, distanceZ)
        end match
    end distanceToShape

end ShapeUtility
