package me.sagirii.worldborder.config

case class PluginConfig(
    borderCheckInterval: Long,
    denyEnderPearl: Boolean,
    portalRedirection: Boolean,
    preventBlockPlace: Boolean,
    preventMobSpawn: Boolean,
    knockbackPower: Double,
    borders: Map[String, BorderConfig]
)
