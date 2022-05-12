package lucasalfare.basicappengine.geometry

import lucasalfare.basicappengine.Handleable
import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY
import lucasalfare.basicappengine.math.Vector3
import java.awt.Color
import kotlin.math.max
import kotlin.math.min

/**
 * The points here must be declared in a consistent order.
 */
class Triangle(
  var p0: Vector3,
  var p1: Vector3,
  var p2: Vector3,
  var color: Color
) : Handleable {

  var normal = 0.0

  var averageZ = 0.0

  private val transformedPoints =
    Array(3) { Vector3() }

  private lateinit var a: Vector3
  private lateinit var b: Vector3
  private lateinit var c: Vector3

  /**
   * Operations done in this update:
   * - Transforms (translate, rotate, scale);
   * - Apply perspective to this triangle points;
   * - Calculates the normal of this triangle.
   *
   * Note that
   * - All original points are not modified;
   * - This step is done with single and separated vector objects,
   * which is replacing the classical "matrix multiplication", which
   * usually is used in this case/step.
   * */
  override fun update(vararg args: Any) {
    val position = args[0] as Vector3
    val rotation = args[1] as Vector3
    val scale = args[2] as Double

    // first transforms (store results in separate fields)
    a = p0.translate(position).rotate(rotation).scale(scale)
    b = p1.translate(position).rotate(rotation).scale(scale)
    c = p2.translate(position).rotate(rotation).scale(scale)

    // calculates arithmetically the average Z value of this triangle
    // before applying perspective
    averageZ = (a.z + b.z + c.z) / 3

    // now apply perspective
    a = a.toPerspective().centerInBound(ResolutionX, ResolutionY)
    b = b.toPerspective().centerInBound(ResolutionX, ResolutionY)
    c = c.toPerspective().centerInBound(ResolutionX, ResolutionY)

    // then calculates the length of the cross product between to vectors
    normal = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)

    /*
    also, always sorts the points by Y value, helper to rasterize;
    here is used an array to sort instead doing it manually.
    */
//    transformedPoints[0] = a
//    transformedPoints[1] = b
//    transformedPoints[2] = c
//    transformedPoints.sortBy { it.y }
  }

  override fun render(renderer: Renderer) {
    rasterize2(renderer)
  }

  /**
   * Rasterizes this triangle using a Scan Line algorithm.
   */
  private fun rasterize(renderer: Renderer) {
    /*
    back the points to the sorted order, after
    sorting then in the previous update
    */
    a = transformedPoints[0]
    b = transformedPoints[1]
    c = transformedPoints[2]

    /**
     * Internal helper function to rasterize a triangle that is considered "flat"
     * and that is "pointing to down".
     */
    fun rasterizeFlatBottom(v1: Vector3, v2: Vector3, v3: Vector3) {
      val inverseSlope1 = (v2.x - v1.x) / (v2.y - v1.y)
      val inverseSlope2 = (v3.x - v1.x) / (v3.y - v1.y)
      var currX1 = v1.x
      var currX2 = v1.x
      var scanLineY = v1.y
      while (scanLineY <= v2.y) {
        renderer.drawLine(currX1.toInt(), scanLineY.toInt(), currX2.toInt(), scanLineY.toInt(), color.rgb)
        currX1 += inverseSlope1
        currX2 += inverseSlope2
        scanLineY++
      }
    }

    /**
     * Internal helper function to rasterize a triangle that is considered "flat"
     * and that is "pointing to up".
     */
    fun rasterizeFlatTop(v1: Vector3, v2: Vector3, v3: Vector3) {
      val inverseSlope1 = ((v3.x - v1.x) / (v3.y - v1.y))
      val inverseSlope2 = ((v3.x - v2.x) / (v3.y - v2.y))
      var currX1 = v3.x
      var currX2 = v3.x

      var scanLineY = v3.y
      while (scanLineY > v1.y) {
        renderer.drawLine(currX1.toInt(), scanLineY.toInt(), currX2.toInt(), scanLineY.toInt(), color.rgb)
        currX1 -= inverseSlope1
        currX2 -= inverseSlope2
        scanLineY--
      }
    }

    //performs the rasterization
    if (b.y == c.y) {
      rasterizeFlatBottom(a, b, c)
    } else if (a.y == b.y) {
      rasterizeFlatTop(a, b, c)
    } else {
      // calculates the vertex that divides the triangle in half
      val v4 = Vector3(
        x = (a.x + ((b.y - a.y) / (c.y - a.y)) * (c.x - a.x)),
        y = b.y
      )

      rasterizeFlatTop(b, v4, c)
      rasterizeFlatBottom(a, b, v4)
    }
  }

  /**
   * Rasterizes this triangle using a barycentric point to determine if a pixel should
   * be drawn or not.
   */
  private fun rasterize2(renderer: Renderer) {
    fun contains(
      x: Int, y: Int,
      ax: Double = a.x, ay: Double = a.y,
      bx: Double = b.y, by: Double = b.y,
      cx: Double = c.y, cy: Double = c.y
    ): Boolean {
      val det = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax)

      return (det * ((bx - ax) * (y - ay) - (by - ay) * (x - ax)) >= 0) &&
              (det * ((cx - bx) * (y - by) - (cy - by) * (x - bx)) >= 0) &&
              (det * ((ax - cx) * (y - cy) - (ay - cy) * (x - cx)) >= 0)
    }

    val maxX = max(a.x, max(b.x, c.x)).toInt()
    val minX = min(a.x, min(b.x, c.x)).toInt()
    val maxY = max(a.y, max(b.y, c.y)).toInt()
    val minY = min(a.y, min(b.y, c.y)).toInt()

    for (y in minY..maxY) {
      for (x in minX..maxX) {
        if (contains(x, y)) {
          renderer.setPixel(x, y, color.rgb)
        }
      }
    }
  }

  override fun toString() =
    "Triangle(" +
            "p0=$p0, " +
            "p1=$p1, " +
            "p2=$p2, " +
            "color=$color, " +
            "normal=$normal, " +
            "transformedPoints=${transformedPoints.contentToString()})"
}