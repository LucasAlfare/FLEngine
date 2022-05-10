package lucasalfare.basicappengine.graphics

abstract class AbstractApp(var title: String) {

  abstract fun init()
  abstract fun update(step: Float)
  abstract fun render(renderer: Renderer)
}
