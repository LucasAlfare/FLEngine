package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.*
import lucasalfare.basicappengine.math.Cube
import lucasalfare.basicappengine.math.Mesh
import lucasalfare.basicappengine.math.Triangle
import lucasalfare.basicappengine.math.Vector3

class MainExample : AbstractApp() {

  private val cube = Cube()

  override fun init(engine: Engine) {

  }

  override fun update(engine: Engine, deltaTime: Float) {

  }

  override fun render(engine: Engine, renderer: Renderer) {

  }
}

fun main() {
  val e = Engine(MainExample())
  e.setSize(ResolutionX.toInt(), ResolutionY.toInt())
  e.start()
}