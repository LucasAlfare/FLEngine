package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY
import java.awt.Color
import java.awt.geom.Path2D

/**
 * The points here must be declared in CLOCKWISE
 * order consistently.
 */
data class Triangle(
  var p0: Vector3 = Vector3(),
  var p1: Vector3 = Vector3(),
  var p2: Vector3 = Vector3(),
  var color: Color = Color.WHITE
) {

  var averageZ = 0.0
  var normal = 0.0

  private val vertices = Array(3) { Vector3() }

  fun update(
    source: Triangle,
    position: Vector3 = Vector3(),
    rotation: Vector3 = Vector3(),
    scaleFactor: Double = 1.0
  ) {
    color = source.color

    //transforms the triangle points
    p0 = source.p0.rotate(rotation).translateTo(position).scale(scaleFactor)
    p1 = source.p1.rotate(rotation).translateTo(position).scale(scaleFactor)
    p2 = source.p2.rotate(rotation).translateTo(position).scale(scaleFactor)

    //calculate averageZ before other stuff
    averageZ = (p0.z + p1.z + p2.z) / 3

    //apply perspective
    p0 = p0.toPerspective().centerInBound(ResolutionX, ResolutionY)
    p1 = p1.toPerspective().centerInBound(ResolutionX, ResolutionY)
    p2 = p2.toPerspective().centerInBound(ResolutionX, ResolutionY)

    //then calculates the normal
    normal = (p1.x - p0.x) * (p2.y - p0.y) - (p1.y - p0.y) * (p2.x - p0.x)

    vertices[0] = p0
    vertices[1] = p1
    vertices[2] = p2
    vertices.sortBy { it.y }
  }

  fun render(renderer: Renderer) {
    renderer.g2d.color = color
    rasterize(renderer)
  }

  private fun rasterize(renderer: Renderer) {
    val p = Path2D.Double()

    fun rasterizeFlatBottom(v1: Vector3, v2: Vector3, v3: Vector3) {
      val inverseSlope1 = (v2.x - v1.x) / (v2.y - v1.y)
      val inverseSlope2 = (v3.x - v1.x) / (v3.y - v1.y)
      var currX1 = v1.x
      var currX2 = v1.x
      var scanLineY = v1.y
      while (scanLineY <= v2.y) {
        p.moveTo(currX1, scanLineY)
        p.lineTo(currX2, scanLineY)

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
        p.moveTo(currX1, scanLineY)
        p.lineTo(currX2, scanLineY)

        currX1 -= inverseSlope1
        currX2 -= inverseSlope2

        scanLineY--
      }
    }

    if (vertices[1].y == vertices[2].y) {
      rasterizeFlatBottom(vertices[0], vertices[1], vertices[2])
    } else if (vertices[0].y == vertices[1].y) {
      rasterizeFlatTop(vertices[0], vertices[1], vertices[2])
    } else {
      val vertex4 = Vector3(
        x = (vertices[0].x + ((vertices[1].y - vertices[0].y) / (vertices[2].y - vertices[0].y)) * (vertices[2].x - vertices[0].x)),
        y = vertices[1].y
      )

      rasterizeFlatTop(vertices[1], vertex4, vertices[2])
      rasterizeFlatBottom(vertices[0], vertices[1], vertex4)
    }

    renderer.g2d.draw(p)
  }
}