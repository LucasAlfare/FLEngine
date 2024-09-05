@file:Suppress("MemberVisibilityCanBePrivate")

package com.lucasalfare.flengine.math

import java.util.*
import kotlin.math.cos
import kotlin.math.sin

/**
 * Represents a 4x4 matrix used for various transformations in 3D space.
 *
 * This class treats a linear collection of elements as a bidimensional 4x4 matrix,
 * providing methods to perform common matrix operations such as translation,
 * scaling, and rotation along the X, Y, and Z axes. It also supports matrix
 * multiplication and provides utility functions for setting and retrieving matrix elements.
 *
 * @property elements An array of 16 double values representing the matrix elements in row-major order.
 *
 * @throws IllegalArgumentException If the number of elements provided is not exactly 16.
 */
class Matrix4x4(vararg var elements: Double) {

  /**
   * Initializes the Matrix4x4 instance.
   *
   * If exactly 16 elements are provided, they are used to populate the matrix.
   * If no elements are provided, the matrix is initialized as an identity matrix.
   *
   * @throws IllegalArgumentException If the number of elements is between 1 and 15.
   */
  init {
    // Only creates a matrix if it has exactly 16 elements or...
    if (elements.size in 1..15)
      throw IllegalArgumentException("Matrix must contain exactly 16 elements.")

    // ...if it is empty (then this is set to identity matrix)
    if (elements.isEmpty()) {
      elements = DoubleArray(16) { 0.0 }
      setIdentity()
    }
  }

  /**
   * Companion object containing factory methods for creating common transformation matrices.
   */
  companion object {
    /**
     * Creates a translation matrix based on the provided translation values.
     *
     * @param tx Translation along the X-axis. Defaults to 0.0.
     * @param ty Translation along the Y-axis. Defaults to 0.0.
     * @param tz Translation along the Z-axis. Defaults to 0.0.
     * @return A new Matrix4x4 instance representing the translation.
     *
     * @see [Translation Matrix](https://static.javatpoint.com/tutorial/computer-graphics/images/computer-graphics-3d-transformations3.png)
     */
    fun translationMatrix(tx: Double = 0.0, ty: Double = 0.0, tz: Double = 0.0): Matrix4x4 {
      // @formatter:off
      return Matrix4x4(
        1.0, 0.0, 0.0,  tx,
        0.0, 1.0, 0.0,  ty,
        0.0, 0.0, 1.0,  tz,
        0.0, 0.0, 0.0, 1.0
      )
      // @formatter:on
    }

    /**
     * Creates a scaling matrix based on the provided scaling factors.
     *
     * @param sx Scaling factor along the X-axis. Defaults to 1.0.
     * @param sy Scaling factor along the Y-axis. Defaults to 1.0.
     * @param sz Scaling factor along the Z-axis. Defaults to 1.0.
     * @return A new Matrix4x4 instance representing the scaling.
     *
     * @see [Scaling Matrix](http://www.c-jump.com/bcc/common/Talk3/Math/Matrices/const_images/applying_scaling.png)
     */
    fun scaleMatrix(sx: Double = 1.0, sy: Double = 1.0, sz: Double = 1.0): Matrix4x4 {
      // @formatter:off
      return Matrix4x4(
        sx, 0.0, 0.0, 0.0,
        0.0,  sy, 0.0, 0.0,
        0.0, 0.0,  sz, 0.0,
        0.0, 0.0, 0.0, 1.0
      )
      // @formatter:on
    }

    /**
     * Creates a rotation matrix around the X-axis based on the provided angle.
     *
     * @param degreeAngle The angle in degrees to rotate around the X-axis. Defaults to 0.0.
     * @return A new Matrix4x4 instance representing the rotation around the X-axis.
     *
     * @see [Rotation Matrix X](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkOm2Vs8YxvEDDMNHS8vRNrdfavXSfcM4JuA&s)
     */
    fun rotationXMatrix(degreeAngle: Double = 0.0): Matrix4x4 {
      val radians = Math.toRadians(degreeAngle)
      val sin = sin(radians)
      val cos = cos(radians)
      // @formatter:off
      return Matrix4x4(
        1.0,  0.0,  0.0,  0.0,
        0.0,  cos, -sin,  0.0,
        0.0,  sin,  cos,  0.0,
        0.0,  0.0,  0.0,  1.0
      )
      // @formatter:on
    }

    /**
     * Creates a rotation matrix around the Y-axis based on the provided angle.
     *
     * @param degreeAngle The angle in degrees to rotate around the Y-axis. Defaults to 0.0.
     * @return A new Matrix4x4 instance representing the rotation around the Y-axis.
     *
     * @see [Rotation Matrix Y](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkOm2Vs8YxvEDDMNHS8vRNrdfavXSfcM4JuA&s)
     */
    fun rotationYMatrix(degreeAngle: Double = 0.0): Matrix4x4 {
      val radians = Math.toRadians(degreeAngle)
      val sin = sin(radians)
      val cos = cos(radians)
      // @formatter:off
      return Matrix4x4(
        cos,  0.0,  sin,  0.0,
        0.0,  1.0,  0.0,  0.0,
        -sin,  0.0,  cos,  0.0,
        0.0,  0.0,  0.0,  1.0
      )
      // @formatter:on
    }

    /**
     * Creates a rotation matrix around the Z-axis based on the provided angle.
     *
     * @param degreeAngle The angle in degrees to rotate around the Z-axis. Defaults to 0.0.
     * @return A new Matrix4x4 instance representing the rotation around the Z-axis.
     *
     * @see [Rotation Matrix Z](https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkOm2Vs8YxvEDDMNHS8vRNrdfavXSfcM4JuA&s)
     */
    fun rotationZMatrix(degreeAngle: Double = 0.0): Matrix4x4 {
      val radians = Math.toRadians(degreeAngle)
      val sin = sin(radians)
      val cos = cos(radians)
      // @formatter:off
      return Matrix4x4(
        cos, -sin,  0.0,  0.0,
        sin,  cos,  0.0,  0.0,
        0.0,  0.0,  1.0,  0.0,
        0.0,  0.0,  0.0,  1.0
      )
      // @formatter:on
    }
  }

  /**
   * Multiplies the current matrix with another Matrix4x4.
   *
   * This method performs standard matrix multiplication, where each element of the resulting
   * matrix is the dot product of the corresponding row from the first matrix and the column
   * from the second matrix.
   *
   * @param m The Matrix4x4 to multiply with the current matrix.
   * @return A new Matrix4x4 instance representing the product of the two matrices.
   */
  fun multiply(m: Matrix4x4): Matrix4x4 {
    val result = Matrix4x4()
    repeat(4) { i ->
      repeat(4) { j ->
        var sum = 0.0
        repeat(4) { k ->
          sum += this.get(k, i) * m.get(j, k)
        }
        result.set(j, i, sum)
      }
    }

    return result
  }

  /**
   * Sets the current matrix to be an identity matrix.
   *
   * This method sets the diagonal elements (top-left to bottom-right) to 1.0 and all other
   * elements to 0.0, effectively making it an identity matrix.
   *
   * @return The current Matrix4x4 instance after being set to identity.
   */
  fun setIdentity(): Matrix4x4 {
    repeat(4) { y ->
      repeat(4) { x ->
        if (x == y) set(x, y, 1.0)
        else set(x, y, 0.0)
      }
    }

    return this
  }

  /**
   * Retrieves the value at the specified coordinates in the matrix.
   *
   * @param x The column index (0-based).
   * @param y The row index (0-based).
   * @return The double value at the specified (x, y) position.
   *
   * @throws IndexOutOfBoundsException If x or y is outside the range [0, 3].
   */
  fun get(x: Int, y: Int): Double {
    return elements[x + y * 4]
  }

  /**
   * Sets the value at the specified coordinates in the matrix.
   *
   * @param x The column index (0-based).
   * @param y The row index (0-based).
   * @param d The double value to set at the specified (x, y) position.
   *
   * @throws IndexOutOfBoundsException If x or y is outside the range [0, 3].
   */
  fun set(x: Int, y: Int, d: Double) {
    elements[x + y * 4] = d
  }

  /**
   * Returns a string representation of the matrix for debugging purposes.
   *
   * The matrix is formatted in a readable 4x4 structure with each element rounded to one decimal place.
   *
   * @return A string representing the matrix.
   */
  override fun toString(): String {
    var s = "[\n"

    repeat(4) { y ->
      repeat(4) { x ->
        s += "\t"
        s += "${String.format("%.1f", get(x, y))} ".replace(",", ".")
      }

      s += "\n"
    }

    return "$s]"
  }

  /**
   * Checks if this matrix is equal to another object.
   *
   * Two Matrix4x4 instances are considered equal if their elements are identical.
   *
   * @param other The object to compare with.
   * @return `true` if the other object is a Matrix4x4 with the same elements, `false` otherwise.
   */
  override fun equals(other: Any?): Boolean {
    if (other == null) return false
    if (other !is Matrix4x4) return false
    return this.elements.contentEquals(other.elements)
  }

  /**
   * Returns the hash code for this matrix.
   *
   * This implementation is based on the hash code of the elements array.
   *
   * @return The hash code of the matrix.
   */
  override fun hashCode(): Int {
    return elements.contentHashCode()
  }

  operator fun times(m: Matrix4x4) = multiply(m)
}
