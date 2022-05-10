package lucasalfare.basicappengine.util

object ElapsedTimeChecker {
  fun check(times: Int, useNanos: Boolean, action: () -> Unit) {
    val begin = if (useNanos) System.nanoTime() else System.currentTimeMillis()
    repeat(times) { action() }
    val end = (if (useNanos) System.nanoTime() else System.currentTimeMillis()) - begin
    println("Spent " + end + (if (useNanos) "ns" else "ms") + " to perform the passed action " + times + " times.")
  }
}