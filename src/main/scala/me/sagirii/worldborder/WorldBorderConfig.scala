package me.sagirii.worldborder

import me.sagirii.worldborder.config.*

import scala.jdk.CollectionConverters.*

object WorldBorderConfig:

    def load(plugin: WorldBorderPlugin): PluginConfig =
        plugin.reloadConfig()
        val fileConfig = plugin.getConfig

        // Borders
        val bordersSection = fileConfig.getConfigurationSection("borders")
        if bordersSection == null then
            throw new Exception(
              "Section \"borders\" is not found in the config, please check your configuration file."
            )

        val bordersConfig: Map[String, BorderConfig] = {
            for (name <- bordersSection.getKeys(false).asScala.toSeq) yield
                val borderItemSection = bordersSection.getConfigurationSection(name)
                val world             = borderItemSection.getString("world")
                val optionsSection    = borderItemSection.getConfigurationSection("options")
                val shape = BorderShapeType.fromString(borderItemSection.getString("shape"))
                val shapeConfig =
                    shape match
                    case BorderShapeType.RECTANGLE => Rectangle(RectangleOptions(
                          optionsSection.getInt("xMin"),
                          optionsSection.getInt("xMax"),
                          optionsSection.getInt("zMin"),
                          optionsSection.getInt("zMax")
                        ))
                    end match

                name -> BorderConfig(world, shapeConfig)
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

    end load

    def save(plugin: WorldBorderPlugin, config: PluginConfig): Unit =
        val fileConfig = plugin.getConfig

        fileConfig.set("borderCheckInterval", config.borderCheckInterval)
        fileConfig.set("denyEnderPearl", config.denyEnderPearl)
        fileConfig.set("portalRedirection", config.portalRedirection)
        fileConfig.set("preventBlockPlace", config.preventBlockPlace)
        fileConfig.set("preventMobSpawn", config.preventMobSpawn)
        fileConfig.set("knockbackPower", config.knockbackPower)

        fileConfig.set("borders", null)
        for (name, border) <- config.borders do
            val shape = border.shape.shapeType
            fileConfig.set(s"borders.$name.world", border.world)
            fileConfig.set(s"borders.$name.shape", shape.toString)
            border.shape match
            case Rectangle(options) =>
                fileConfig.set(s"borders.$name.options.xMin", options.xMin)
                fileConfig.set(s"borders.$name.options.xMax", options.xMax)
                fileConfig.set(s"borders.$name.options.zMin", options.zMin)
                fileConfig.set(s"borders.$name.options.zMax", options.zMax)

        plugin.saveConfig()

    end save

end WorldBorderConfig
