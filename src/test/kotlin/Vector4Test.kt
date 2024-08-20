import com.lucasalfare.flengine.math.Matrix4x4
import com.lucasalfare.flengine.math.Vector4
import junit.framework.TestCase.assertEquals
import kotlin.test.Test

class Vector4Test {

  @Test
  fun testLength() {
    val vector = Vector4(1f, 2f, 3f, 4f)
    assertEquals(5.477f, vector.length(), 0.001f)
  }

  @Test
  fun testDotProduct() {
    val vectorA = Vector4(1f, 2f, 3f, 4f)
    val vectorB = Vector4(5f, 6f, 7f, 8f)
    assertEquals(70f, vectorA.dotProduct(vectorB))
  }

  @Test
  fun testAdd() {
    val vectorA = Vector4(1f, 2f, 3f, 4f)
    val vectorB = Vector4(4f, 3f, 2f, 1f)
    val result = vectorA.add(vectorB)
    val expected = Vector4(5f, 5f, 5f, 5f)
    assertEquals(expected.toString(), result.toString())
  }

  @Test
  fun testSubtract() {
    val vectorA = Vector4(1f, 2f, 3f, 4f)
    val vectorB = Vector4(4f, 3f, 2f, 1f)
    val result = vectorA.subtract(vectorB)
    val expected = Vector4(-3f, -1f, 1f, 3f)
    assertEquals(expected.toString(), result.toString())
  }

  @Test
  fun testScale() {
    val vector = Vector4(1f, 2f, 3f, 4f)
    val scalar = 2f
    val result = vector.scale(scalar)
    val expected = Vector4(2f, 4f, 6f, 8f)
    assertEquals(expected.toString(), result.toString())
  }

  @Test
  fun testNormalized() {
    val vector = Vector4(1f, 2f, 3f, 4f)
    val calculatedLength = vector.length()
    val result = vector.normalized()

    println(result)

    val expected = Vector4(1f, 2f, 3f, 4f)
      .scale(1f / calculatedLength)

    assertEquals(expected, result)
  }

  @Test
  fun testTransform() {
    val vector = Vector4(1f, 2f, 3f, 1f)
    val matrix = Matrix4x4(
      floatArrayOf(
        1f, 0f, 0f, 0f,
        0f, 1f, 0f, 0f,
        0f, 0f, 1f, 0f,
        0f, 0f, 0f, 1f
      )
    )
    val result = vector.transform(matrix)
    assertEquals(vector.toString(), result.toString())
  }
}