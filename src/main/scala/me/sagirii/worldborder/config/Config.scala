package me.sagirii.worldborder.config

case class Config(
    borderCheckInterval: Long,
    denyEnderPearl: Boolean,
    portalRedirection: Boolean,
    preventBlockPlace: Boolean,
    preventMobSpawn: Boolean,
    knockbackPower: Double,
    borders: Map[String, BorderConfig]
)

case class BorderConfig(world: String, shape: BorderShape, options: BorderOptions)
