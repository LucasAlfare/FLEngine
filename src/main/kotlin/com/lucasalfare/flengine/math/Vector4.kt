package com.lucasalfare.flengine.math

import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represents a 4-dimensional vector, commonly used in 3D graphics for homogeneous coordinates.
 *
 * @property x The x-coordinate of the vector.
 * @property y The y-coordinate of the vector.
 * @property z The z-coordinate of the vector.
 * @property w The w-coordinate (homogeneous coordinate) of the vector, with a default value of 1.0.
 */
data class Vector4(
  var x: Double = 0.0,
  var y: Double = 0.0,
  var z: Double = 0.0,
  var w: Double = 1.0
) {

  /**
   * Adds this vector to another vector [v] and returns a new [Vector4] representing the result.
   *
   * @param v The vector to be added.
   * @return A new [Vector4] representing the sum of the two vectors.
   */
  fun add(v: Vector4) = Vector4(x + v.x, y + v.y, z + v.z, w + v.w)

  /**
   * Subtracts another vector [v] from this vector and returns a new [Vector4] representing the result.
   *
   * @param v The vector to be subtracted.
   * @return A new [Vector4] representing the difference between the two vectors.
   */
  fun subtract(v: Vector4) = Vector4(x - v.x, y - v.y, z - v.z, w - v.w)

  /**
   * Scales this vector by a scalar value and returns a new [Vector4] representing the result.
   *
   * @param scalar The scalar value by which to scale the vector.
   * @return A new [Vector4] representing the scaled vector.
   */
  fun scale(scalar: Double) = Vector4(x * scalar, y * scalar, z * scalar, w * scalar)

  /**
   * Computes the dot product between this vector and another vector [v].
   *
   * The dot product is a measure of how much one vector extends in the direction of another vector.
   *
   * @param v The vector to compute the dot product with.
   * @return The dot product as a [Double].
   */
  fun dotProduct(v: Vector4): Double = (x * v.x) + (y * v.y) + (z * v.z)

  /**
   * Computes the cross product between this vector and another vector [v] and returns a new [Vector4] representing the result.
   *
   * The cross product is a vector that is perpendicular to both original vectors and has a magnitude
   * equal to the area of the parallelogram that the vectors span.
   *
   * Note: The w component is ignored in this calculation, as cross product is typically used in 3D space.
   *
   * @param v The vector to compute the cross product with.
   * @return A new [Vector4] representing the cross product.
   */
  fun crossProduct(v: Vector4): Vector4 = Vector4(
    x = (y * v.z) - (z * v.y),
    y = (z * v.x) - (x * v.z),
    z = (x * v.y) - (y * v.x),
    w = 0.0  // The w component is set to 0 for cross products.
  )

  /**
   * Multiplies this vector by a [Matrix4x4] and returns a new [Vector4] representing the result.
   *
   * This operation is typically used to transform the vector by applying translation, rotation, scale,
   * or other transformations encapsulated by the matrix.
   *
   * @param m The matrix by which to multiply this vector.
   * @return A new [Vector4] representing the transformed vector.
   */
  fun multiply(m: Matrix4x4): Vector4 = Vector4(
    x = (m.get(0, 0) * x) + (m.get(1, 0) * y) + (m.get(2, 0) * z) + (m.get(3, 0) * w),
    y = (m.get(0, 1) * x) + (m.get(1, 1) * y) + (m.get(2, 1) * z) + (m.get(3, 1) * w),
    z = (m.get(0, 2) * x) + (m.get(1, 2) * y) + (m.get(2, 2) * z) + (m.get(3, 2) * w),
    w = (m.get(0, 3) * x) + (m.get(1, 3) * y) + (m.get(2, 3) * z) + (m.get(3, 3) * w)
  )

  /**
   * Computes the Euclidean length (magnitude) of this vector.
   *
   * The magnitude is a measure of the vector's length in 3D space.
   *
   * @return The magnitude as a [Double].
   */
  fun magnitude() = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

  /**
   * Normalizes this vector, producing a unit vector in the same direction.
   *
   * A unit vector has a magnitude of 1. If the magnitude is 0 or 1, the original vector is returned unchanged.
   *
   * @return A new [Vector4] representing the normalized vector.
   */
  fun normalized(): Vector4 {
    val magnitude = magnitude()
    return if (magnitude == 0.0 || magnitude == 1.0) this else Vector4(x / magnitude, y / magnitude, z / magnitude, w)
  }

  /**
   * Normalizes this vector with respect to its w component, dividing x, y, and z by w.
   *
   * This operation is commonly used in graphics pipelines to project points from 4D homogeneous coordinates
   * back to 3D space. If w is 0, the original vector is returned unchanged.
   *
   * @return A new [Vector4] representing the w-normalized vector.
   */
  fun wNormalized(): Vector4 {
    return if (w == 0.0) this else Vector4(x / w, y / w, z / w, 1.0)
  }

  fun toViewportCoordinates(viewportWidth: Int, vieweportHeight: Int): Vector4 {
    val wNormalized = wNormalized()
    val screenX = (wNormalized.x * viewportWidth / 2.0 + viewportWidth / 2.0)
    val screenY = (wNormalized.y * -vieweportHeight / 2.0 + vieweportHeight / 2.0)
    return Vector4(screenX, screenY, 0.0, 0.0)
  }

  /**
   * Provides a string representation of the vector, primarily for debugging purposes.
   *
   * @return A [String] in the format "x, y, z, w".
   */
  override fun toString() = "$x, $y, $z, $w"

  // operators functions
  operator fun plus(v: Vector4) = add(v)
  operator fun times(m: Matrix4x4) = multiply(m)
  operator fun minus(v: Vector4) = subtract(v)

  // inline function to call cross product
  infix fun x(v: Vector4) = crossProduct(v)
}