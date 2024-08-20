import com.lucasalfare.flengine.math.Matrix4x4
import kotlin.test.Test
import kotlin.test.assertEquals

class Matrix4x4Test {

  @Test
  fun testSetIdentify() {
    val matrix = Matrix4x4()
    matrix.setIdentify()

    val expected = Matrix4x4(
      floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected = expected.toString(), actual = matrix.toString())
  }

  @Test
  fun testMultiply() {
    val matrixA = Matrix4x4(
      floatArrayOf(
        1f, 2f, 3f, 4f,
        5f, 6f, 7f, 8f,
        9f, 10f, 11f, 12f,
        13f, 14f, 15f, 16f
      )
    )

    val matrixB = Matrix4x4(
      floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    val result = matrixA.multiply(matrixB)

    assertEquals(expected = matrixA.toString(), actual = result.toString())
  }

  @Test
  fun testRotationX() {
    val angle = 90f // 90 degrees
    val rotationMatrixOnAngle = Matrix4x4.rotationX(angle)
    val expected = Matrix4x4(
      floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 0f, -1f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected = expected.toString(), actual = rotationMatrixOnAngle.toString())
  }

  @Test
  fun testRotationY() {
    val angle = 90f // 90 degrees
    val rotationMatrixOnAngle = Matrix4x4.rotationY(angle)
    val expected = Matrix4x4(
      floatArrayOf(
        0f, 0f, 1f, 0f,
        0f, 1f, 0f, 0f,
        -1f, 0f, 0f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected = expected.toString(), actual = rotationMatrixOnAngle.toString())
  }

  @Test
  fun testRotationZ() {
    val angle = 90f // 90 degrees
    val rotationMatrixOnAngle = Matrix4x4.rotationZ(angle)
    val expected = Matrix4x4(
      floatArrayOf(
        0f, -1f, 0f, 0f,
        1f, 0f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected = expected.toString(), actual = rotationMatrixOnAngle.toString())
  }

  @Test
  fun testTranslationMatrix() {
    val tx = 5f
    val ty = -3f
    val tz = 2f
    val result = Matrix4x4.translationMatrix(tx, ty, tz)

    val expected = Matrix4x4(
      floatArrayOf(
        1f, 0f, 0f, 5f,
        0f, 1f, 0f, -3f,
        0f, 0f, 1f, 2f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected.toString(), result.toString())
  }

  @Test
  fun testScaleMatrix() {
    val sx = 2f
    val sy = 3f
    val sz = 4f
    val result = Matrix4x4.scaleMatrix(sx, sy, sz)

    val expected = Matrix4x4(
      floatArrayOf(
        2f, 0f, 0f, 0f,
        0f, 3f, 0f, 0f,
        0f, 0f, 4f, 0f,
        0f, 0f, 0f, 1f
      )
    )

    assertEquals(expected.toString(), result.toString())
  }
}