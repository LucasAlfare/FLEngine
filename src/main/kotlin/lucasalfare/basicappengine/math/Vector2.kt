package lucasalfare.basicappengine.math

data class UV(var u: Float = 0f, var v: Float = 0f) {
  override fun toString() = "($u, $v)"
}

data class Vector2(var x: Float = 0f, var y: Float = 0f) {
  override fun toString() = "[$x, $y]"
}