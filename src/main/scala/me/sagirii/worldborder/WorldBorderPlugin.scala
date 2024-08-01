package me.sagirii.worldborder

import me.sagirii.worldborder.WorldBorderPlugin.config
import me.sagirii.worldborder.WorldBorderPlugin.config_=
import me.sagirii.worldborder.WorldBorderPlugin.plugin
import me.sagirii.worldborder.config.Config
import me.sagirii.worldborder.config.WorldBorderConfig
import me.sagirii.worldborder.listener.BlockPlaceListener
import me.sagirii.worldborder.listener.CreatureSpawnListener
import me.sagirii.worldborder.listener.WorldBorderListener
import org.bukkit.plugin.java.JavaPlugin

object WorldBorderPlugin {

    @volatile
    private var _plugin: WorldBorderPlugin = _

    @volatile
    private var _config: Config = _

    def plugin: WorldBorderPlugin = _plugin

    def config: Config = _config

    private def plugin_=(plugin: WorldBorderPlugin): Unit = _plugin = plugin

    private def config_=(config: Config): Unit = _config = config

}

class WorldBorderPlugin extends JavaPlugin {

    override def onEnable(): Unit = {
        if plugin == null then plugin = this

        // Load configuration and register tasks and listeners
        this.saveDefaultConfig()
        updateConfig(WorldBorderConfig.load(plugin))
        WorldBorderCheckTask.runTaskTimer(this, 0L, config.borderCheckInterval)

        this.getCommand("border").setExecutor(WorldBorderCommand)
    }

    def updateConfig(newConfig: Config): Unit = {
        config = newConfig

        // Save config to disk
        WorldBorderConfig.save(plugin, newConfig)

        // Register events
        WorldBorderListener.unregister()
        this.getServer.getPluginManager.registerEvents(WorldBorderListener, this)

        BlockPlaceListener.unregister()
        if config.preventBlockPlace then this.getServer.getPluginManager.registerEvents(
          BlockPlaceListener,
          this
        )

        CreatureSpawnListener.unregister()
        if config.preventMobSpawn then this.getServer.getPluginManager.registerEvents(
          CreatureSpawnListener,
          this
        )

        getLogger.info("Configuration updated.")
    }

}
