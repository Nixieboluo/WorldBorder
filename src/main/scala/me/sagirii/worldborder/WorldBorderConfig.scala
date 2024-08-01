package me.sagirii.worldborder

import me.sagirii.worldborder.config.*
import scala.jdk.CollectionConverters.*

object WorldBorderConfig {

    def load(plugin: WorldBorderPlugin): PluginConfig = {
        plugin.reloadConfig()
        val fileConfig = plugin.getConfig

        // Borders
        val bordersSection = fileConfig.getConfigurationSection("borders")
        if bordersSection == null then throw new Exception(
          "Section \"borders\" is not found in the config, please check your configuration file."
        )

        val bordersConfig: Map[String, BorderConfig] = {
            for (name <- bordersSection.getKeys(false).asScala.toSeq) yield {
                val borderItemSection = bordersSection.getConfigurationSection(name)
                val world             = borderItemSection.getString("world")

                val shape = BorderShapeType.fromString(borderItemSection.getString("shape"))
                val shapeOptionsSection = borderItemSection.getConfigurationSection("shapeOptions")
                val shapeConfig = {
                    shape match {
                    case BorderShapeType.RECTANGLE => Rectangle(RectangleOptions(
                          shapeOptionsSection.getInt("xMin"),
                          shapeOptionsSection.getInt("xMax"),
                          shapeOptionsSection.getInt("zMin"),
                          shapeOptionsSection.getInt("zMax")
                        ))
                    }
                }

                val commonOptionsSections = borderItemSection.getConfigurationSection("options")
                val commonOptions = BorderOptions(
                  BorderHeightLimit(
                    commonOptionsSections.getInt("yMin"),
                    commonOptionsSections.getInt("yMax")
                  )
                )

                name -> BorderConfig(world, shapeConfig, commonOptions)
            }
        }.toMap

        PluginConfig(
          borderCheckInterval = fileConfig.getLong("borderCheckInterval"),
          denyEnderPearl = fileConfig.getBoolean("denyEnderPearl"),
          portalRedirection = fileConfig.getBoolean("portalRedirection"),
          preventBlockPlace = fileConfig.getBoolean("preventBlockPlace"),
          preventMobSpawn = fileConfig.getBoolean("preventMobSpawn"),
          knockbackPower = fileConfig.getDouble("knockbackPower"),
          borders = bordersConfig
        )
    }

    def save(plugin: WorldBorderPlugin, config: PluginConfig): Unit = {
        val fileConfig = plugin.getConfig

        fileConfig.set("borderCheckInterval", config.borderCheckInterval)
        fileConfig.set("denyEnderPearl", config.denyEnderPearl)
        fileConfig.set("portalRedirection", config.portalRedirection)
        fileConfig.set("preventBlockPlace", config.preventBlockPlace)
        fileConfig.set("preventMobSpawn", config.preventMobSpawn)
        fileConfig.set("knockbackPower", config.knockbackPower)

        fileConfig.set("borders", null)
        for (name, border) <- config.borders do {
            fileConfig.set(s"borders.$name.world", border.world)

            // Shape options
            fileConfig.set(s"borders.$name.shape", border.shape.shapeType.toString)
            border.shape match {
            case Rectangle(options) =>
                fileConfig.set(s"borders.$name.shapeOptions.xMin", options.xMin)
                fileConfig.set(s"borders.$name.shapeOptions.xMax", options.xMax)
                fileConfig.set(s"borders.$name.shapeOptions.zMin", options.zMin)
                fileConfig.set(s"borders.$name.shapeOptions.zMax", options.zMax)
            }

            // Common options
            fileConfig.set(s"borders.$name.options.yMin", border.options.heightLimit.min)
            fileConfig.set(s"borders.$name.options.yMax", border.options.heightLimit.max)
        }

        plugin.saveConfig()
    }

}
