package lucasalfare.basicappengine.math.geometry

import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.math.Vector3

/**
 * Class used to hold triangles that can represent an
 * 3D shape, such as Cubes, Planes, Characters.
 */
class Mesh(
    var triangles: Array<Triangle>,
    var position: Vector3 = Vector3(),
    var rotation: Vector3 = Vector3(),
    var scale: Double = 1.0
) {

  fun update() {
    /*
    all triangles of this mesh are updated based
    in the same transformation supplied to this
    instance, then the final mesh draw can be
    consistent
    */
    triangles.forEach { it.update(position, rotation, scale) }
  }

  fun render(renderer: Renderer) {
    triangles.forEach {
      if (it.normal < 0) {
        it.render(renderer)
      }
    }
  }
}
