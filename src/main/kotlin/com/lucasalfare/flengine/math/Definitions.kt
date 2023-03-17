package com.lucasalfare.flengine.math


import com.lucasalfare.flengine.graphics.ScreenWidth
import com.lucasalfare.flengine.graphics.ScreenHeight
import kotlin.math.PI
import kotlin.math.tan

var FieldOfView = 45.0

fun getAspectRatio() = ScreenHeight / ScreenWidth

fun getZ0() = ((ScreenWidth / 2f) / tan((FieldOfView / 2f) * PI / 180f)).toFloat()

fun lerp(a: Float, b: Float, percentile: Float) =
  if (percentile < .5f) {
    a + (b - a) * percentile
  } else {
    b - (b - a) * (1f - percentile)
  }

fun percentile(min: Float, max: Float, target: Float) = ((target - min) / (max - min))
