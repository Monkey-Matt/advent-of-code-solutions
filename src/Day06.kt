fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    println(part1(testInput))
    check(part1(testInput) == 7)
    println(part2(testInput))
    check(part2(testInput) == 19)

    println("---")
    val input = readInput("Day06_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: String): Int {
    return positionOfFirstUniqueChain(input, 4)
}


private fun part2(input: String): Int {
    return positionOfFirstUniqueChain(input, 14)
}

private fun positionOfFirstUniqueChain(input: String, chainLength: Int): Int {
    val recentChainChars = MutableList(chainLength) { ' ' }
    input.forEachIndexed { index, char ->
        recentChainChars[index % chainLength] = char
        if (index >= chainLength && recentChainChars.toSet().size == chainLength) return index+1
    }
    error("no answer")
}
