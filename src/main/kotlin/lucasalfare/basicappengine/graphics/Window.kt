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

  var title = ""

  /**
   * This is the target image that will be constantly redrawn
   * on each frame by a [Renderer].
   */
  val renderingImage: BufferedImage

  /**
   * This canvas works as a container to hold the [renderingImage].
   *
   * This also provides the initialization of an [BufferStrategy]
   * object and, for this engine, is the root source of the
   * input events (mouse and keyboard).
   */
  val canvas: Canvas

  /**
   * Root container to show the application up.
   */
  private val frame: JFrame

  /**
   * Basic reference of an [BufferStrategy] object, created from
   * the [canvas] field.
   *
   * This object is used to draw the [renderingImage] to the screen
   * based in the back buffer strategy.
   */
  private val strategy: BufferStrategy

  init {
    renderingImage = createCompatibleImageBasedOn(
      BufferedImage(
        width,
        height,
        /*
         The color format is set to the simple RGB (e.g.: 0xRR_GG_BB)
         once transparency is handled directly by the [Renderer].
         */
        BufferedImage.TYPE_INT_RGB
      )
    )
    canvas = Canvas()
    val canvasWidth = (width * scale).toInt()
    val canvasHeight = (height * scale).toInt()
    val windowDimension = Dimension(canvasWidth, canvasHeight)

    canvas.size = windowDimension
    canvas.preferredSize = windowDimension
    canvas.maximumSize = windowDimension
    canvas.minimumSize = windowDimension

    frame = JFrame(title)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = BorderLayout()
    frame.add(canvas, BorderLayout.CENTER)
    frame.isResizable = false
    frame.pack() // this makes the frame "match" the canvas dimension
    frame.setLocationRelativeTo(null)
    frame.isVisible = true //must be visible to be able to create a [Strategy]

    canvas.createBufferStrategy(2)
    strategy = canvas.bufferStrategy
  }

  fun update(fps: Int, ups: Int) {
    frame.title = "$title | $fps FPS, $ups UPS"
  }

  fun render() {
    strategy.drawGraphics.drawImage(
      renderingImage,
      0, 0,
      canvas.width, canvas.height,
      null
    )
    strategy.show()
  }

  //source: https://stackoverflow.com/a/197060/4563960
  private fun createCompatibleImageBasedOn(image: BufferedImage): BufferedImage {
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
}