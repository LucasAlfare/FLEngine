package com.lucasalfare.flengine.math

import kotlin.math.sqrt

data class Vector4(
  var x: Float = 0f,
  var y: Float = 0f,
  var z: Float = 0f,
  var w: Float = 0f
) {
  fun length() = sqrt((x * x) + (y * y) + (z * z) + (w * w))

  fun dotProduct(v: Vector4) = (x * v.x) + (y * v.y) + (z * v.z) + (w * v.w)

  fun add(v: Vector4) = Vector4(
    x = x + v.x,
    y = y + v.y,
    z = z + v.z,
    w = w + v.w
  )

  fun subtract(v: Vector4) = Vector4(
    x = x - v.x,
    y = y - v.y,
    z = z - v.z,
    w = w - v.w
  )

  fun normalized(): Vector4 {
    val len = length()
    if (len == 0f || len == 1f) return this
    return scale(1f / len)
  }

  fun scale(scalar: Float) = Vector4(
    x = x * scalar,
    y = y * scalar,
    z = z * scalar,
    w = w * scalar
  )

  fun transform(matrix: Matrix4x4): Vector4 {
    return Vector4(
      x = matrix.get(0, 0) * x + matrix.get(1, 0) * y + matrix.get(2, 0) * z + matrix.get(3, 0) * w,
      y = matrix.get(0, 1) * x + matrix.get(1, 1) * y + matrix.get(2, 1) * z + matrix.get(3, 1) * w,
      z = matrix.get(0, 2) * x + matrix.get(1, 2) * y + matrix.get(2, 2) * z + matrix.get(3, 2) * w,
      w = matrix.get(0, 3) * x + matrix.get(1, 3) * y + matrix.get(2, 3) * z + matrix.get(3, 3) * w
    )
  }

  override fun toString() = "[$x, $y, $z, $w]"
}