package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.input.Input

@Suppress("SameParameterValue")
class Engine(private val app: AbstractApp) : Runnable {

  var width = 0
  var height = 0
  var scale = 1f

  private var frames = 0
  private var updates = 0
  lateinit var window: Window

  private lateinit var renderer: Renderer
  private lateinit var input: Input

  private lateinit var ratesMeasurementHelper: Thread
  private lateinit var mainThread: Thread

  fun start() {
    window = Window(
      width = width,
      height = height,
      scale = scale
    )

    renderer = Renderer(targetImage = window.renderingImage)

    input = Input(
      inputEventsGenerator = window.canvas,
      customScale = scale
    )

    mainThread = Thread(this)

    ratesMeasurementHelper = Thread {
      while(true) {
        /*
        window title update runs in a separated
        thread, in order to show the rates even
        the main app loop is not running
         */
        window.update(frames, updates)
        frames = 0
        updates = 0
        Thread.sleep(1000)
      }
    }

    window.title = app.title
    app.init()
    ratesMeasurementHelper.start()
    mainThread.start()
  }


  override fun run() {
    val rate = 1f / 60f
    var accumulator = 0f
    var curr: Long
    var last = System.currentTimeMillis()

    while (true) {
      curr = System.currentTimeMillis()
      val lastRenderTime = (curr - last) / 1000f
      accumulator += lastRenderTime
      last = curr

      while (accumulator > rate) {
        update(rate)
        accumulator -= rate
      }
      render()
    }
  }

  private fun update(step: Float) {
    input.update()
    app.update(step)
    updates++
  }

  private fun render() {
    renderer.clear()
    app.render(renderer)
    window.render()
    frames++
  }

  fun setSize(width: Int, height: Int) {
    this.width = width
    this.height = height
  }
}