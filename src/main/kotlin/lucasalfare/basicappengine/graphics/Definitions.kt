package lucasalfare.basicappengine.graphics

import java.awt.Color
import java.awt.GraphicsEnvironment
import java.awt.image.BufferedImage

/**
 * This color is not considered when trying to set individual pixels.
 */
var transparentColor: Int = Color.MAGENTA.rgb

//source: https://stackoverflow.com/a/197060/4563960
fun createCompatibleImageBasedOn(image: BufferedImage): BufferedImage {
  // obtain the current system graphical settings
  val gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration

  /*
   if image is already compatible and optimized for current system
   settings, simply return it
   */
  if (image.colorModel == gfxConfig.colorModel) return image

  // image is not optimized, so create a new image that is
  val newImage = gfxConfig.createCompatibleImage(
    image.width, image.height, image.transparency
  )

  // get the graphics context of the new image to draw the old image on
  val g2d = newImage.createGraphics()

  // actually draw the image and dispose of context no longer needed
  g2d.drawImage(image, 0, 0, null)
  g2d.dispose()

  // return the new/optimized image
  return newImage
}