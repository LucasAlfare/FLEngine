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
  }

  fun render(renderer: Renderer) {
    val p = Path2D.Double()

    p.moveTo(p0.x, p0.y)
    p.lineTo(p1.x, p1.y)
    p.lineTo(p2.x, p2.y)
    p.closePath()

    renderer.g2d.color = color
    renderer.g2d.fill(p)
  }
}