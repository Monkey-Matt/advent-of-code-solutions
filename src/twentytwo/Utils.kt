package twentytwo

import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInputLines(name: String) = File("src/twentytwo", "$name.txt")
    .readLines()

/**
 * Read input as a single string
 */
fun readInput(name: String) = File("src", "$name.txt").readText()

fun <T> List<T>.split(splitOn: T): List<List<T>> {
    val groups = mutableListOf<MutableList<T>>()
    var sublistCount = 0
    this.forEach { item ->
        if (item == splitOn) {
            sublistCount++
        } else {
            if (groups.size <= sublistCount) {
                groups.add(mutableListOf())
            }
            val sublist = groups[sublistCount]
            sublist.add(item)
        }
    }
    return groups
}
