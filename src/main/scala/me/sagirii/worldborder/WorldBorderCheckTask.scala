package me.sagirii.worldborder

import me.sagirii.worldborder.utility.ShapeUtility.distanceToShape
import me.sagirii.worldborder.utility.ShapeUtility.withinShape
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

import scala.jdk.CollectionConverters.*

class WorldBorderCheckTask extends BukkitRunnable:

    override def run(): Unit =
        val players = Bukkit.getServer.getOnlinePlayers.asScala.toList

        for player <- players do
            val bordersInWorld = WorldBorderPlugin.config.borders
                .filter((name, border) => border.world == player.getWorld.getName)

            val loc   = player.getLocation.clone()
            val world = player.getWorld.getName

            val withinBorder =
                // If the location is inside any border in that world, we assume it was inside the border.
                bordersInWorld.exists((name, border) => withinShape(loc, border.shape))

            // Teleport player back into the closest border
            if !withinBorder then
                val (distanceX, distanceZ) =
                    bordersInWorld
                        .map((name, border) => distanceToShape(loc, border.shape))
                        .minBy((x, z) => Math.pow(x, 2) + Math.pow(z, 2))

                player.teleport(loc.add(-distanceX, 0.0, -distanceZ))
            end if
        end for

    end run

end WorldBorderCheckTask
