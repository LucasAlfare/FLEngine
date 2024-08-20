package com.lucasalfare.flengine.geometry

import com.lucasalfare.flengine.math.Vector3

/**
 * The points here must be declared in a consistent order.
 */
@Suppress("MemberVisibilityCanBePrivate")
data class Triangle(
  var p0: Vector3,
  var p1: Vector3,
  var p2: Vector3
)