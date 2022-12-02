fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 24_000)
    println(part2(testInput))
//    check(part2(testInput) == 45_000)

    println("---")
    val input = readInput("Day02_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: List<String>): Int {
    return input.size
}


private fun part2(input: List<String>): Int {
    return input.size
}
