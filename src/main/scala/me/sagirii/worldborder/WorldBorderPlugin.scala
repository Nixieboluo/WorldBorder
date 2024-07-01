package me.sagirii.worldborder

import me.sagirii.worldborder.WorldBorderPlugin.config
import me.sagirii.worldborder.WorldBorderPlugin.config_=
import me.sagirii.worldborder.WorldBorderPlugin.plugin
import me.sagirii.worldborder.config.PluginConfig
import org.bukkit.plugin.java.JavaPlugin

object WorldBorderPlugin:

    @volatile private var _plugin: WorldBorderPlugin = _

    @volatile private var _config: PluginConfig = _

    def plugin: WorldBorderPlugin = _plugin

    def config: PluginConfig = _config

    private def plugin_=(plugin: WorldBorderPlugin): Unit = _plugin = plugin

    private def config_=(config: PluginConfig): Unit = _config = config

end WorldBorderPlugin

class WorldBorderPlugin extends JavaPlugin:

    override def onEnable(): Unit =
        if plugin == null then plugin = this
        updateConfig(WorldBorderConfig.load(plugin))

        this.saveDefaultConfig()

        this.getCommand("border").setExecutor(new WorldBorderCommand)

    end onEnable

    def updateConfig(newConfig: PluginConfig): Unit =
        config = newConfig
        getLogger.info("Loaded configuration from the disk.")

end WorldBorderPlugin
