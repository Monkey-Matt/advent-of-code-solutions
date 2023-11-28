package twentythree

fun main() {
    val day = "01"
    // test if implementation meets criteria from the description:
    println("Day$day Test Answers:")
    val testInput = readInputLines("Day${day}_test")
    val part1 = part1(testInput)
    part1.println()
//    check(part1 == 24_000)
    val part2 = part2(testInput)
    part2.println()
//    check(part2 == 45_000)

    println("---")
    println("Solutions:")
    val input = readInputLines("Day${day}_input")
    part1(input).println()
    part2(input).println()
}


private fun part1(input: List<String>): Int {
    return input.size
}


private fun part2(input: List<String>): Int {
    return input.size
}
