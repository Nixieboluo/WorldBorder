package me.sagirii.worldborder.utility

import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause
import org.bukkit.util.Vector

import scala.jdk.CollectionConverters.*

object TpUtility:

    def teleport(player: Player, location: Location): Boolean =
        // If the player is in a vehicle
        if player.isInsideVehicle then
            val ride = player.getVehicle
            player.leaveVehicle()
            if ride != null then
                // Move the ride to correct Y level and stop it
                val rideYOffset =
                    if ride.isInstanceOf[LivingEntity] then 0
                    else ride.getLocation().getY - location.getY

                val rideLoc = location.clone()
                rideLoc.setY(rideLoc.getY + rideYOffset)

                ride.setVelocity(new Vector(0, 0, 0))
                ride.teleport(rideLoc, TeleportCause.PLUGIN)

                // Remount player onto the vehicle
                ride.addPassenger(player)
            end if
        end if

        // If the player have something ride on them
        val passengers = player.getPassengers.asScala.toList
        if passengers.nonEmpty then
            player.eject()
            for passenger <- passengers do
                passenger.teleport(location, TeleportCause.PLUGIN)
                player.sendMessage("Your passengers have been ejected.")
        end if

        player.teleport(location, TeleportCause.PLUGIN)
    end teleport

end TpUtility
