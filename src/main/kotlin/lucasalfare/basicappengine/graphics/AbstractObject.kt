package lucasalfare.basicappengine.graphics

abstract class AbstractObject(var parent: AbstractObject?) {

  var x = 0
  var y = 0
  var width = 0
  var height = 0

  abstract fun init(e: Engine)
  abstract fun update(e: Engine, dt: Float)
  abstract fun render(e: Engine, r: Renderer)
}