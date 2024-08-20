package com.lucasalfare.flengine.graphics

import java.awt.BorderLayout
import java.awt.Canvas
import java.awt.Dimension
import java.awt.image.BufferStrategy
import java.awt.image.BufferedImage
import javax.swing.JFrame

class Window(
  width: Int,
  height: Int,
  scale: Float
) {

  /**
   * Main application title.
   *
   * This is public, then could be set from outside if needed.
   */
  var title = ""

  /**
   * This is the target image that will be constantly redrawn
   * on each frame by a [Renderer].
   */
  //setup root image
  val renderingImage: BufferedImage = GraphicsUtils.createCompatibleImageBasedOn(
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

  /**
   * This canvas works as a container to hold the [renderingImage].
   *
   * This also provides the initialization of an [BufferStrategy]
   * object which is, for this engine, is the root source of the
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

    //setup canvas
    val canvasWidth = (width * scale).toInt()
    val canvasHeight = (height * scale).toInt()
    val windowDimension = Dimension(canvasWidth, canvasHeight)
    canvas = Canvas()
    canvas.size = windowDimension
    canvas.preferredSize = windowDimension
    canvas.maximumSize = windowDimension
    canvas.minimumSize = windowDimension

    //setup frame
    frame = JFrame(title)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.layout = BorderLayout()
    frame.add(canvas, BorderLayout.CENTER)
    frame.isResizable = false
    frame.pack() // this makes the frame "match" to the canvas dimension
    frame.setLocationRelativeTo(null)
    frame.isVisible = true //must be visible to be able to create a [Strategy]

    //setup buffer strategy
    canvas.createBufferStrategy(2)
    strategy = canvas.bufferStrategy
  }

  /**
   * Updates title with current received rates that comes from the
   * [Engine].
   */
  fun update(fps: Int, ups: Int) {
    frame.title = "$title | $fps FPS, $ups UPS"
  }

  fun render() {
    strategy.drawGraphics.drawImage(
      renderingImage, 0, 0,
      canvas.width, canvas.height,
      null
    )
    strategy.show()
  }
}