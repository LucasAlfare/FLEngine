package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY

open class Mesh(
  var triangles: Array<Triangle> = arrayOf(),
  var position: Vector3 = Vector3(),
  var rotation: Vector3 = Vector3(),
  var scaleFactor: Double = 1.0
) {

  private val drawTriangles = Array(triangles.size) { Triangle() }

  fun update(deltaTime: Float, callback: (deltaTime: Float) -> Unit = {}) {
    callback(deltaTime)

    triangles.forEachIndexed { index, originalTriangle ->
      drawTriangles[index].p0 =
        originalTriangle.p0
          .rotate(rotation)
          .translateTo(position)
          .scale(scaleFactor)
          .toPerspective()
          .centerInBound(ResolutionX, ResolutionY)

      drawTriangles[index].p1 =
        originalTriangle.p1
          .rotate(rotation)
          .translateTo(position)
          .scale(scaleFactor)
          .toPerspective()
          .centerInBound(ResolutionX, ResolutionY)

      drawTriangles[index].p2 =
        originalTriangle.p2
          .rotate(rotation)
          .translateTo(position)
          .scale(scaleFactor)
          .toPerspective()
          .centerInBound(ResolutionX, ResolutionY)

      drawTriangles[index].color = originalTriangle.color
    }
  }

  fun render(renderer: Renderer) {
    drawTriangles.forEach {
      it.render(renderer)
    }
  }
}
