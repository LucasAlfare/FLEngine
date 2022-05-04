package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.*
import lucasalfare.basicappengine.math.Mesh
import lucasalfare.basicappengine.math.Triangle
import lucasalfare.basicappengine.math.Vector3
import java.awt.Color
import java.awt.event.KeyEvent


private val a = Vector3(-1.0, 1.0, -1.0)
private val b = Vector3(1.0, 1.0, -1.0)
private val c = Vector3(1.0, -1.0, -1.0)
private val d = Vector3(-1.0, -1.0, -1.0)

private val e = Vector3(1.0, 1.0, 1.0)
private val f = Vector3(-1.0, 1.0, 1.0)
private val g = Vector3(-1.0, -1.0, 1.0)
private val h = Vector3(1.0, -1.0, 1.0)

class MainExample : AbstractApp() {

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
    scaleFactor = 100.0
  )

  override fun init(engine: Engine) {

  }

  override fun update(engine: Engine, deltaTime: Float) {
    cube.update(deltaTime) {
      if (Input.isKey(KeyEvent.VK_RIGHT)) {
        cube.position.x += 10 * it
      } else if (Input.isKey(KeyEvent.VK_LEFT)) {
        cube.position.x -= 10 * it
      }

      if (Input.isKey(KeyEvent.VK_UP)) {
        cube.position.z += 10 * it
      } else if (Input.isKey(KeyEvent.VK_DOWN)) {
        cube.position.z -= 10 * it
      }

      cube.rotation.x += 1 * it
      cube.rotation.y += 2 * it
      cube.rotation.z += 3 * it
    }
  }

  override fun render(engine: Engine, renderer: Renderer) {
    cube.render(renderer)
  }
}

fun main() {
  val e = Engine(MainExample())
  e.setSize(ResolutionX.toInt(), ResolutionY.toInt())
  e.start()
}