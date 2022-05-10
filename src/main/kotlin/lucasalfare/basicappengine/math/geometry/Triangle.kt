package lucasalfare.basicappengine.math.geometry

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY
import lucasalfare.basicappengine.math.Vector3
import java.awt.Color

/**
 * The points here must be declared in CLOCKWISE
 * order consistently.
 */
class Triangle(
  var p0: Vector3,
  var p1: Vector3,
  var p2: Vector3,
  var color: Color
) {

  var normal = 0.0

  private val transformedPoints = Array(3) { Vector3() }

  private lateinit var a: Vector3
  private lateinit var b: Vector3
  private lateinit var c: Vector3

  fun update(
    position: Vector3,
    rotation: Vector3,
    scale: Double
  ) {
    //transforms and apply perspective to this triangle points
    a = p0
      // transforms...
      .translateTo(position).rotate(rotation).scale(scale)
      // ...and applies perspective
      .toPerspective().centerInBound(ResolutionX, ResolutionY)

    b = p1
      // transforms...
      .translateTo(position).rotate(rotation).scale(scale)
      // ...and applies perspective
      .toPerspective().centerInBound(ResolutionX, ResolutionY)

    c = p2
      // transforms...
      .translateTo(position).rotate(rotation).scale(scale)
      // ...and applies perspective
      .toPerspective().centerInBound(ResolutionX, ResolutionY)

    //then calculates the normal
    normal = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)

    // always sorts the points by Y value, helper to rasterize
    transformedPoints[0] = a
    transformedPoints[1] = b
    transformedPoints[2] = c
    transformedPoints.sortBy { it.y }
  }

  fun render(renderer: Renderer) {
    rasterize(renderer)
  }

  private fun rasterize(renderer: Renderer) {
    a = transformedPoints[0]
    b = transformedPoints[1]
    c = transformedPoints[2]

    fun rasterizeFlatBottom(v1: Vector3, v2: Vector3, v3: Vector3) {
      val inverseSlope1 = (v2.x - v1.x) / (v2.y - v1.y)
      val inverseSlope2 = (v3.x - v1.x) / (v3.y - v1.y)
      var currX1 = v1.x
      var currX2 = v1.x
      var scanLineY = v1.y
      while (scanLineY <= v2.y) {
        renderer.drawLine(currX1.toInt(), scanLineY.toInt(), currX2.toInt(), scanLineY.toInt(), color.rgb)
        currX1 += inverseSlope1
        currX2 += inverseSlope2
        scanLineY++
      }
    }

    fun rasterizeFlatTop(v1: Vector3, v2: Vector3, v3: Vector3) {
      val inverseSlope1 = ((v3.x - v1.x) / (v3.y - v1.y))
      val inverseSlope2 = ((v3.x - v2.x) / (v3.y - v2.y))
      var currX1 = v3.x
      var currX2 = v3.x

      var scanLineY = v3.y
      while (scanLineY > v1.y) {
        renderer.drawLine(currX1.toInt(), scanLineY.toInt(), currX2.toInt(), scanLineY.toInt(), color.rgb)
        currX1 -= inverseSlope1
        currX2 -= inverseSlope2
        scanLineY--
      }
    }

    if (b.y == c.y) {
      rasterizeFlatBottom(a, b, c)
    } else if (a.y == b.y) {
      rasterizeFlatTop(a, b, c)
    } else {
      val vertex4 = Vector3(
        x = (a.x + ((b.y - a.y) / (c.y - a.y)) * (c.x - a.x)),
        y = b.y
      )

      rasterizeFlatTop(b, vertex4, c)
      rasterizeFlatBottom(a, b, vertex4)
    }
  }

  override fun toString() = "Original points=($p0 | $p1 | $p2); CurrentTransformedPoints=($a | $b | $c)"
}