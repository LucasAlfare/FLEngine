package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.*
import lucasalfare.basicappengine.math.Cube
import lucasalfare.basicappengine.math.Pyramid

class MainExample : AbstractApp() {

  private val cube = Cube()
  private val pyramid = Pyramid()

  init {
    cube.mesh.position.x += -15
  }

  override fun init(engine: Engine) {

  }

  override fun update(engine: Engine, deltaTime: Float) {
    pyramid.mesh.update(deltaTime) {
      pyramid.mesh.rotation.x += 1 * it
      pyramid.mesh.rotation.y += 1 * it
      pyramid.mesh.rotation.z += 1 * it
    }

    cube.mesh.update(deltaTime) {
      cube.mesh.rotation.x += 1 * it
      cube.mesh.rotation.y += 1 * it
      cube.mesh.rotation.z += 1 * it
    }
  }

  override fun render(engine: Engine, renderer: Renderer) {
    cube.mesh.render(renderer)
    pyramid.mesh.render(renderer)
  }
}

fun main() {
  val e = Engine(MainExample())
  e.setSize(ResolutionX.toInt(), ResolutionY.toInt())
  e.start()
}