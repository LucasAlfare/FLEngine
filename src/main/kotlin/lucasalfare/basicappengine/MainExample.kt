package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.AbstractApp
import lucasalfare.basicappengine.graphics.Engine
import lucasalfare.basicappengine.graphics.Renderer
import java.util.*

class MainExample : AbstractApp() {

  private lateinit var random: Random

  private var red = 0
  private var green = 0
  private var blue = 0
  private var alpha = 255
  private var color = 0

  override fun init(engine: Engine) {
    random = Random()
  }

  override fun update(engine: Engine, deltaTime: Float) {}

  override fun render(engine: Engine, renderer: Renderer) {
    for (i in 0 until engine.width) {
      for (j in 0 until engine.height) {
        red = random.nextInt(255)
        green = random.nextInt(255)
        blue = random.nextInt(255)
        color = red shl 8 or green shl 8 or blue shl 8 or alpha
        renderer.setPixel(i, j, color)
      }
    }
  }
}

fun main() {
  val e = Engine(MainExample())
  e.setSize(500, 500)
  e.start()
}