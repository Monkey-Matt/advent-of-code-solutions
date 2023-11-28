package twentytwo

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

private data class Map(
    val terrain: List<List<Int>>,
    val start: Pair<Int, Int>,
    val end: Pair<Int, Int>,
)

private fun List<String>.toTerrainMap(): Map {
    var start: Pair<Int, Int> = Pair(0,0)
    var end: Pair<Int, Int> = Pair(0,0)
    val terrain: List<List<Int>> = this.mapIndexed { index, inputLine ->
        if (inputLine.contains('S')) {
            start = Pair(inputLine.indexOf('S'), index)
        }
        if (inputLine.contains('E')) {
            end = Pair(inputLine.indexOf('E'), index)
        }
        inputLine.map { it.toHeight() }
    }
    return Map(terrain, start, end)
}

private fun Char.toHeight(): Int {
    val heights = "abcdefghijklmnopqrstuvwxyz"
    if (this == 'S') return 0
    if (this == 'E') return 25
    return heights.indexOf(this)
}


private fun part1(input: List<String>): Int {
    val tm = input.toTerrainMap()
    println(tm)
    return input.size
}


private fun part2(input: List<String>): Int {
    return input.size
}
