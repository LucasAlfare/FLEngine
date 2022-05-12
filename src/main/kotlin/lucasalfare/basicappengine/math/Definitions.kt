package lucasalfare.basicappengine.math


import lucasalfare.basicappengine.graphics.ScreenWidth
import lucasalfare.basicappengine.graphics.ScreenHeight
import kotlin.math.PI
import kotlin.math.tan

var FieldOfView = 45.0

fun getAspectRatio() = ScreenHeight / ScreenWidth

fun getZ0() = ((ScreenWidth / 2f) / tan((FieldOfView / 2f) * PI / 180f)).toFloat()
