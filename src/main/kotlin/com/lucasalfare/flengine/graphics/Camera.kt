package com.lucasalfare.flengine.graphics

import com.lucasalfare.flengine.math.Matrix4x4
import com.lucasalfare.flengine.math.Vector4
import kotlin.math.tan

/**
 * This class represents a camera in a 3D space, providing methods to generate perspective and view matrices.
 * The camera operates in a coordinate system where:
 *
 * ```
 * O--------------- x
 * |
 * |
 * |
 * |
 * y
 * ```
 *
 * The "Z" dimension extends inward (into the screen) for positive values, meaning that objects further away
 * have higher Z values.
 *
 * @property aspectRatio The ratio of the camera's width to its height. This affects the perspective projection.
 * @property fov Field of view angle in degrees, defining the extent of the observable world seen by the camera.
 * @property near The distance to the near clipping plane, below which objects are not rendered.
 * @property far The distance to the far clipping plane, beyond which objects are not rendered.
 * @property position The position of the camera in 3D space, often referred to as the "eye" position.
 * @property target The point in 3D space the camera is looking at, used to define the direction of view.
 * @property up The "up" direction vector relative to the camera, used to maintain orientation.
 */
data class Camera(
  var aspectRatio: Double = 0.0,
  var fov: Double = 0.0, // field of view angle in degrees
  var near: Double = 1.0,
  var far: Double = 100.0,
  var position: Vector4 = Vector4(0.0, 0.0, 0.0), // "eye"
  var target: Vector4 = Vector4(0.0, 0.0, 0.0), // "looking at point"
  val up: Vector4 = Vector4(0.0, 1.0, 0.0, 0.0) // "up direction"
) {

  /**
   * Companion object providing static functions to generate the perspective and view matrices for the camera.
   */
  companion object {

    /**
     * Generates a perspective projection matrix based on the camera's parameters.
     * This matrix is used to simulate the effect of a 3D perspective, where objects further away appear smaller.
     *
     * @param camera The camera instance containing the parameters for the projection.
     * @return A 4x4 matrix representing the perspective projection.
     *
     * The perspective matrix is calculated using the following formula:
     * - The `fov` is converted from degrees to radians and halved to get `halfFov`.
     * - `tanHalfFov` is the tangent of `halfFov`, which determines the scaling based on the field of view.
     * - The diagonal elements of the matrix are scaled inversely by the aspect ratio and `tanHalfFov`.
     * - The near and far planes are used to scale and translate the Z-values into normalized device coordinates.
     */
    fun perspectiveMatrix(camera: Camera): Matrix4x4 {
      // fov refers to a "degree angle"
      val halfFov = Math.toRadians((camera.fov / 2.0))
      val tanHalfFov = tan(halfFov)
      // @formatter:off
      return Matrix4x4(
        (1 / (camera.aspectRatio * tanHalfFov)), 0.0, 0.0, 0.0,
        0.0, (1 / tanHalfFov), 0.0, 0.0,
        0.0, 0.0, -((camera.far + camera.near) / (camera.far - camera.near)), -((2 * camera.far * camera.near) / (camera.far - camera.near)),
        0.0, 0.0, -1.0, 0.0
      )
      // @formatter:on
    }

    /**
     * Generates a view matrix for the camera, which transforms world coordinates to camera coordinates.
     * This matrix is responsible for the camera's orientation and position in the world space.
     *
     * @param camera The camera instance for which to generate the view matrix.
     * @return A 4x4 matrix representing the view transformation.
     *
     * The view matrix is constructed as follows:
     * - The `zAxis` is the normalized vector from the camera's position to its target, representing the viewing direction.
     * - The `xAxis` is computed as the cross product of the camera's `up` vector and the `zAxis`, representing the camera's right direction.
     * - The `yAxis` is the cross product of the `zAxis` and `xAxis`, representing the camera's up direction.
     * - The matrix is filled with the components of the `xAxis`, `yAxis`, and `zAxis`, and the camera's position is used to translate the world coordinates.
     */
    fun viewMatrix(camera: Camera): Matrix4x4 {
      val zAxis = (camera.target - camera.position).normalized()
      val xAxis = (camera.up x zAxis).normalized()
      val yAxis = zAxis x xAxis

      // precomputed orientation * (position (inverted)) matrixes
      return Matrix4x4(
        xAxis.x, yAxis.x, zAxis.x, -camera.position.x,
        xAxis.y, yAxis.y, zAxis.y, -camera.position.y,
        xAxis.z, yAxis.z, zAxis.z, -camera.position.z,
        0.0, 0.0, 0.0, 1.0
      )
    }
  }

  /**
   * Combines the view and perspective matrices to produce a final transformation matrix.
   * This matrix is used to transform world coordinates directly into normalized device coordinates
   * for rendering from the camera's point of view.
   *
   * @return A 4x4 matrix that is the product of the view matrix and perspective matrix.
   *
   * The combined matrix is crucial in the rendering pipeline, as it allows objects in the world space
   * to be projected onto the screen with the correct perspective and orientation.
   */
  fun combinedMatrix() = viewMatrix(this) * perspectiveMatrix(this)
}
