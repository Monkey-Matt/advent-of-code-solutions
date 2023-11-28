package twentytwo

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day01_test")
//    println(part1(testInput))
    check(part1(testInput) == 24_000)
//    println(part2(testInput))
    check(part2(testInput) == 45_000)

    val input = readInputLines("Day01_input")
    println(part1(input))
    println(part2(input))

    testAlternativeSolutions()
}


private fun part1(input: List<String>): Int {
    val groupTotals = getElfTotals(input)
    return groupTotals.max()
}

private fun getElfTotals(input: List<String>): MutableList<Int> {
    val groups = input.split("")
    return groups.map { group -> group.sumOf { it.toInt() } }.toMutableList()
}


private fun part2(input: List<String>): Int {
    val elfTotals = getElfTotals(input)
    val one = elfTotals.max()
    elfTotals.remove(one)
    val two = elfTotals.max()
    elfTotals.remove(two)
    val three = elfTotals.max()
    return one + two + three
}

// ------------------------------------------------------------------------------------------------

private fun testAlternativeSolutions() {
    val testInput = readInput("Day01_test")
    check(part1AlternativeSolution(testInput) == 24_000)
    check(part2AlternativeSolution(testInput) == 45_000)

    println("Alternative Solutions:")
    val input = readInputLines("Day01_input")
    println(part1(input))
    println(part2(input))
}

private fun part1AlternativeSolution(input: String): Int {
    val elfTotals = getElfTotalsAlternative(input)
    return elfTotals.max()
}

private fun part2AlternativeSolution(input: String): Int {
    val elfTotals = getElfTotalsAlternative(input)
    return elfTotals.sortedDescending().take(3).sum()
}

private fun getElfTotalsAlternative(input: String): List<Int> {
    return input.split("\n\n").map { elfGroup ->
        elfGroup.lines().sumOf { it.toInt() }
    }
}
