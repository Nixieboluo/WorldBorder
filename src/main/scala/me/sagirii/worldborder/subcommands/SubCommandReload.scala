package me.sagirii.worldborder.subcommands

import me.sagirii.worldborder.WorldBorderConfig
import me.sagirii.worldborder.WorldBorderPlugin
import me.sagirii.worldborder.WorldBorderPlugin.plugin
import org.bukkit.command.CommandSender

class SubCommandReload extends SubCommand:

    override val name = "reload"

    override val permission = Some("reload")

    override val paramNums = None

    override val helpText = "Reloads configuration from disk."

    override def execute(sender: CommandSender, params: List[String]): Boolean =
        val plugin = WorldBorderPlugin.plugin
        plugin.updateConfig(WorldBorderConfig.load(plugin))
        sender.sendMessage("Config reloaded from disk.")
        true

end SubCommandReload
