package lucasalfare.basicappengine.graphics

class Engine(private val application: AbstractApp) : Runnable {

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
    window = Window(this)
    renderer = Renderer(this)
    input = Input(this)
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
      renderGame()

      //helper to measure some rates per second
      if (System.currentTimeMillis() - auxTimer >= 1000) {
        window.frame.title = "$frames FPS | $updates UPS"
        frames = 0
        updates = 0
        auxTimer = System.currentTimeMillis()
      }
    }
  }

  private fun update() {
    application.updateWithInternalDelta(this)
    input.update()
    updates++
  }

  private fun renderGame() {
    renderer.clear()
    application.render(this, renderer)
    window.render()
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