package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.ResourcesPathPrefix
import lucasalfare.basicappengine.math.UV
import java.awt.image.BufferedImage
import java.awt.image.DataBufferInt
import java.io.File
import javax.imageio.ImageIO

class Texture(res: String) {

  private val image: BufferedImage =
    createCompatibleImageBasedOn(
      ImageIO.read(File(ResourcesPathPrefix + res))
    )
  private val pixelData: IntArray =
    (image.raster.dataBuffer as DataBufferInt).data

  fun getPixel(uv: UV): Int {
    val x = (uv.u * image.width).toInt()
    val y = (uv.v * image.height).toInt()
    return getPixel(x, y)
  }

  private fun getPixel(x: Int, y: Int) =
    if (!coordInBounds(x, y))
      -1
    else
      pixelData[x + y * image.width]

  private fun coordInBounds(x: Int, y: Int) =
    (x >= 0) && (x < image.width) && (y >= 0) && (y < image.height)
}
