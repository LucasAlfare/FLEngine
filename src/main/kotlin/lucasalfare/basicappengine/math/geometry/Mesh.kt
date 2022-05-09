package lucasalfare.basicappengine.math.geometry

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.math.Vector3

open class Mesh(
    var triangles: Array<Triangle> = arrayOf(),
    var position: Vector3 = Vector3(),
    var rotation: Vector3 = Vector3(),
    var scale: Double = 1.0
) {

  private val drawTriangles = Array(triangles.size) { Triangle() }

  fun update(deltaTime: Float, callback: (deltaTime: Float) -> Unit = {}) {
    callback(deltaTime)

    triangles.forEachIndexed { index, originalTriangle ->
      drawTriangles[index].update(
        source = originalTriangle,
        rotation = rotation,
        position = position,
        scaleFactor = scale
      )
    }

    //drawTriangles.sortBy { it.averageZ }
  }

  fun render(renderer: Renderer) {
    drawTriangles.forEach {
      if (it.normal < 0) {
        it.render(renderer)
      }
    }
  }
}
