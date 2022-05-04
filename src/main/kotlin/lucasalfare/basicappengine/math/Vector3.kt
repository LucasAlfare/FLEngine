package lucasalfare.basicappengine.math

import kotlin.math.cos
import kotlin.math.sin

class Vector3(var x: Double = 0.0, var y: Double = 0.0, var z: Double = 0.0) {

  fun translateTo(translation: Vector3) = Vector3(
    x = x + translation.x,
    y = y + translation.y,
    z = z + translation.z
  )

  // Rotation matrix: https://en.wikipedia.org/wiki/Rotation_matrix
  fun rotate(rotation: Vector3) = Vector3(
    x = x * (cos(rotation.z) * cos(rotation.y)) +
            y * (cos(rotation.z) * sin(rotation.y) * sin(rotation.x) -
            sin(rotation.z) * cos(rotation.x)) +
            z * (cos(rotation.z) * sin(rotation.y) * cos(rotation.x) +
            sin(rotation.z) * sin(rotation.x)),
    y = x * (sin(rotation.z) * cos(rotation.y)) +
            y * (sin(rotation.z) * sin(rotation.y) * sin(rotation.x) +
            cos(rotation.z) * cos(rotation.x)) +
            z * (sin(rotation.z) * sin(rotation.y) * cos(rotation.x) -
            cos(rotation.z) * sin(rotation.x)),
    z = x * (-sin(rotation.y)) +
            y * (cos(rotation.y) * sin(rotation.x)) +
            z * (cos(rotation.y) * cos(rotation.x))
  )

  fun toPerspective() = Vector3(
    x = x * getZ0() / (getZ0() + z),
    y = y * getZ0() / (getZ0() + z),
    z = z
  )

  fun centerInBound(width: Double, height: Double) = Vector3(
    x = x + (width / 2),
    y = y + (height / 2)
  )
}