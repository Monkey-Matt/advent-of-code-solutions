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
