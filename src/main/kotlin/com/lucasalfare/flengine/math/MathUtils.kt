package com.lucasalfare.flengine.math


class MathUtils {
  companion object {
    fun lerp(a: Float, b: Float, percentile: Float) =
      if (percentile < .5f) a + (b - a) * percentile
      else b - (b - a) * (1f - percentile)

    fun percentile(min: Float, max: Float, target: Float) =
      ((target - min) / (max - min))
  }
}
