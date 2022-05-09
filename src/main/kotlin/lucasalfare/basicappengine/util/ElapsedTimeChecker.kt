package lucasalfare.basicappengine.util

object ElapsedTimeChecker {
    fun check(times: Int, useNanos: Boolean, callback: () -> Unit) {
        val begin = if (useNanos) System.nanoTime() else System.currentTimeMillis()
        repeat(times) {
            callback()
        }
        val end = (if (useNanos) System.nanoTime() else System.currentTimeMillis()) - begin
        println("Spent " + end + (if (useNanos) "ns" else "ms") + " to perform " + times + " times.")
    }
}