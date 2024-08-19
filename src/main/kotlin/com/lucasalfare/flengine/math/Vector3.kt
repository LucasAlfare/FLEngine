@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.lucasalfare.flengine.math

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Vector3(
  var x: Float = 0f,
  var y: Float = 0f,
  var z: Float = 0f
) {

  constructor(x: Int, y: Int, z: Int) : this(x.toFloat(), y.toFloat(), z.toFloat())

  fun length() =
    sqrt((x * x) + (y * y) + (z * z))

  /**
   * Returns the dot product between this vector and the param [v].
   */
  fun dotProduct(v: Vector3) =
    (x * v.x) + (y * v.y) + (z * v.z)

  /**
   * Returns the cross product between this vector and the param [v].
   */
  fun crossProduct(v: Vector3) = Vector3(
    x = (y * v.z) - (z * v.y),
    y = (z * v.x) - (x * v.z),
    z = (x * v.y) - (y * v.x)
  )

  /**
   * Performs vector addition operation between this vector and the param [v] and,
   * stores the result in this instance and returns itself.
   */
  fun add(v: Vector3) = Vector3(
    x = x + v.x,
    y = y + v.y,
    z = z + v.z
  )

  /**
   * Performs vector subtraction operation between this vector and the param [v]
   * and, stores the result in this instance and returns itself.
   */
  fun subtract(v: Vector3) = Vector3(
    x = x - v.x,
    y = y - v.y,
    z = z - v.z
  )

  /**
   * Normalizes this vector and returns itself.
   *
   * A normalized vector is a vector with its values padded in order to match Euclidean Length
   * of 1.
   */
  fun normalized(): Vector3 {
    val len2 = length()
    if (len2 == 0f || len2 == 1f) return this
    return scale(1f / sqrt(len2))
  }

  fun translate(translation: Vector3) = Vector3(
    x = x + translation.x,
    y = y + translation.y,
    z = z + translation.z
  )

  fun scale(scalar: Float) = Vector3(
    x = x * scalar,
    y = y * scalar,
    z = z * scalar
  )

  fun rotateAround(point: Vector3) = Vector3(
    x = x * (cos(point.z) * cos(point.y)) +
            y * (cos(point.z) * sin(point.y) * sin(point.x) -
            sin(point.z) * cos(point.x)) +
            z * (cos(point.z) * sin(point.y) * cos(point.x) +
            sin(point.z) * sin(point.x)),

    y = x * (sin(point.z) * cos(point.y)) +
            y * (sin(point.z) * sin(point.y) * sin(point.x) +
            cos(point.z) * cos(point.x)) +
            z * (sin(point.z) * sin(point.y) * cos(point.x) -
            cos(point.z) * sin(point.x)),

    z = x * (-sin(point.y)) +
            y * (cos(point.y) * sin(point.x)) +
            z * (cos(point.y) * cos(point.x))
  )

  fun perspective() = Vector3(
    x = x * getZ0() / (getZ0() + z),
    y = y * getZ0() / (getZ0() + z),
    z = z
  )

  fun centralize(width: Float, height: Float) = Vector3(
    x = x + (width / 2),
    y = y + (height / 2)
  )

  /**
   * Returns the distance between this vector and the vector of the param [v].
   */
  fun distance(v: Vector3): Float {
    val deltaX = v.x - x
    val deltaY = v.y - y
    val deltaZ = v.z - z
    return sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ))
  }

  /**
   * Sets the values of this vector to be the same of the passed vector [v].
   */
  fun copy(v: Vector3) {
    this.x = v.x
    this.y = v.y
    this.z = v.z
  }

  /**
   * Returns a new instance (new object) with the same values from this.
   */
  fun clone() = Vector3(this.x, this.y, this.z)

  override fun toString() = "[$x, $y, $z]"
}