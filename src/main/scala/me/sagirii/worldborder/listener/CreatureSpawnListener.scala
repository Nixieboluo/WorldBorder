package me.sagirii.worldborder.listener

import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

object CreatureSpawnListener extends Listener {

    @EventHandler(EventPriority.LOWEST, ignoreCancelled = true)
    def onCreatureSpawn(event: CreatureSpawnEvent): Unit = {
        val loc   = event.getEntity.getLocation()
        val world = loc.getWorld.getName

        executeIfOutsideBorders(loc, world) {
            () => event.setCancelled(true)
        }
    }

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
