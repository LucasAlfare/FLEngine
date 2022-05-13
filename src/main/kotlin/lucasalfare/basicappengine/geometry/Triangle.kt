package lucasalfare.basicappengine.geometry

import lucasalfare.basicappengine.Handleable
import lucasalfare.basicappengine.graphics.*
import lucasalfare.basicappengine.math.UV
import lucasalfare.basicappengine.math.Vector3
import lucasalfare.basicappengine.math.percentile
import java.awt.Color
import kotlin.math.max
import kotlin.math.min

enum class RenderMode {
  Outline,
  Fill,
  FillAndOutline,
  Texture,
  TextureAndOutline
}

/**
 * The points here must be declared in a consistent order.
 */
@Suppress("MemberVisibilityCanBePrivate")
class Triangle(
  var p0: Vector3,
  var p1: Vector3,
  var p2: Vector3,
  var color: Color = Color.BLACK,
  var outlineColor: Color = Color(transparentColor),
  var targetTexture: Texture? = null
) : Handleable {

  var normal = 0f
  var averageZ = 0f
  lateinit var renderMode: RenderMode

  private lateinit var a: Vector3
  private lateinit var b: Vector3
  private lateinit var c: Vector3

  private val transformedPoints =
    Array(3) { Vector3() }

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
    val scale = args[2] as Float

    // first transforms (store results in separate fields)
    a = p0.translate(position).rotate(rotation).scale(scale)
    b = p1.translate(position).rotate(rotation).scale(scale)
    c = p2.translate(position).rotate(rotation).scale(scale)

    // calculates arithmetically the average Z value of this
    // triangle before applying perspective
    averageZ = (a.z + b.z + c.z) / 3

    // now apply perspective
    a = a.perspective().centralize(ScreenWidth, ScreenHeight)
    b = b.perspective().centralize(ScreenWidth, ScreenHeight)
    c = c.perspective().centralize(ScreenWidth, ScreenHeight)

    // then calculates the length of the cross product between to vectors
    normal = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x)

    /*
    also, always sorts the points by Y value, helper to rasterize;
    here is used an array to sort instead doing it manually.

    Only needed when using Scan Line rasterization method.
    */
//    transformedPoints[0] = a
//    transformedPoints[1] = b
//    transformedPoints[2] = c
//    transformedPoints.sortBy { it.y }
  }

  override fun render(r: Renderer) {
    when (renderMode) {
      RenderMode.Fill, RenderMode.Texture -> {
        rasterize2(r)
      }

      RenderMode.Outline -> {
        outline(r)
      }

      RenderMode.FillAndOutline, RenderMode.TextureAndOutline -> {
        rasterize2(r)
        outline(r)
      }
    }
  }

  private fun outline(r: Renderer) {
    r.drawLine(a.x.toInt(), a.y.toInt(), b.x.toInt(), b.y.toInt(), outlineColor.rgb)
    r.drawLine(b.x.toInt(), b.y.toInt(), c.x.toInt(), c.y.toInt(), outlineColor.rgb)
    r.drawLine(c.x.toInt(), c.y.toInt(), a.x.toInt(), a.y.toInt(), outlineColor.rgb)
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
   * Rasterizes this triangle using a barycentric point to determine if a single
   * pixel should be drawn or not.
   */
  private fun rasterize2(renderer: Renderer) {
    val minX = min(a.x, min(b.x, c.x)).toInt()
    val maxX = max(a.x, max(b.x, c.x)).toInt()
    val minY = min(a.y, min(b.y, c.y)).toInt()
    val maxY = max(a.y, max(b.y, c.y)).toInt()

    for (y in minY..maxY) {
      for (x in minX..maxX) {
        if (containsPoint(x.toFloat(), y.toFloat())) {
          renderer.setPixel(x, y, getPixelColor(x, y))
        }
      }
    }
  }

  fun getPixelColor(x: Int, y: Int) =
    if (renderMode == RenderMode.Fill || renderMode == RenderMode.FillAndOutline) {
      this.color.rgb
    } else {
      if (targetTexture != null) {
        val minX = min(a.x, min(b.x, c.x))
        val maxX = max(a.x, max(b.x, c.x))
        val minY = min(a.y, min(b.y, c.y))
        val maxY = max(a.y, max(b.y, c.y))

        // calculates the [x,y] coord inside the texture space,
        // represented by this UV object.
        val targetUV = UV(
          u = percentile(minX, maxX, x.toFloat()),
          v = percentile(minY, maxY, y.toFloat())
        )

        targetTexture!!.getPixel(targetUV)
      } else {
        0
      }
    }

  /**
   * Checks if a points of coordinates [x] and [y] lies inside this Triangle.
   */
  fun containsPoint(
    x: Float, y: Float,
    ax: Float = a.x, ay: Float = a.y,
    bx: Float = b.x, by: Float = b.y,
    cx: Float = c.x, cy: Float = c.y
  ): Boolean {
    val det = (bx - ax) * (cy - ay) - (by - ay) * (cx - ax)
    return (det * ((bx - ax) * (y - ay) - (by - ay) * (x - ax)) >= 0) &&
            (det * ((cx - bx) * (y - by) - (cy - by) * (x - bx)) >= 0) &&
            (det * ((ax - cx) * (y - cy) - (ay - cy) * (x - cx)) >= 0)
  }

  fun defineRenderMode() {
    // TODO: implement outline handling decision based on MAGENTA

    renderMode = if (targetTexture == null) RenderMode.Fill
    else RenderMode.Texture
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