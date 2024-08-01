package me.sagirii.worldborder.utility

import me.sagirii.worldborder.WorldBorderPlugin
import me.sagirii.worldborder.config.BorderConfig
import me.sagirii.worldborder.utility.BoundUtility.distanceToBound
import me.sagirii.worldborder.utility.BoundUtility.withinBound
import org.bukkit.Location

object BorderUtility {

    def bordersInWorld(world: String): Map[String, BorderConfig] =
        WorldBorderPlugin.config.borders.filter((name, border) => border.world == world)

    def executeIfOutsideBorders(
        loc: Location,
        world: String
    )(callback: (Double, Double, Double) => Unit): Unit = {
        val borders         = bordersInWorld(world).values.toSeq
        val withinAnyBorder = borders.exists(border => withinBound(loc, border))

        if borders.nonEmpty && !withinAnyBorder then {
            // Distance to the closest border
            val (distanceX, distanceY, distanceZ) = borders
                .map(border => distanceToBound(loc, border))
                .minBy((dx, dy, dz) => Math.pow(dx, 2) + Math.pow(dz, 2))

            callback(distanceX, distanceY, distanceZ)
        }
    }

    def executeIfOutsideBorders(
        loc: Location,
        world: String
    )(callback: () => Unit): Unit = {
        val borders         = bordersInWorld(world).values.toSeq
        val withinAnyBorder = borders.exists(border => withinBound(loc, border))

        if borders.nonEmpty && !withinAnyBorder then callback()
    }

}
