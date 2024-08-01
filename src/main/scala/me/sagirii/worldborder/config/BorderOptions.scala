package me.sagirii.worldborder.config

case class BorderOptions(heightLimit: BorderHeightLimit)

case class BorderHeightLimit(min: Int, max: Int)
