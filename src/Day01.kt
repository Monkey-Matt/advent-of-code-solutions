fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
//    println(part1(testInput))
    check(part1(testInput) == 24_000)
//    println(part2(testInput))
    check(part2(testInput) == 45_000)

    val input = readInput("Day01_input")
    println(part1(input))
    println(part2(input))
}


fun part1(input: List<String>): Int {
    val groupTotals = getElfTotals(input)
    return groupTotals.max()
}

fun getElfTotals(input: List<String>): MutableList<Int> {
    val groups = input.split("")
    return groups.map { group -> group.sumOf { it.toInt() } }.toMutableList()
}

fun <T> List<T>.split(splitOn: T): List<List<T>> {
    val groups = mutableListOf<MutableList<T>>()
    var sublistCount = 0
    this.forEach { item ->
        if (item == splitOn) {
            sublistCount++
        } else {
            if (groups.size <= sublistCount) {
                groups.add(mutableListOf())
            }
            val sublist = groups[sublistCount]
            sublist.add(item)
        }
    }
    return groups
}


fun part2(input: List<String>): Int {
    val elfTotals = getElfTotals(input)
    val one = elfTotals.max()
    elfTotals.remove(one)
    val two = elfTotals.max()
    elfTotals.remove(two)
    val three = elfTotals.max()
    return one + two + three
}
