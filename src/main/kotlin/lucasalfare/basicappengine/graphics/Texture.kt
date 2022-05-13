package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.ResourcesPathPrefix
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Texture(res: String) {

  val image: BufferedImage = ImageIO.read(File(ResourcesPathPrefix + res))
}