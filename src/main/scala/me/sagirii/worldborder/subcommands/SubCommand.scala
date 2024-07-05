package me.sagirii.worldborder.subcommands

import org.bukkit.command.CommandSender

trait SubCommand {

    val name: String

    val permission: Option[String]

    val paramNums: Option[List[Int]]

    val helpText: String

    def execute(sender: CommandSender, params: List[String]): Boolean

    def showHelp(sender: CommandSender): Unit = sender.sendMessage(this.helpText)

}
