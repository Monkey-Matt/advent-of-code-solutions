fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day10_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 13140)
    val part2 = part2(testInput)
    println(part2)
    check(part2 ==
    """
       ##..##..##..##..##..##..##..##..##..##..
       ###...###...###...###...###...###...###.
       ####....####....####....####....####....
       #####.....#####.....#####.....#####.....
       ######......######......######......####
       #######.......#######.......#######.....
    """.trimIndent())

    println("---")
    val input = readInputLines("Day10_input")
    println(part1(input))
    println(part2(input))
}

private sealed interface Operation {
    object NOOP: Operation
    data class ADDX(val x: Int): Operation
}

private fun List<String>.toOperations(): List<Operation> {
    return this.map { it.toOperation() }
}

private fun String.toOperation(): Operation {
    return when (this) {
        "noop" -> Operation.NOOP
        else -> Operation.ADDX(this.substringAfter("addx ").toInt())
    }
}


private fun part1(input: List<String>): Int {
    val cyclesToCount = listOf(20, 60, 100, 140, 180, 220)
    var cycle = 0
    var x = 1
    val countedX = mutableListOf<Int>()
    input.toOperations().forEach { operation ->
        cycle++
        if (cycle in cyclesToCount) countedX.add(x)
        if (operation is Operation.ADDX) {
            cycle++
            if (cycle in cyclesToCount) countedX.add(x)
            x += operation.x
        }
    }
    val scores = countedX.mapIndexed { index, xAtSpot -> xAtSpot * cyclesToCount[index] }
    return scores.sum()
}


private fun part2(input: List<String>): String {
    var cycle = -1 // because crt starts at 0
    var x = 1
    val output = mutableListOf<Boolean>()
    input.toOperations().forEach { operation ->
        cycle++
        output.add(crtState(cycle, x))
        if (operation is Operation.ADDX) {
            cycle++
            output.add(crtState(cycle, x))
            x += operation.x
        }
    }
    return output
        .map { if (it) "#" else "." }
        .reduce { a, b -> "$a$b" }
        .chunked(40)
        .reduce { a, b -> "$a\n$b"}
}

private fun crtState(cycle: Int, x: Int): Boolean {
    val horizontalCrtPosition = cycle % 40
    return horizontalCrtPosition == x ||
            horizontalCrtPosition == x - 1 ||
            horizontalCrtPosition == x + 1
}
