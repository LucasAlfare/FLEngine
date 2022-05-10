package lucasalfare.basicappengine.input

import java.awt.Component
import java.awt.Point
import java.awt.event.*

class Input(
  inputEventsGenerator: Component,
  private var customScale: Float = 1f
) : KeyListener,
  MouseListener,
  MouseMotionListener,
  MouseWheelListener {

  private var mouseMoving = false

  init {
    mousePoint = Point(Companion.mouseX, Companion.mouseY)
    inputEventsGenerator.addKeyListener(this)
    inputEventsGenerator.addMouseListener(this)
    inputEventsGenerator.addMouseMotionListener(this)
    inputEventsGenerator.addMouseWheelListener(this)
    inputEventsGenerator.requestFocus()
  }

  fun update() {
    Companion.mouseScroll = 0
    System.arraycopy(keys, 0, lastKeys, 0, NUM_KEYS)
    System.arraycopy(buttons, 0, lastButtons, 0, NUM_BUTTONS)
    lastMouseX = Companion.mouseX
    lastMouseY = Companion.mouseY
  }

  override fun keyTyped(e: KeyEvent) {
    /*--*/
  }

  override fun keyPressed(e: KeyEvent) {
    keys[e.keyCode] = true
  }

  override fun keyReleased(e: KeyEvent) {
    keys[e.keyCode] = false
  }

  override fun mouseClicked(e: MouseEvent) {}

  override fun mousePressed(e: MouseEvent) {
    buttons[e.button] = true
  }

  override fun mouseReleased(e: MouseEvent) {
    buttons[e.button] = false
  }

  override fun mouseEntered(e: MouseEvent) {
    mouseMoving = true
  }

  override fun mouseExited(e: MouseEvent) {
    mouseMoving = false
  }

  override fun mouseDragged(e: MouseEvent) {
    Companion.mouseX = (e.x / customScale).toInt()
    Companion.mouseY = (e.y / customScale).toInt()
    mousePoint.x = Companion.mouseX
    mousePoint.y = Companion.mouseY
    mouseMoving = true
  }

  override fun mouseMoved(e: MouseEvent) {
    Companion.mouseX = (e.x / customScale).toInt()
    Companion.mouseY = (e.y / customScale).toInt()
    mousePoint.x = Companion.mouseX
    mousePoint.y = Companion.mouseY
    mouseMoving = true
  }

  override fun mouseWheelMoved(e: MouseWheelEvent) {
    Companion.mouseScroll = e.wheelRotation
  }

  val mouseX: Int
    get() = Companion.mouseX
  val mouseY: Int
    get() = Companion.mouseY
  val mouseScroll: Int
    get() = Companion.mouseScroll

  companion object {
    //TODO: consider update to handle more codes from virtual keys above 256
    const val NUM_KEYS = 256
    const val NUM_BUTTONS = 5

    private val keys = Array(NUM_KEYS) { false }
    private var lastKeys = Array(NUM_KEYS) { false }
    private var buttons = Array(NUM_BUTTONS) { false }
    private var lastButtons = Array(NUM_BUTTONS) { false }

    var mousePoint = Point()

    var mouseX = -1
    var mouseY = -1
    var lastMouseX = -1
    var lastMouseY = -1
    var mouseScroll = 0

    fun isKey(keyCode: Int): Boolean {
      return keys[keyCode]
    }

    fun isKeyUp(keyCode: Int): Boolean {
      return !keys[keyCode] && lastKeys[keyCode]
    }

    fun isKeyDown(keyCode: Int): Boolean {
      return keys[keyCode] && !lastKeys[keyCode]
    }

    fun isMouseButton(button: Int): Boolean {
      return buttons[button]
    }

    fun isMouseButtonUp(button: Int): Boolean {
      return !buttons[button] && lastButtons[button]
    }

    fun isMouseButtonDown(button: Int): Boolean {
      return buttons[button] && !lastButtons[button]
    }

    val isMouseMoving: Boolean
      get() = mouseX != lastMouseX || mouseY != lastMouseY
  }
}
