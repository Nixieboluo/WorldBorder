package me.sagirii.worldborder.config

case class BorderConfig(world: String, shape: BorderShape)

object BorderShapeType extends Enumeration:

    type ShapeType = Value

    val RECTANGLE: Value = Value

    def fromString(shape: String): ShapeType = shape match
    case "RECTANGLE" => RECTANGLE
    case _           => throw new IllegalArgumentException(s"Unknown shape type: $shape")

sealed trait BorderShape:

    def shapeType: BorderShapeType.ShapeType

case class Rectangle(options: RectangleOptions) extends BorderShape:

    override def shapeType = BorderShapeType.RECTANGLE

case class RectangleOptions(xMin: Int, xMax: Int, zMin: Int, zMax: Int)

object RectangleOptions:

    def apply(xMin: Int, xMax: Int, zMin: Int, zMax: Int): RectangleOptions =
        require(xMin < xMax, "xMin must be less than xMax")
        require(zMin < zMax, "zMin must be less than zMax")
        new RectangleOptions(xMin, xMax, zMin, zMax)
