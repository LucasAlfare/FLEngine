package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.Renderer

/**
 * Interface to describe an entity which usually can be
 * updated and rendered.
 */
interface Handleable {

  /**
   * Updates anything related to this object.
   *
   * The arguments are supplied via a [vararg].
   */
  fun update(vararg args: Any = emptyArray())

  /**
   * Renders/Draws anything related to this object using the [renderer] object.
   */
  fun render(renderer: Renderer)
}
