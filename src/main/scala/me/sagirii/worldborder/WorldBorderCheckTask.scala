package me.sagirii.worldborder

import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import me.sagirii.worldborder.utility.TpUtility
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

import scala.jdk.CollectionConverters.*

object WorldBorderCheckTask extends BukkitRunnable:

    override def run(): Unit =
        val players = Bukkit.getServer.getOnlinePlayers.asScala.toList

        for player <- players do
            val loc   = player.getLocation.clone()
            val world = player.getWorld.getName

            // Teleport player back into the closest border
            executeIfOutsideBorders(loc, world) { (distanceX, distanceZ) =>
                TpUtility.teleport(player, loc.add(-distanceX, 0.0, -distanceZ))
            }
