package lucasalfare.basicappengine.math


import lucasalfare.basicappengine.graphics.ScreenWidth
import lucasalfare.basicappengine.graphics.ScreenHeight
import kotlin.math.PI
import kotlin.math.tan

var FieldOfView = 45.0

fun getAspectRatio() = ScreenHeight / ScreenWidth

fun getZ0() = ((ScreenWidth / 2f) / tan((FieldOfView / 2f) * PI / 180f)).toFloat()

fun lerp(a: Float, b: Float, t: Float) =
  if (t < .5f) {
    a + (b - a) * t
  } else {
    b - (b - a) * (1f - t)
  }

fun percentile(min: Float, max: Float, target: Float) = ((target - min) / (max - min))
