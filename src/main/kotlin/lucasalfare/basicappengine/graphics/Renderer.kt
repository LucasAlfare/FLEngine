package lucasalfare.basicappengine.graphics

import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.util.*
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate")
class Renderer(var targetImage: BufferedImage) {

  /**
   * Color used to reset pixels values while "clearing".
   */
  var clearColor: Int = Color.BLACK.rgb

  /**
   * Field to store a REFERENCE to the actual/real pixel info, from the target image.
   *
   * Modifying this array automatically affects the target image.
   */
  private var pixelData: IntArray = (targetImage.raster.dataBuffer as DataBufferInt).data

  fun clear() {
    pixelData.fill(clearColor)
  }

  /**
   * Sets a single pixel in the buffer image with the given [color].
   *
   * The pixel is set only if:
   * - the pixel coordinate is inside the image bounds;
   * - the color passed is not equals to the [transparentColor] value defined ins this class.
   */
  fun setPixel(x: Int, y: Int, color: Int) {
    if (!coordInBounds(x, y) || color == transparentColor) return
    pixelData[x + y * targetImage.width] = color
  }

  fun getPixel(x: Int, y: Int) =
    if (!coordInBounds(x, y)) -1
    else pixelData[x + y * targetImage.width]

  /**
   * Basic function to draw line between two points.
   * This function uses the Bresenham's algorithm.
   */
  fun drawLine(x1: Int, y1: Int, x2: Int, y2: Int, color: Int) {
    var d = 0

    val dx = abs(x2 - x1)
    val dy = abs(y2 - y1)

    val dx2 = 2 * dx
    val dy2 = 2 * dy

    val ix = if (x1 < x2) 1 else -1
    val iy = if (y1 < y2) 1 else -1

    var x = x1
    var y = y1

    if (dx >= dy) {
      while (true) {
        setPixel(x, y, color)
        if (x == x2) break
        x += ix
        d += dy2
        if (d > dx) {
          y += iy
          d -= dx2
        }
      }
    } else {
      while (true) {
        setPixel(x, y, color)
        if (y == y2) break
        y += iy
        d += dx2
        if (d > dy) {
          x += ix
          d -= dy2
        }
      }
    }
  }

  /**
   * Creates an instance to directly access a [Graphics2D] object from the image passed.
   *
   * This renderer class aims to be free from the pre-created graphics, from [java.awt]
   * package. For this reason, any instance of that is keep here, however, if needed, the
   * graphics from [java.awt] can be retrieved from this function.
   */
  fun createGraphics() = targetImage.createGraphics()

  private fun coordInBounds(x: Int, y: Int) =
    (x >= 0) && (x < targetImage.width) &&
    (y >= 0) && (y < targetImage.height)

  companion object {
    /**
     * This color is not considered when trying to set individual pixels.
     */
    var transparentColor: Int = Color.MAGENTA.rgb
  }
}
