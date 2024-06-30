package me.sagirii.worldborder.subcommands

import org.bukkit.command.CommandSender

class SubCommandHelp extends SubCommand:

    override val name = "help"

    override val permission = None

    override val paramNums = Some(1 :: 2 :: Nil)

    override val helpText = "Shows the help message of World Border."

    override def execute(sender: CommandSender, params: List[String]): Boolean =
        sender.sendMessage(params.toString())
        true

end SubCommandHelp
