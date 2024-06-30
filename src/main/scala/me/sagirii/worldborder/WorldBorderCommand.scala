package me.sagirii.worldborder

import me.sagirii.worldborder.WorldBorderCommand.subCommands
import me.sagirii.worldborder.subcommands.{SubCommand, SubCommandHelp}
import org.bukkit.command.{Command, CommandExecutor, CommandSender}

object WorldBorderCommand:

    private val subCommandsList = List(new SubCommandHelp())

    val subCommands: Map[String, SubCommand] =
        subCommandsList.map(cmd => cmd.name -> cmd).toMap

end WorldBorderCommand

class WorldBorderCommand extends CommandExecutor:

    override def onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        params: Array[String]
    ): Boolean =
        // Fallback unknown or empty subcommands to "help"
        val cmd =
            params.headOption match
            case Some(cmdString) =>
                if subCommands.contains(cmdString) then subCommands(cmdString)
                else
                    sender.sendMessage(s"Subcommand $cmdString not found, showing usage.")
                    subCommands("help")
            case None =>
                subCommands("help")
            end match
        end cmd

        val cmdParams = params.toList.drop(1)

        // Checking
        cmd.paramNums match
        case None => cmd.execute(sender, cmdParams)
        case Some(paramNums) =>
            if paramNums.contains(cmdParams.length) then cmd.execute(sender, cmdParams)
            else
                sender.sendMessage(
                  s"You have provided invalid number of params."
                )
                cmd.showHelp(sender)
                true
            end if
        end match

    end onCommand

end WorldBorderCommand
