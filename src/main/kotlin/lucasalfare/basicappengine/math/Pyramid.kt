package lucasalfare.basicappengine.math

import java.awt.Color

private val a = Vector3(0.0, 1.0, 0.0)
private val b = Vector3(1.0, -1.0, -1.0)
private val c = Vector3(-1.0, -1.0, -1.0)
private val d = Vector3(0.0, -1.0, 1.0)

class Pyramid(
  val mesh: Mesh = Mesh(
    triangles = arrayOf(
      Triangle(p0 = a, p1 = b, p2 = c, color = Color.GREEN),
      Triangle(p0 = a, p1 = c, p2 = d, color = Color.RED),
      Triangle(p0 = a, p1 = d, p2 = b, color = Color.YELLOW),
      Triangle(p0 = b, p1 = d, p2 = c, color = Color.BLUE)
    ),
    scaleFactor = 100.0
  )
)