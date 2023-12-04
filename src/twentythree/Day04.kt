package twentythree

import kotlin.math.pow

const val day = "04"
fun main() {
    // test if implementation meets criteria from the description:
    println("Day$day Test Answers:")
    val testInput = readInputLines("Day${day}_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 13)
    val part2 = part2(testInput)
    part2.println()
    check(part2 == 30)

    println("---")
    println("Solutions:")
    val input = readInputLines("Day${day}_input")
    part1(input).println()
    part2(input).println()

    testAlternativeSolutions()
}


val points = listOf(0, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 4096*2, 4096*4, 4096*8, 4096*16)
private fun part1(input: List<String>): Int {
    return input.sumOf { inputLine ->
        val (_, gameLine) = inputLine.split(":")
        val (targetsString, ourScoresString) = gameLine.split("|")
        val targets = targetsString.trim().split(" ").filter { it != "" }
        val ourScores = ourScoresString.trim().split(" ").filter { it != "" }
        val countingScores = ourScores.filter { targets.contains(it) }
        points[countingScores.size]
    }
}


private fun part2(input: List<String>): Int {
    val cards = MutableList(input.size) { 1 }
    input.forEachIndexed { index, inputLine ->
        val (_, gameLine) = inputLine.split(":")
        val (targetsString, ourScoresString) = gameLine.split("|")
        val targets = targetsString.trim().split(" ").filter { it != "" }
        val ourScores = ourScoresString.trim().split(" ").filter { it != "" }
        val countingScores = ourScores.filter { targets.contains(it) }
        val winning = countingScores.size
        // add winning copies
        for (i in index+1..(index+winning)) {
            cards[i] += cards[index]
        }
    }
    return cards.sum()
}


// ------------------------------------------------------------------------------------------------

private fun testAlternativeSolutions() {
    val testInput = readInputLines("Day${day}_test")
    check(alternativePart1(testInput) == 13)
//    check(part2AlternativeSolution(testInput) == 281)

    println("Alternative Solutions:")
    val input = readInputLines("Day${day}_input")
    println(alternativePart1(input))
//    println(part2(input))
}

private fun alternativePart1(input: List<String>): Int {
    return input.sumOf { inputLine ->
        val (_, gameLine) = inputLine.split(":")
        val (targetsString, ourScoresString) = gameLine.split("|")
        val targets = targetsString.trim().replace("  ", " ").split(" ")
        val ourScores = ourScoresString.trim().replace("  ", " ").split(" ")
        val countingScores = ourScores.count { targets.contains(it) }
        if (countingScores == 0) {
            0
        } else {
            2.pow(countingScores-1)
        }
    }
}

fun Int.pow(n: Int): Int {
    return this.toDouble().pow(n.toDouble()).toInt()
}
