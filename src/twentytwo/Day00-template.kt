package twentytwo

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day0X_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 24_000)
    val part2 = part2(testInput)
    println(part2)
//    check(part2 == 45_000)

    println("---")
    val input = readInputLines("Day0X_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: List<String>): Int {
    return input.size
}


private fun part2(input: List<String>): Int {
    return input.size
}
