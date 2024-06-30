package me.sagirii.worldborder

import org.bukkit.permissions.PermissionDefault
import org.bukkit.plugin.java.JavaPlugin

class WorldBorderPlugin extends JavaPlugin:

    override def onEnable(): Unit =
        getLogger.info("Started")
