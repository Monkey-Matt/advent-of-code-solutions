package twentytwo

import java.util.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day05_test")
    println(part1(testInput))
    check(part1(testInput) == "CMZ")
    println(part2(testInput))
    check(part2(testInput) == "MCD")

    println("---")
    val input = readInputLines("Day05_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: List<String>): String {
    val (stackInputLines, moveInputLines) = input.split("")
    val stacks = inputToStacks(stackInputLines)
    val moves = moveInputLines.map { StackMove.create(it) }
    moves.forEach { move ->
        executeMoveWith9000(stacks, move)
    }
    val topItems = stacks.map { it.pop() }
    return topItems.joinToString(separator = "")
}

private fun inputToStacks(input: List<String>): List<Stack<Char>> {
    val chunked = input.map { stackInputLine -> // [N] [C] [P]
        stackInputLine.chunked(4).map {
            it[1] // [N]
        }
    }
    val stacks = List(chunked.last().size) { Stack<Char>() }
    chunked.dropLast(1).reversed().forEach { line ->
        line.forEachIndexed { index, entry ->
            if (entry != ' ') {
                stacks[index].push(entry)
            }
        }
    }
    return stacks
}

private fun executeMoveWith9000(stacks: List<Stack<Char>>, move: StackMove) {
    for (i in 0 until move.quantity) {
        val char = stacks[move.fromStack-1].pop()
        stacks[move.toStack-1].push(char)
    }
}

private fun executeMoveWith9001(stacks: List<Stack<Char>>, move: StackMove) {
    val tempStack = Stack<Char>()
    for (i in 0 until move.quantity) {
        val char = stacks[move.fromStack-1].pop()
        tempStack.push(char)
    }
    while (tempStack.isNotEmpty()) {
        stacks[move.toStack-1].push(tempStack.pop())
    }
}

private data class StackMove(val quantity: Int, val fromStack: Int, val toStack: Int) {
    companion object {
        fun create(input: String): StackMove {
            val inputParts = input.split(" ")
            return StackMove(
                quantity = inputParts[1].toInt(),
                fromStack = inputParts[3].toInt(),
                toStack = inputParts[5].toInt(),
            )
        }
    }
}

private fun part2(input: List<String>): String {
    val (stackInputLines, moveInputLines) = input.split("")
    val stacks = inputToStacks(stackInputLines)
    val moves = moveInputLines.map { StackMove.create(it) }
    moves.forEach { move ->
        executeMoveWith9001(stacks, move)
    }
    val topItems = stacks.map { it.pop() }
    return topItems.joinToString(separator = "")
}
