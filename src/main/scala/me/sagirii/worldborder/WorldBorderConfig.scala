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
                val shape             = BorderShapeType.fromString(borderItemSection.getString("shape"))
                val optionsSection    = borderItemSection.getConfigurationSection("options")
                val shapeConfig =
                    shape match
                    case BorderShapeType.RECTANGLE =>
                        Rectangle(
                          RectangleOptions(
                            optionsSection.getInt("xMin"),
                            optionsSection.getInt("xMax"),
                            optionsSection.getInt("zMin"),
                            optionsSection.getInt("zMax")
                          )
                        )
                    end match
                end shapeConfig

                name -> BorderConfig(world, shapeConfig)
        }.toMap

        PluginConfig(bordersConfig)

    end load

end WorldBorderConfig
