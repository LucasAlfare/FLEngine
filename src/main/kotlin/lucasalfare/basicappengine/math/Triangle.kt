package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY
import java.awt.Color
import java.awt.geom.Path2D

class Triangle(
  var p0: Vector3 = Vector3(0.0, -200.0, 0.0),
  var p1: Vector3 = Vector3(200.0, 200.0, 0.0),
  var p2: Vector3 = Vector3(-200.0, 200.0, 0.0),
  var color: Color = Color.WHITE
) {

  private val position = Vector3(0.0, 0.0, 400.0)
  private val rotation = Vector3(0.0, 0.0, 0.0)
  private val drawPoints = Array(3) { Vector3() }

  fun update(deltaTime: Float) {
    rotation.y += 1 * deltaTime

    drawPoints[0] = p0.rotate(rotation).translateTo(position).toPerspective().centerInBound(ResolutionX, ResolutionY)
    drawPoints[1] = p1.rotate(rotation).translateTo(position).toPerspective().centerInBound(ResolutionX, ResolutionY)
    drawPoints[2] = p2.rotate(rotation).translateTo(position).toPerspective().centerInBound(ResolutionX, ResolutionY)
  }

  fun render(renderer: Renderer) {
    val p = Path2D.Double()

    p.moveTo(drawPoints[0].x, drawPoints[0].y)
    p.lineTo(drawPoints[1].x, drawPoints[1].y)
    p.lineTo(drawPoints[2].x, drawPoints[2].y)
    p.closePath()

    renderer.g2d.color = color
    renderer.g2d.draw(p)

    renderer.setPixel((ResolutionX / 2).toInt(), (ResolutionY / 2).toInt(), Color.YELLOW.rgb)
  }
}