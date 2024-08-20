@file:Suppress("MemberVisibilityCanBePrivate")

package com.lucasalfare.flengine.math

import java.util.*
import kotlin.math.cos
import kotlin.math.sin

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

  companion object {
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

    fun translationMatrix(tx: Float, ty: Float, tz: Float): Matrix4x4 {
      val matrix = Matrix4x4()
      matrix.setIdentify()
      matrix.set(tx, 3, 0)
      matrix.set(ty, 3, 1)
      matrix.set(tz, 3, 2)
      return matrix
    }

    fun scaleMatrix(sx: Float, sy: Float, sz: Float): Matrix4x4 {
      val matrix = Matrix4x4()
      matrix.setIdentify()
      matrix.set(sx, 0, 0)
      matrix.set(sy, 1, 1)
      matrix.set(sz, 2, 2)
      return matrix
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

fun main() {
  val m = Matrix4x4()
  m.setIdentify()
  println(m)

  println(m.multiply(Matrix4x4.rotationX(45f)))
}