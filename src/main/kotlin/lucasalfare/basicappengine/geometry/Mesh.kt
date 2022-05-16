package lucasalfare.basicappengine.geometry

import lucasalfare.basicappengine.Handleable
import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.Texture
import lucasalfare.basicappengine.math.Vector3

/**
 * Class used to hold triangles that can represent an
 * 3D shape, such as Cubes, Planes, Humanoid Characters and so on.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Mesh(
  var triangles: Array<Triangle>,
  var position: Vector3 = Vector3(),
  var rotation: Vector3 = Vector3(),
  var scale: Float = 1f,
  var texture: Texture? = null
) : Handleable {

  init {
    triangles.forEach {
      it.targetTexture = texture
      it.defineRenderMode()
      println(it)
    }
  }

  override fun update(vararg args: Any) {
    triangles.sortBy { it.averageZ }

    /*
    all triangles of this mesh are updated based
    in the same transformation supplied to this
    instance, then the final mesh drawing can be
    consistent
    */
    triangles.forEach { it.update(position, rotation, scale) }
  }

  override fun render(r: Renderer) {
    triangles.forEach {
      if (it.normal > 0) {
        it.render(r)
      }
    }
  }
}
