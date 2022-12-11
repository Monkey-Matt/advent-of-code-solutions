fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day04_test")
    println(part1(testInput))
    check(part1(testInput) == 2)
    println(part2(testInput))
    check(part2(testInput) == 4)

    println("---")
    val input = readInputLines("Day04_input")
    println(part1(input))
    println(part2(input))

    testAlternativeSolutions()
}


private fun part1(input: List<String>): Int {
    val pairContains = input.map { elfPair ->
        val (firstElf, secondElf) = elfPair.split(',')
        val (firstStart, firstEnd) = firstElf.split('-').map { it.toInt() }
        val (secondStart, secondEnd) = secondElf.split('-').map { it.toInt() }
        (firstStart <= secondStart && firstEnd >= secondEnd) || (secondStart <= firstStart && secondEnd >= firstEnd)
    }
    return pairContains.count { it }
}


private fun part2(input: List<String>): Int {
    val pairContains = input.map { elfPair ->
        val (firstElf, secondElf) = elfPair.split(',')
        val (firstStart, firstEnd) = firstElf.split('-').map { it.toInt() }
        val (secondStart, secondEnd) = secondElf.split('-').map { it.toInt() }
        secondStart in firstStart..firstEnd || secondEnd in firstStart .. firstEnd || firstStart in secondStart..secondEnd || firstEnd in secondStart .. secondEnd
    }
    return pairContains.count { it }
}

// ------------------------------------------------------------------------------------------------

private fun testAlternativeSolutions() {
    val testInput = readInputLines("Day04_test")
    check(part1AlternativeSolution(testInput) == 2)
    check(part2AlternativeSolution(testInput) == 4)

    println("Alternative Solutions:")
    val input = readInputLines("Day04_input")
    println(part1AlternativeSolution(input))
    println(part2AlternativeSolution(input))
}

private fun part1AlternativeSolution(input: List<String>): Int {
    return input
        .map { it.toRanges() }
        .map { it.first fullyContains it.second || it.second fullyContains it.first }
        .count { it }
}

private fun part2AlternativeSolution(input: List<String>): Int {
    return input
        .map { it.toRanges() }
        .map { it.first overlaps it.second }
        .count { it }
}

private fun String.toRanges(): Pair<IntRange, IntRange> {
    return this
        .let {
            it.substringBefore(",") to it.substringAfter(",")
        }
        .let { pair ->
            pair.onEach {
                val start = it.substringBefore("-").toInt()
                val end = it.substringAfter("-").toInt()
                IntRange(start, end)
            }
        }
}

private fun <T, R> Pair<T, T>.onEach(transform: (T) -> R): Pair<R, R> {
    return Pair(transform(this.first), transform(this.second))
}

private infix fun IntRange.fullyContains(other: IntRange): Boolean {
    return this.all { it in other }
}

private infix fun IntRange.overlaps(other: IntRange): Boolean {
    return this.any { it in other }
}
