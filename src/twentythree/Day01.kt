package twentythree

fun main() {
    val day = "01"
    // test if implementation meets criteria from the description:
    println("Day$day Test Answers:")
    val testInput = readInputLines("Day${day}_test_part1")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 142)
    val testPart2Input = readInputLines("Day${day}_test_part2")
    val part2 = part2(testPart2Input)
    part2.println()
    check(part2 == 281)

    println("---")
    println("Solutions:")
    val input = readInputLines("Day${day}_input")
    part1(input).println()
    part2(input).println()
}


private fun part1(input: List<String>): Int {
    val output = input.map { line ->
        val firstNumber = line.first { it.isDigit() }
        val lastNumber = line.last { it.isDigit() }
        "$firstNumber$lastNumber".toInt()
    }
    output.sum()
    var answer = 0
    output.forEach { answer += it }
    return output.sum()
}


private fun part2(input: List<String>): Int {
    val output = input.map {
        val firstNumber = it.startNumber()
        val lastNumber = it.endNumber()
        val out = "$firstNumber$lastNumber".toInt()
        out
    }
    var answer = 0
    output.forEach { answer += it }
    return output.sum()
}

fun String.startNumber(): String {
    if (this.first().isDigit()) return this.first().toString()
    return when {
        this.startsWith("one") -> "1"
        this.startsWith("two") -> "2"
        this.startsWith("three") -> "3"
        this.startsWith("four") -> "4"
        this.startsWith("five") -> "5"
        this.startsWith("six") -> "6"
        this.startsWith("seven") -> "7"
        this.startsWith("eight") -> "8"
        this.startsWith("nine") -> "9"
        else -> this.drop(1).startNumber()
    }
}

fun String.endNumber(): String {
    if (this.last().isDigit()) return this.last().toString()
    return when {
        this.endsWith("one") -> "1"
        this.endsWith("two") -> "2"
        this.endsWith("three") -> "3"
        this.endsWith("four") -> "4"
        this.endsWith("five") -> "5"
        this.endsWith("six") -> "6"
        this.endsWith("seven") -> "7"
        this.endsWith("eight") -> "8"
        this.endsWith("nine") -> "9"
        else -> this.dropLast(1).endNumber()
    }
}
