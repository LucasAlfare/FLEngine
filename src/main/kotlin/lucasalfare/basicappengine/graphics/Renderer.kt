package lucasalfare.basicappengine.graphics

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.image.DataBufferInt
import java.util.*

@Suppress("MemberVisibilityCanBePrivate")
class Renderer(engine: Engine) {

  var g: Graphics

  @JvmField
  var g2d: Graphics2D

  private val imageWidth: Int
  private val imageHeight: Int

  private var pixelData: IntArray

  var clearColor = 0

  init {
    val img = engine.window.image
    pixelData = (img.raster.dataBuffer as DataBufferInt).data
    g = img.graphics
    g2d = img.createGraphics()
    imageWidth = engine.window.image.width
    imageHeight = engine.window.image.height
  }

  fun clear() {
    Arrays.fill(pixelData, clearColor)
  }

  fun setPixel(x: Int, y: Int, value: Int): Boolean {
    if (
      (x < 0) ||
      (x >= imageWidth) ||
      (y < 0) ||
      (y >= imageHeight) ||
      (value == -0xff01)
    ) {
      return false
    }

    pixelData[x + y * imageWidth] = value
    return true
  }

  fun getPixel(x: Int, y: Int): Int {
    return if (x < 0 || x >= imageWidth || y < 0 || y >= imageHeight) {
      -1
    } else pixelData[x + y * imageWidth]
  }
}
