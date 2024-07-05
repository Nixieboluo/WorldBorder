package me.sagirii.worldborder.utility

import me.sagirii.worldborder.WorldBorderPlugin
import me.sagirii.worldborder.config.BorderConfig
import me.sagirii.worldborder.utility.ShapeUtility.distanceToShape
import me.sagirii.worldborder.utility.ShapeUtility.withinShape
import org.bukkit.Location

object BorderUtility {

    def bordersInWorld(world: String): Map[String, BorderConfig] =
        WorldBorderPlugin.config.borders.filter((name, border) => border.world == world)

    def executeIfOutsideBorders(
        loc: Location,
        world: String
    )(callback: (Double, Double) => Unit): Unit = {
        val borders         = bordersInWorld(world).values.toSeq
        val withinAnyBorder = borders.exists(border => withinShape(loc, border.shape))

        if borders.nonEmpty && !withinAnyBorder then {
            // Distance to the closest border
            val (distanceX, distanceZ) = borders
                .map(border => distanceToShape(loc, border.shape))
                .minBy((x, z) => Math.pow(x, 2) + Math.pow(z, 2))

            callback(distanceX, distanceZ)
        }
    }

    def executeIfOutsideBorders(
        loc: Location,
        world: String
    )(callback: () => Unit): Unit = {
        val borders         = bordersInWorld(world).values.toSeq
        val withinAnyBorder = borders.exists(border => withinShape(loc, border.shape))

        if borders.nonEmpty && !withinAnyBorder then callback()
    }

}
