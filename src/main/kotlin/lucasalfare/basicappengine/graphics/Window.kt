package lucasalfare.basicappengine.graphics

import java.awt.*
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Window(
  width: Int,
  height: Int,
  scale: Float
) {
  val renderingImage: BufferedImage
  val canvas: Canvas

  private var title = "Application"
  private val frame: JFrame

  private val strategy: BufferStrategy
  private val rootWindowGraphics: Graphics //aux to draw the main image of the window

  init {
    renderingImage = toCompatibleImage(BufferedImage(width, height, BufferedImage.TYPE_INT_RGB))
    canvas = Canvas()
    val canvasWidth = (width * scale).toInt()
    val canvasHeight = (height * scale).toInt()
    val windowDimension = Dimension(canvasWidth, canvasHeight)
    frame = JFrame("Application")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = BorderLayout()
    frame.add(canvas, BorderLayout.CENTER)
    frame.isResizable = false
    frame.isVisible = true
    canvas.createBufferStrategy(2)

    canvas.size = windowDimension
    canvas.preferredSize = windowDimension
    canvas.maximumSize = windowDimension
    canvas.minimumSize = windowDimension

    frame.pack()
    frame.setLocationRelativeTo(null)
    strategy = canvas.bufferStrategy
    rootWindowGraphics = strategy.drawGraphics
  }

  fun render(fps: Int, ups: Int) {
    rootWindowGraphics.drawImage(renderingImage, 0, 0, canvas.width, canvas.height, null)
    strategy.show()
    frame.title = "$title | $fps FPS, $ups UPS"
  }

  //source: https://stackoverflow.com/a/197060/4563960
  private fun toCompatibleImage(image: BufferedImage): BufferedImage {
    // obtain the current system graphical settings
    val gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice.defaultConfiguration

    /*
     * if image is already compatible and optimized for current system
     * settings, simply return it
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

    // return the [new/optimized] image
    return newImage
  }
}