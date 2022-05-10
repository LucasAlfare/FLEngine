package lucasalfare.basicappengine.graphics

import lucasalfare.basicappengine.Handleable

/**
 * The application type that can be handled by the [Engine] class.
 *
 * All subclasses from this can be passed to a [Engine] class instance.
 */
abstract class AbstractApp(var title: String) : Handleable {

  /**
   * This method is called before the [Engine] main loops is started,
   * then could be useful to initializes fields.
   */
  abstract fun init()

  /**
   * All the application update logic goes here.
   *
   * This method is called in a certain frequency, which is
   * determined by the [Engine] class.
   *
   * Also, this method receive via argument an [vararg] argument, being
   * the [index 0] a [Float] number normally used to fix logic
   * behaviours that can different based on the machine that is running
   * the main [Engine]'s loop.
   *
   * Note that [vararg] number can represent a fixed time or a
   * variable time, depending on the algorithm implemented by the
   * [Engine] class.
   */
  abstract override fun update(vararg args: Any)

  /**
   * All the application drawing/rendering logic goes here.
   *
   * This method is called in a certain frequency, which is
   * determined by the [Engine] class.
   */
  abstract override fun render(renderer: Renderer)
}
