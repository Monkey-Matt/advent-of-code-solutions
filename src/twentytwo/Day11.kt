package twentytwo

import java.util.*

fun main() {
    println("Int max = ${Int.MAX_VALUE}")
    println("Long max = ${Long.MAX_VALUE}")
    println("Short max = ${Short.MAX_VALUE}")
    println("Byte max = ${Byte.MAX_VALUE}")
    println()


    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day11_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 10605)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 2713310158L)

    println("---")
    val input = readInputLines("Day11_input")
    println(part1(input))
    println(part2(input))
}

private data class Monkey(
    val items: Queue<Long>,
    val operation: (Long) -> Long,
    val test: (Long) -> Boolean,
    val testDivider: Long,
    val trueThrowIndex: Int,
    val falseThrowIndex: Int,
    var inspections: Int = 0,
)

private fun List<String>.toMonkeys(): List<Monkey> {
    return this.split("").map { monkeyInput ->
        val items = monkeyInput[1]
            .substringAfter("  Starting items: ")
            .split(", ")
            .map { it.toLong() }
        val (operator, thing) = monkeyInput[2]
            .substringAfter("  Operation: new = old ")
            .split(" ")
        val operation: (Long) -> Long = { old: Long ->
            if (thing == "old") {
                when (operator) {
                    "+" -> old + old
                    "-" -> 0L
                    "*" -> old * old
                    "/" -> old / old
                    else -> error("unexpected operator $operator")
                }
            } else {
                val thing2 = thing.toLong()
                when (operator) {
                    "+" -> old + thing2
                    "-" -> old - thing2
                    "*" -> old * thing2
                    "/" -> old / thing2
                    else -> error("unexpected operator $operator")
                }
            }
        }
        val testDivider = monkeyInput[3].substringAfter("  Test: divisible by ").toLong()
        val test: (Long) -> Boolean = { value ->
            value % testDivider == 0L
        }

        Monkey(
            items = items.toQueue(),
            operation = operation,
            test = test,
            testDivider = testDivider,
            trueThrowIndex = monkeyInput[4].substringAfter("    If true: throw to monkey ").toInt(),
            falseThrowIndex = monkeyInput[5].substringAfter("    If false: throw to monkey ").toInt()
        )
    }
}

private fun <T> List<T>.toQueue(): Queue<T> {
    return LinkedList(this)
}

private fun Monkey.inspectNext(): Long {
    val item = this.items.poll()
    return this.operation(item)
}

private fun part1(input: List<String>): Int {
    val monkeys = input.toMonkeys()
    for (roundNum in 1 .. 20) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                val itemValue = monkey.inspectNext() / 3L
                if (monkey.test(itemValue)) {
                    monkeys[monkey.trueThrowIndex].items.add(itemValue)
                } else {
                    monkeys[monkey.falseThrowIndex].items.add(itemValue)
                }
                monkey.inspections++
            }
        }
    }
    val (a, b) = monkeys.sortedByDescending { it.inspections }.take(2)
    return a.inspections * b.inspections
}


private fun part2(input: List<String>): Long {
    val monkeys = input.toMonkeys()
    // this common divider step I couldn't figure out myself, I had to look up others answers.
    // It's required so that the items values don't get too big to calculate.
    val commonDivider = monkeys.fold(1L) { a, monkey -> a * monkey.testDivider }
    for (roundNum in 1 .. 10_000) {
        monkeys.forEach { monkey ->
            while (monkey.items.isNotEmpty()) {
                val itemValue = (monkey.inspectNext()) % commonDivider
                if (monkey.test(itemValue)) {
                    monkeys[monkey.trueThrowIndex].items.add(itemValue)
                } else {
                    monkeys[monkey.falseThrowIndex].items.add(itemValue)
                }
                monkey.inspections++
            }
        }
    }
    val (a, b) = monkeys.sortedByDescending { it.inspections }.take(2)
    return a.inspections.toLong() * b.inspections.toLong()
}
