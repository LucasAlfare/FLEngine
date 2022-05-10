package lucasalfare.basicappengine.math.geometry

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.math.Vector3

open class Mesh(
    var triangles: Array<Triangle>,
    var position: Vector3 = Vector3(),
    var rotation: Vector3 = Vector3(),
    var scale: Double = 1.0
) {

  fun update() {
    triangles.forEach {
      it.update(
        rotation = rotation,
        position = position,
        scale = scale
      )
    }
  }

  fun render(renderer: Renderer) {
    triangles.forEach {
      if (it.normal < 0) {
        it.render(renderer)
      }
    }
  }
}
