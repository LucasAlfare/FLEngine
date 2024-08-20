package com.lucasalfare.flengine

/**
 * This file demonstrates some mesh rendering using the
 * own engine API.
 */
import com.lucasalfare.flengine.graphics.Engine
import com.lucasalfare.flengine.graphics.Renderer
import com.lucasalfare.flengine.graphics.ScreenHeight
import com.lucasalfare.flengine.graphics.ScreenWidth

class MyApp(title: String) : App(title) {

  override fun init() {

  }

  override fun update(vararg args: Any) {
    //val timeStep = args[0] as Float
  }

  override fun render(r: Renderer) {

  }
}

fun main() {
  val e = Engine(MyApp("Meu teste de render  3D"))
  e.setSize(ScreenWidth.toInt(), ScreenHeight.toInt())
  e.start()
}