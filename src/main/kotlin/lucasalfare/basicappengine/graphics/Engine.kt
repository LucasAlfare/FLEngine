package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.input.Input

class Engine(private val application: App) : Runnable {

  var width = 0
  var height = 0
  var scale = 1f

  private var frames = 0
  private var updates = 0
  private var isRunning = false
  lateinit var window: Window

  private lateinit var renderer: Renderer
  private lateinit var input: Input

  fun start() {
    window = Window(width, height, scale)
    renderer = Renderer(window.renderingImage)
    input = Input(window.canvas, scale)
    init()
    val t = Thread(this)
    t.start()
  }

  private fun init() {
    application.init(this)
  }

  override fun run() {
    isRunning = true
    val ns = 1e9 / RATE
    var now: Long
    var auxTimer = System.currentTimeMillis()
    var lastTime = System.nanoTime()
    var delta = 0f
    while (isRunning) {
      now = System.nanoTime()
      delta += ((now - lastTime) / ns).toFloat()
      lastTime = now
      while (delta >= 1) {
        //performs a bunch of updates
        update()
        delta--
      }

      //then render here
      render()

      //helper to measure some rates per second
      if (System.currentTimeMillis() - auxTimer >= 1000) {
        frames = 0
        updates = 0
        auxTimer = System.currentTimeMillis()
      }
    }
  }

  private fun update() {
    input.update()
    application.updateWithInternalDelta(this)
    updates++
  }

  private fun render() {
    renderer.clear()
    application.render(this, renderer)
    window.render(frames, updates)
    frames++
  }

  fun setSize(width: Int, height: Int) {
    this.width = width
    this.height = height
  }

  companion object {
    const val RATE = 60
  }
}