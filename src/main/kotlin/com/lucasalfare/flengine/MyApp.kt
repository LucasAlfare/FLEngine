package com.lucasalfare.flengine

/**
 * This file demonstrates some mesh rendering using the
 * own engine API.
 */
import com.lucasalfare.flengine.math.Vector3
import com.lucasalfare.flengine.geometry.Mesh
import com.lucasalfare.flengine.geometry.Triangle
import com.lucasalfare.flengine.graphics.*

/**
 * Only auxiliary fields to store points to the cube mesh.
 */
private val a = Vector3(-1f, 1f, -1f)
private val b = Vector3(1f, 1f, -1f)
private val c = Vector3(1f, -1f, -1f)
private val d = Vector3(-1f, -1f, -1f)

private val e = Vector3(1f, 1f, 1f)
private val f = Vector3(-1f, 1f, 1f)
private val g = Vector3(-1f, -1f, 1f)
private val h = Vector3(1f, -1f, 1f)

/**
 * A custom class representing an Application that can be
 * updated and rendered by the [Engine].
 */
class MyApp(title: String) : App(title) {

  private val t = Mesh(
    triangles = arrayOf(
      Triangle(
        Vector3(-1, -1, 0),
        Vector3(1, -1, 0),
        Vector3(-1, 1, 0)
      )
    ),
    scale = 100f,
    texture = Texture("tri_test.png")
  )

  override fun init() {

  }

  override fun update(vararg args: Any) {
    val timeStep = args[0] as Float

    //t.rotation.x += 1 * timeStep
    //t.rotation.y += 1 * timeStep
    t.rotation.z += 1 * timeStep
    t.update()
  }

  override fun render(r: Renderer) {
    t.render(r)
  }
}

fun main() {
  val e = Engine(MyApp("Meu teste de render  3D"))
  e.setSize(ScreenWidth.toInt(), ScreenHeight.toInt())
  e.start()
}