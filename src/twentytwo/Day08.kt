package twentytwo

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day08_test")
    println(part1(testInput))
    check(part1(testInput) == 21)
    println(part2(testInput))
    check(part2(testInput) == 8)

    println("---")
    val input = readInputLines("Day08_input")
    println(part1(input))
    println(part2(input))
}

private data class Position(val x: Int, val y: Int)

private val List<String>.lastYIndex
        get() = this.lastIndex
private val List<String>.lastXIndex
        get() = this[0].lastIndex

private fun part1(forest: List<String>): Int {
    val width = forest[0].length
    val height = forest.size
    var visibleTreesCount = 0
    for (y in 0 until height) {
        for (x in 0 until width) {
            val treePosition = Position(x, y)
            if (forest.treeIsVisible(treePosition)) visibleTreesCount++
        }
    }
    return visibleTreesCount
}

private fun List<String>.treeIsVisible(position: Position): Boolean {
    val forest = this
    // edge is always visible
    if (position.y == 0 || position.x == 0) return true
    if (position.y == forest.lastYIndex || position.x == forest.lastXIndex) return true

    val currentTree = forest[position.y][position.x]
    var blocked = false
    for (i in position.y-1 downTo  0) { // up
        if (forest[i][position.x] >= currentTree) {
            blocked = true
            break
        }
    }
    if (!blocked) return true
    blocked = false

    for (i in position.y+1 .. forest.lastYIndex) { // down
        if (forest[i][position.x] >= currentTree) {
            blocked = true
            break
        }
    }
    if (!blocked) return true
    blocked = false

    for (j in position.x-1 downTo  0) { // left
        if (forest[position.y][j] >= currentTree) {
            blocked = true
            break
        }
    }
    if (!blocked) return true
    blocked = false

    for (j in position.x+1 .. forest.lastXIndex) { // right
        if (forest[position.y][j] >= currentTree) {
            blocked = true
            break
        }
    }
    if (!blocked) return true
    return false
}


private fun part2(forest: List<String>): Int {
    val width = forest[0].length
    val height = forest.size
    val treeScores = mutableListOf<Int>()
    for (y in 0 until height) {
        for (x in 0 until width) {
            val treePosition = Position(x, y)
            treeScores.add(forest.treeScore(treePosition))
        }
    }
    return treeScores.max()
}

private fun List<String>.treeScore(position: Position): Int {
    val forest = this
    val currentTree = forest[position.y][position.x]

    var score1 = 0 // up
    for (i in position.y-1 downTo  0) {
        score1++
        if (forest[i][position.x] >= currentTree) {
            break
        }
    }
    var score2 = 0 // down
    for (i in position.y+1 .. forest.lastIndex) {
        score2++
        if (forest[i][position.x] >= currentTree) {
            break
        }
    }

    var score3 = 0 // left
    for (j in position.x-1 downTo  0) {
        score3++
        if (forest[position.y][j] >= currentTree) {
            break
        }
    }

    var score4 = 0 // right
    for (j in position.x+1 .. forest[0].lastIndex) {
        score4++
        if (forest[position.y][j] >= currentTree) {
            break
        }
    }

    return score1 * score2 * score3 * score4
}
