@file:Suppress("MemberVisibilityCanBePrivate")

package com.lucasalfare.flengine.math

import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class Matrix4x4(
  val data: FloatArray = FloatArray(4 * 4) { 0f }
) {

  init {
    setIdentify()
  }

  fun setIdentify() {
    Arrays.fill(data, 0f)
    data[0] = 1f
    data[5] = 1f
    data[10] = 1f
    data[15] = 1f
  }

  fun multiply(other: Matrix4x4): Matrix4x4 {
    for (row in 0 until 4) {
      for (col in 0 until 4) {
        this.data[row * 4 + col] =
          data[row * 4 + 0] * other.data[0 * 4 + col] +
                  data[row * 4 + 1] * other.data[1 * 4 + col] +
                  data[row * 4 + 2] * other.data[2 * 4 + col] +
                  data[row * 4 + 3] * other.data[3 * 4 + col]
      }
    }
    return this
  }


  companion object {
    fun rotationX(angle: Float): Matrix4x4 {
      val sinTheta = sin(angle)
      val cosTheta = cos(angle)

      println(sinTheta)
      println(cosTheta)

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
      val sinTheta = sin(angle)
      val cosTheta = cos(angle)

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
      val sinTheta = sin(angle)
      val cosTheta = cos(angle)

      return Matrix4x4(
        floatArrayOf(
          cosTheta, -sinTheta, 0f, 0f,
          sinTheta, cosTheta, 0f, 0f,
          0f, 0f, 1f, 0f,
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

fun main() {
  val m = Matrix4x4()
  println(m)

  println()

  val res = m.multiply(Matrix4x4.rotationX(37f))
  println(res)
}