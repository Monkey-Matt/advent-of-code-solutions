/**
 * I used brute force (marking the route on the input) to solve this one initially :|
 * Took just under an hour. I really didn't know how to solve it in code
 */
fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day12_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 31)
    val part2 = part2(testInput)
    println(part2)
//    check(part2 == 45_000)

    println("---")
    val input = readInputLines("Day12_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: List<String>): Int {
    return input.size
}


private fun part2(input: List<String>): Int {
    return input.size
}
