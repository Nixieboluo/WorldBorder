package me.sagirii.worldborder

import org.bukkit.entity.Player

object WorldBorderPermission {

    def hasPermission(player: Player, perm: String, notify: Boolean = true) = {
        val has = player.hasPermission(s"worldborder.$perm")
        if notify && !has then player.sendMessage("You do not have sufficient permissions.")
        has
    }

}
