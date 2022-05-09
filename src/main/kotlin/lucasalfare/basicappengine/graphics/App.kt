package lucasalfare.basicappengine.graphics

abstract class App {
  private var last: Long = 0

  @Volatile
  private var started = false

  abstract fun init(engine: Engine)
  abstract fun update(engine: Engine, deltaTime: Float)
  abstract fun render(engine: Engine, renderer: Renderer)

  fun updateWithInternalDelta(engine: Engine) {
    if (!started) {
      started = true
      last = System.nanoTime()
    }
    val now = System.nanoTime()
    val mDelta = (now - last) / 1e9f
    last = now
    this.update(engine, mDelta)
  }
}
