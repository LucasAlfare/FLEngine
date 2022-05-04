@file:Suppress("ArrayInDataClass")

package lucasalfare.basicappengine.math

import java.awt.Color

private val a = Vector3(-1.0, 1.0, -1.0)
private val b = Vector3(1.0, 1.0, -1.0)
private val c = Vector3(1.0, -1.0, -1.0)
private val d = Vector3(-1.0, -1.0, -1.0)

private val e = Vector3(1.0, 1.0, 1.0)
private val f = Vector3(-1.0, 1.0, 1.0)
private val g = Vector3(-1.0, -1.0, 1.0)
private val h = Vector3(1.0, -1.0, 1.0)

class Cube(
  val mesh: Mesh = Mesh(
    triangles = arrayOf(
      Triangle(p0 = a, p1 = b, p2 = d, color = Color.GREEN),
      Triangle(p0 = d, p1 = b, p2 = c, color = Color.GREEN),

      Triangle(p0 = f, p1 = e, p2 = a, color = Color.WHITE),
      Triangle(p0 = a, p1 = e, p2 = b, color = Color.WHITE),

      Triangle(p0 = b, p1 = e, p2 = c, color = Color.RED),
      Triangle(p0 = c, p1 = e, p2 = h, color = Color.RED),

      Triangle(p0 = e, p1 = f, p2 = h, color = Color.GREEN),
      Triangle(p0 = h, p1 = f, p2 = g, color = Color.GREEN),

      Triangle(p0 = f, p1 = a, p2 = g, color = Color.RED),
      Triangle(p0 = g, p1 = a, p2 = d, color = Color.RED),

      Triangle(p0 = d, p1 = c, p2 = g, color = Color.WHITE),
      Triangle(p0 = g, p1 = c, p2 = h, color = Color.WHITE)
    ),
    scaleFactor = 100.0
  )
)