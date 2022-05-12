package lucasalfare.basicappengine

import lucasalfare.basicappengine.graphics.Renderer

/**
 * Interface to describe an entity which usually can be
 * updated and rendered.
 *
 * This makes a standard pattern to all implementing entities.
 */
interface Handleable {

  /**
   * Updates anything related to this object.
   *
   * The arguments are supplied via a [vararg].
   */
  fun update(vararg args: Any = emptyArray())

  /**
   * Renders/Draws anything related to this object using the [r] object.
   */
  fun render(r: Renderer)
}
