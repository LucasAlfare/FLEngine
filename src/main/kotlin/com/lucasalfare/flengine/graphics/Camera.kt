package com.lucasalfare.flengine.graphics

import com.lucasalfare.flengine.math.Matrix4x4
import com.lucasalfare.flengine.math.Vector4

// good reference: https://www3.ntu.edu.sg/home/ehchua/programming/opengl/CG_BasicsTheory.html
class Camera(
  private val position: Vector4 = Vector4(0f, 0f, 0f, 1f), // "eye"
  private var target: Vector4 = Vector4(0f, 0f, -1f, 1f), // "at"
  private val up: Vector4 = Vector4(0f, 1f, 0f, 0f), // "up"
  private val fov: Float = 60f,
  private val aspectRatio: Float = 16f / 9f,
  private val zNear: Float = 0.1f,
  private val zFar: Float = 100f
) {
  fun getViewTransformationMatrix(): Matrix4x4 {
    val zAxis = (position.subtract(target)).normalized() // inverse camera direction
    val xAxis = (up.crossProduct(zAxis)).normalized() // Direção "para a direita" da câmera
    val yAxis = zAxis.crossProduct(xAxis) // Direção "para cima" da câmera

    // translation
    val translation = Matrix4x4.translationMatrix(-position.x, -position.y, -position.z)

    val orientation = Matrix4x4(
      floatArrayOf(
        xAxis.x, yAxis.x, zAxis.x, 0f,
        xAxis.y, yAxis.y, zAxis.y, 0f,
        xAxis.z, yAxis.z, zAxis.z, 0f,
        0f, 0f, 0f, 1f
      )
    )

    return orientation.multiply(translation)
  }

  // Gera a matriz de projeção (perspectiva)
  fun getProjectionMatrix(): Matrix4x4 {
    return Matrix4x4.perspectiveMatrix(fov, aspectRatio, zNear, zFar)
  }

  // Função para atualizar a direção da câmera
  fun lookAt(newTarget: Vector4) {
    target = newTarget
  }
}