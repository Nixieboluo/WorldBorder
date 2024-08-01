package me.sagirii.worldborder

import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import me.sagirii.worldborder.utility.TpUtility
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import scala.jdk.CollectionConverters.*

object WorldBorderCheckTask extends BukkitRunnable {

    override def run(): Unit = {
        val players = Bukkit.getServer.getOnlinePlayers.asScala.toList

        for player <- players do {
            val loc   = player.getLocation.clone()
            val world = player.getWorld.getName

            // Teleport player back into the closest border
            executeIfOutsideBorders(loc, world) { (dx, dy, dz) =>
                TpUtility.teleport(player, loc.add(-dx, -dy, -dz))

                def knockTask: BukkitRunnable = () => {
                    // Knock players back
                    val knockPower = WorldBorderPlugin.config.knockbackPower
                    if knockPower > 0.0 then {
                        val velocityNormalizer =
                            Math.max(Math.abs(dx), Math.abs(dz)) / knockPower

                        val xVelocity =
                            if velocityNormalizer <= 0 then 0
                            else -dx / velocityNormalizer
                        val zVelocity =
                            if velocityNormalizer <= 0 then 0
                            else -dz / velocityNormalizer
                        // If the player is flying or riding something, do not throw the player up
                        val ride = player.getVehicle
                        val yVelocity =
                            if player.isFlying || ride != null then 0
                            // If the player is higher than the upper bound, throw down instead
                            else if dy > 0 then -knockPower
                            else knockPower

                        // Throw player and their rides back
                        val velocity = new Vector(xVelocity, yVelocity, zVelocity)
                        if ride != null then ride.setVelocity(velocity)
                        else {
                            player.setVelocity(velocity)
                        }
                    }
                }
                knockTask.runTaskLater(WorldBorderPlugin.plugin, 0L)
            }
        }

    }

}
