package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.Renderer
import java.awt.Color
import java.awt.geom.Path2D

data class Triangle(
  var p0: Vector3 = Vector3(),
  var p1: Vector3 = Vector3(),
  var p2: Vector3 = Vector3(),
  var color: Color = Color.WHITE
) {

  fun render(renderer: Renderer) {
    val p = Path2D.Double()

    p.moveTo(p0.x, p0.y)
    p.lineTo(p1.x, p1.y)
    p.lineTo(p2.x, p2.y)
    p.closePath()

    renderer.g2d.color = color
    //renderer.g2d.draw(p)
    renderer.g2d.fill(p)
  }
}