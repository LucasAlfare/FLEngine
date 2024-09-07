package com.lucasalfare.flengine

/**
 * This file demonstrates some mesh rendering using the
 * own engine API.
 */
import com.lucasalfare.flengine.graphics.*
import com.lucasalfare.flengine.math.Matrix4x4
import com.lucasalfare.flengine.math.Vector4
import java.awt.Color
import java.awt.geom.Path2D

class MyApp(title: String) : App(title) {

  private val camera = Camera(
    aspectRatio = 16.0 / 8.0,
    fov = 90.0,
    position = Vector4(0.0, 0.0, 1.0)
  )
  private val objPoints = listOf(
    Vector4(0.0, 1.0, 0.0, 1.0),  // Vértice A (ápice)
    Vector4(-0.5, -1.0, 0.0, 1.0),  // Vértice B (esquerda)
    Vector4(0.5, -1.0, 0.0, 1.0)   // Vértice C (direita)
  )

  private var angle: Double = 0.0
  private var transformedPoints = listOf<Vector4>()
  private var path = Path2D.Double()

  var zzz = 0.0

  override fun init() {

  }

  override fun update(vararg args: Any) {
    val timeStep = args[0] as Float

    if (angle >= 360) angle = 0.0

    transformedPoints = objPoints.map {
      it
//        .multiply(Matrix4x4.rotationXMatrix(angle))
        .multiply(Matrix4x4.rotationYMatrix(angle))
//        .multiply(Matrix4x4.rotationZMatrix(angle))
        .multiply(Matrix4x4.translationMatrix(tz = 100.0))
        .multiply(camera.combinedMatrix())
        .toViewportCoordinates(ScreenWidth.toInt(), ScreenHeight.toInt())
    }

    path.reset()
    transformedPoints.forEachIndexed { i, point ->
      if (i == 0) path.moveTo(point.x, point.y)
      else path.lineTo(point.x, point.y)
    }
    path.closePath()

    angle += 90 * timeStep
    zzz += 1 * timeStep
    camera.position.z += zzz
  }

  override fun render(r: Renderer) {
    val g = r.createGraphics()

    g.color = Color.WHITE
    g.draw(path)
  }
}

fun main() {
  val e = Engine(MyApp("Meu teste de render  3D"))
  e.setSize(ScreenWidth.toInt(), ScreenHeight.toInt())
  e.start()
}