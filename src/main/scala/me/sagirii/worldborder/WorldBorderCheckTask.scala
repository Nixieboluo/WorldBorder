package me.sagirii.worldborder

import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import me.sagirii.worldborder.utility.TpUtility
import org.bukkit.Bukkit
import org.bukkit.util.Vector
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

                // Knock players back
                val knockPower = WorldBorderPlugin.config.knockbackPower
                if knockPower > 0 then
                    val velocityNormalizer =
                        Math.max(distanceX, distanceZ) / knockPower
                    player.setVelocity(new Vector(
                      distanceX / velocityNormalizer,
                      knockPower,
                      distanceZ / velocityNormalizer
                    ))
            }

        end for

    end run

end WorldBorderCheckTask
