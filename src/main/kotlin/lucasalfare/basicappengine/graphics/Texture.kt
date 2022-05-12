package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.ResourcesPathPrefix
import lucasalfare.basicappengine.geometry.Triangle
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Texture(resourcePath: String) {

  val image: BufferedImage = ImageIO.read(File(ResourcesPathPrefix + resourcePath))

  fun Triangle.map() {

  }
}