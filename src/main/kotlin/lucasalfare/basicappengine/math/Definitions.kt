package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.FieldOfView
import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import kotlin.math.PI
import kotlin.math.tan

fun getZ0() = (ResolutionX / 2.0) / tan((FieldOfView / 2.0) * PI / 180)

abstract class Mesh(
  val points: Array<Vector3> = arrayOf(),
  val position: Vector3 = Vector3(),
  val rotation: Vector3 = Vector3()
) {

  private val drawPoints = Array(points.size) { Vector3() }

  abstract fun update(deltaTime: Float)

  fun render(renderer: Renderer) {

  }
}