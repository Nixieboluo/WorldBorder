package me.sagirii.worldborder

import me.sagirii.worldborder.subcommands.SubCommand
import me.sagirii.worldborder.subcommands.SubCommandHelp
import me.sagirii.worldborder.subcommands.SubCommandReload
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object WorldBorderCommand extends CommandExecutor {

    private val subCommandsList = List(
      SubCommandHelp,
      SubCommandReload
    )

    val subCommands: Map[String, SubCommand] =
        subCommandsList.map(cmd => cmd.name -> cmd).toMap

    override def onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        params: Array[String]
    ): Boolean = {
        val cmd = {
            params.headOption match {
            case Some(cmdString) if subCommands.contains(cmdString) => subCommands(cmdString)
            // Fallback empty subcommands to "help"
            case None => subCommands("help")
            // Do not accept commands that are not exist
            case Some(cmdString) =>
                sender.sendMessage(s"Subcommand $cmdString not found, use /border help for help.")
                null
            }
        }

        if cmd == null then return true

        val hasPermission = sender match {
        // Ignore commands that does not require a permission
        case player: Player if cmd.permission.isDefined =>
            cmd.permission.exists(WorldBorderPermission.hasPermission(player, _))
        // Console always have permissions
        case _ => true
        }

        if !hasPermission then return true

        // Checking params count
        val cmdParams = params.toList.drop(1)

        val paramsCorrect = cmd.paramNums match {
        case None                                                    => true
        case Some(paramNums) if paramNums.contains(cmdParams.length) => true
        case _                                                       => false
        }

        if paramsCorrect then cmd.execute(sender, cmdParams)
        else {
            sender.sendMessage(s"You have provided invalid number of params.")
            cmd.showHelp(sender)
        }

        true

    }

}
