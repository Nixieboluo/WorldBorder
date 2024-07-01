package me.sagirii.worldborder.subcommands

import me.sagirii.worldborder.WorldBorderConfig
import me.sagirii.worldborder.WorldBorderPlugin
import org.bukkit.command.CommandSender

class SubCommandHelp extends SubCommand:

    override val name = "help"

    override val permission = None

    override val paramNums = None

    override val helpText = "Shows the help message of World Border."

    override def execute(sender: CommandSender, params: List[String]): Boolean =
        sender.sendMessage(this.helpText)
        sender.sendMessage(WorldBorderPlugin.config.borders.toString())
        true

end SubCommandHelp
