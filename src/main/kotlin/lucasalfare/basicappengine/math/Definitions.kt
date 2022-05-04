package lucasalfare.basicappengine.math

import lucasalfare.basicappengine.graphics.FieldOfView
import lucasalfare.basicappengine.graphics.Renderer
import lucasalfare.basicappengine.graphics.ResolutionX
import kotlin.math.PI
import kotlin.math.tan

fun getZ0() = (ResolutionX / 2.0) / tan((FieldOfView / 2.0) * PI / 180)