package lucasalfare.basicappengine.math


import lucasalfare.basicappengine.graphics.ResolutionX
import lucasalfare.basicappengine.graphics.ResolutionY
import kotlin.math.PI
import kotlin.math.tan

var FieldOfView = 45.0

fun getAspectRatio() = ResolutionY / ResolutionX

fun getZ0() = (ResolutionX / 2.0) / tan((FieldOfView / 2.0) * PI / 180)
