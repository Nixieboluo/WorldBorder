package me.sagirii.worldborder.listener

import me.sagirii.worldborder.WorldBorderPlugin
import me.sagirii.worldborder.utility.BorderUtility.executeIfOutsideBorders
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerPortalEvent
import org.bukkit.event.player.PlayerTeleportEvent

object WorldBorderListener extends Listener {

    @EventHandler(EventPriority.LOWEST, ignoreCancelled = true)
    def onPlayerTeleport(event: PlayerTeleportEvent): Unit = {
        val toLoc   = event.getTo.clone()
        val toWorld = toLoc.getWorld.getName

        executeIfOutsideBorders(toLoc, toWorld) {
            (dx, dy, dz) =>
                if WorldBorderPlugin.config.denyEnderPearl &&
                event.getCause == PlayerTeleportEvent.TeleportCause.ENDER_PEARL
                then event.setCancelled(true)

                event.setTo(toLoc.add(-dx, -dy, -dz))
        }
    }

    @EventHandler(EventPriority.LOWEST, ignoreCancelled = true)
    def onPlayerPortal(event: PlayerPortalEvent): Unit = {
        if !WorldBorderPlugin.config.portalRedirection then return

        val toLoc   = event.getTo.clone()
        val toWorld = toLoc.getWorld.getName

        executeIfOutsideBorders(toLoc, toWorld) {
            (dx, dy, dz) => event.setTo(toLoc.add(-dx, -dy, -dz))
        }
    }

    def unregister(): Unit =
        HandlerList.unregisterAll(this)

}
