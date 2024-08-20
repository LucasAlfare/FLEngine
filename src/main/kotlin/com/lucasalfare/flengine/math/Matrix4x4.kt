@file:Suppress("MemberVisibilityCanBePrivate")

package com.lucasalfare.flengine.math

import java.util.*
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class Matrix4x4(val data: FloatArray = FloatArray(4 * 4) { 0f }) {

  fun setIdentify() {
    Arrays.fill(data, 0f)
    listOf(0, 5, 10, 15).forEach { data[it] = 1f }
  }

  fun multiply(other: Matrix4x4): Matrix4x4 {
    val result = Matrix4x4()
    for (i in 0 until 4) {
      for (j in 0 until 4) {
        var sum = 0f
        for (k in 0 until 4)
          sum += this.get(i, k) * other.get(k, j)
        result.set(sum, i, j)
      }
    }
    return result
  }

  fun get(x: Int, y: Int) = data[x + y * 4]

  fun set(f: Float, x: Int, y: Int) {
    data[x + y * 4] = f
  }

  // static functions to generate transformation matrixes based on arguments
  companion object {

    // https://en.wikipedia.org/wiki/Rotation_matrix
    fun rotationX(angle: Float): Matrix4x4 {
      val radians = Math.toRadians(angle.toDouble())
      val sinTheta = sin(radians).toFloat()
      val cosTheta = cos(radians).toFloat()

      return Matrix4x4(
        floatArrayOf(
          1f, 0f, 0f, 0f,
          0f, cosTheta, -sinTheta, 0f,
          0f, sinTheta, cosTheta, 0f,
          0f, 0f, 0f, 1f
        )
      )
    }

    // https://en.wikipedia.org/wiki/Rotation_matrix
    fun rotationY(angle: Float): Matrix4x4 {
      val radians = Math.toRadians(angle.toDouble())
      val sinTheta = sin(radians).toFloat()
      val cosTheta = cos(radians).toFloat()

      return Matrix4x4(
        floatArrayOf(
          cosTheta, 0f, sinTheta, 0f,
          0f, 1f, 0f, 0f,
          -sinTheta, 0f, cosTheta, 0f,
          0f, 0f, 0f, 1f
        )
      )
    }

    // https://en.wikipedia.org/wiki/Rotation_matrix
    fun rotationZ(angle: Float): Matrix4x4 {
      val radians = Math.toRadians(angle.toDouble())
      val sinTheta = sin(radians).toFloat()
      val cosTheta = cos(radians).toFloat()

      return Matrix4x4(
        floatArrayOf(
          cosTheta, -sinTheta, 0f, 0f,
          sinTheta, cosTheta, 0f, 0f,
          0f, 0f, 1f, 0f,
          0f, 0f, 0f, 1f
        )
      )
    }

    // https://static.javatpoint.com/tutorial/computer-graphics/images/computer-graphics-3d-transformations3.png
    fun translationMatrix(tx: Float, ty: Float, tz: Float): Matrix4x4 {
      val matrix = Matrix4x4()
      matrix.setIdentify()
      matrix.set(tx, 3, 0)
      matrix.set(ty, 3, 1)
      matrix.set(tz, 3, 2)
      return matrix
    }

    // http://www.c-jump.com/bcc/common/Talk3/Math/Matrices/const_images/applying_scaling.png
    fun scaleMatrix(sx: Float, sy: Float, sz: Float): Matrix4x4 {
      val matrix = Matrix4x4()
      matrix.setIdentify()
      matrix.set(sx, 0, 0)
      matrix.set(sy, 1, 1)
      matrix.set(sz, 2, 2)
      return matrix
    }

    // https://i.sstatic.net/C9PST.jpg
    fun perspectiveMatrix(fov: Float, aspect: Float, zNear: Float, zFar: Float): Matrix4x4 {
      val factor = tan(Math.toRadians(fov / 2.0).toFloat())
      return Matrix4x4(
        floatArrayOf(
          1f / (aspect * factor), 0f, 0f, 0f,
          0f, 1f / factor, 0f, 0f,
          0f, 0f, -((zFar + zNear) / (zFar - zNear)), -((2 * zFar * zNear) / (zFar - zNear)),
          0f, 0f, -1f, 0f
        )
      )
    }

    // https://wikimedia.org/api/rest_v1/media/math/render/svg/8ea4e438d7439b8fa504fb53fd7fafd678007243
    fun orthographic(left: Float, right: Float, bottom: Float, top: Float, zNear: Float, zFar: Float): Matrix4x4 {
      return Matrix4x4(
        floatArrayOf(
          2f / (right - left), 0f, 0f, -((right + left) / (right - left)),
          0f, 2f / (top - bottom), 0f, -((top + bottom) / (top - bottom)),
          0f, 0f, -2f / (zFar - zNear), -((zFar + zNear) / (zFar - zNear)),
          0f, 0f, 0f, 1f
        )
      )
    }
  }

  override fun toString(): String {
    val formattedMatrix = Array(4) { row ->
      Array(4) { col ->
        String.format(Locale.US, "%.1f", data[row * 4 + col])
      }.joinToString(" ", "[", "]")
    }.joinToString("\n")

    return "Matrix4x4:\n$formattedMatrix"
  }
}
