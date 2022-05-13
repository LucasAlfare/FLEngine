package lucasalfare.basicappengine

/**
 * This file demonstrates some mesh rendering using the
 * own engine API.
 */
import lucasalfare.basicappengine.graphics.*
import lucasalfare.basicappengine.math.Vector3
import lucasalfare.basicappengine.geometry.Mesh
import lucasalfare.basicappengine.geometry.Triangle
import java.awt.Color

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
class MyApp(title: String) : AbstractApp(title) {

  private val cube = Mesh(
    triangles = arrayOf(
      Triangle(p0 = a, p1 = b, p2 = d, color = Color.GREEN),
      Triangle(p0 = d, p1 = b, p2 = c, color = Color.GREEN),

      Triangle(p0 = f, p1 = e, p2 = a, color = Color.WHITE),
      Triangle(p0 = a, p1 = e, p2 = b, color = Color.WHITE),

      Triangle(p0 = b, p1 = e, p2 = c, color = Color.RED),
      Triangle(p0 = c, p1 = e, p2 = h, color = Color.RED),

      Triangle(p0 = e, p1 = f, p2 = h, color = Color.BLUE),
      Triangle(p0 = h, p1 = f, p2 = g, color = Color.BLUE),

      Triangle(p0 = f, p1 = a, p2 = g, color = Color.PINK),
      Triangle(p0 = g, p1 = a, p2 = d, color = Color.PINK),

      Triangle(p0 = d, p1 = c, p2 = g, color = Color.YELLOW),
      Triangle(p0 = g, p1 = c, p2 = h, color = Color.YELLOW)
    ),
    scale = 100f,
    texture = Texture("crate_texture.png")
  )

  private val plane = Mesh(
    triangles = arrayOf(
      Triangle(
        Vector3(1, 0, -1),
        Vector3(1, 0, 1),
        Vector3(-1, 0, 1),
        Color.CYAN
      ),
      Triangle(
        Vector3(-1, 0, 1),
        Vector3(-1, 0, -1),
        Vector3(1, 0, -1),
        Color.CYAN
      )
    ),
    scale = 200f
  )

  override fun init() {

  }

  override fun update(vararg args: Any) {
    val timeStep = args[0] as Float

    cube.update()
    cube.rotation.x += 1 * timeStep
    cube.rotation.y += 1 * timeStep
    cube.rotation.z += 1 * timeStep

//    plane.update()
//    plane.position.y = 60f * timeStep
//    plane.rotation.y -= 1 * timeStep
  }

  override fun render(r: Renderer) {
    cube.render(r)
    //plane.render(r)
  }
}

fun main() {
  val e = Engine(MyApp("Meu teste de render  3D"))
  e.setSize(ScreenWidth.toInt(), ScreenHeight.toInt())
  e.start()
}