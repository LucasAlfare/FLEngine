package lucasalfare.basicappengine.graphics

import java.awt.Graphics2D
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.util.*
import kotlin.math.abs

@Suppress("MemberVisibilityCanBePrivate")
class Renderer(targetImage: BufferedImage) {

  var g: Graphics2D

  private val width: Int
  private val height: Int

  private var pixelData: IntArray

  var clearColor = 0x00_00_00

  init {
    pixelData = (targetImage.raster.dataBuffer as DataBufferInt).data
    g = targetImage.createGraphics()
    width = targetImage.width
    height = targetImage.height
  }

  fun clear(clearColor: Int = this.clearColor) {
    if (clearColor != this.clearColor) this.clearColor = clearColor
    Arrays.fill(pixelData, clearColor)
  }

  fun setPixel(x: Int, y: Int, value: Int): Boolean {
    if (
      (x < 0) ||
      (x >= width) ||
      (y < 0) ||
      (y >= height) ||
      (value == -0xff01)
    ) {
      return false
    }

    pixelData[x + y * width] = value
    return true
  }

  fun getPixel(x: Int, y: Int): Int {
    return if (x < 0 || x >= width || y < 0 || y >= height) {
      -1
    } else pixelData[x + y * width]
  }

  fun drawHorizontalLine(x1: Int, x2: Int, y: Int, color: Int) {
    val xDiff = abs(x1 - x2)
    repeat(xDiff) {
      setPixel(x1 + it, y, color)
    }
  }
}
