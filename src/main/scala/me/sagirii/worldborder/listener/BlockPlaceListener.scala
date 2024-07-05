package me.sagirii.worldborder.listener

import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

object BlockPlaceListener extends Listener {

    @EventHandler(EventPriority.LOWEST, ignoreCancelled = true)
    def onBlockPlace(event: BlockPlaceEvent): Unit = {
        val loc   = event.getBlockPlaced.getLocation()
        val world = event.getBlockPlaced.getWorld.getName

        executeIfOutsideBorders(loc, world) {
            () => event.setCancelled(true)
        }
    }

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
