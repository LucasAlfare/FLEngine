package lucasalfare.basicappengine.graphics

import java.awt.*
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Window(engine: Engine) {
  val frame: JFrame
  val image: BufferedImage
  val canvas: Canvas

  private val strategy: BufferStrategy
  private val rootWindowGraphics: Graphics //aux to draw the main image of the window

  init {
    image = toCompatibleImage(BufferedImage(engine.width, engine.height, BufferedImage.TYPE_INT_RGB))
    canvas = Canvas()
    val canvasWidth = (engine.width * engine.scale).toInt()
    val canvasHeight = (engine.height * engine.scale).toInt()
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

  fun render() {
    rootWindowGraphics.drawImage(image, 0, 0, canvas.width, canvas.height, null)
    strategy.show()
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