package twentythree

fun main() {
    val day = "03"
    // test if implementation meets criteria from the description:
    println("Day$day Test Answers:")
    val testInput = readInputLines("Day${day}_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 4361)
    val part2 = part2(testInput)
    part2.println()
    check(part2 == 467835)

    println("---")
    println("Solutions:")
    val input = readInputLines("Day${day}_input")
    part1(input).println()
    part2(input).println()
}


private fun part1(input: List<String>): Int {
    var sum = 0
    input.forEachIndexed { yIndex, line ->
        var endOfCurrentNumber = -1
        line.forEachIndexed name@ { xIndex, char ->
            if (xIndex > endOfCurrentNumber) {
                if (char.isDigit()) {
                    val (counts, value, endX) = checkIfThisOneCounts(input, yIndex, xIndex)
                    endOfCurrentNumber = endX
                    if (counts) sum += value
                }
            }
        }
    }
    return sum
}

private fun checkIfThisOneCounts(input: List<String>, yIndex: Int, xIndex: Int): Triple<Boolean, Int, Int> {
    val digits = mutableListOf<Char>()
    var xPosition = xIndex
    val finalXIndex: Int
    while (true) {
        if (xPosition <= input.first().lastIndex && input[yIndex][xPosition].isDigit()) {
            digits.add(input[yIndex][xPosition])
        } else {
            finalXIndex = xPosition-1
            break
        }
        xPosition++
    }
    val countsTriple = Triple(true, digits.joinToString("").toInt(), finalXIndex)
    // check above
    if (yIndex != 0) {
        for (x in xIndex-1..finalXIndex+1) {
            val xCoerced = x.coerceIn(0, input.first().lastIndex)
            if (input[yIndex-1][xCoerced] != '.') return countsTriple
        }
    }
    // check before
    if (xIndex != 0) {
        if (input[yIndex][xIndex-1] != '.') return countsTriple
    }
    // check after
    if (finalXIndex != input.first().lastIndex) {
        if (input[yIndex][finalXIndex+1] != '.') return countsTriple
    }
    // check below
    if (yIndex != input.lastIndex) {
        for (x in xIndex-1..finalXIndex+1) {
            val xCoerced = x.coerceIn(0, input.first().lastIndex)
            if (input[yIndex+1][xCoerced] != '.') return countsTriple
        }
    }
    return Triple(false, 0, finalXIndex)
}



private fun part2(input: List<String>): Int {
    var sum = 0
    input.forEachIndexed { yIndex, line ->
        line.forEachIndexed name@ { xIndex, char ->
            if (char == '*') {
                // need to check for adjacent numbers
                val (counts, value) = checkIfThisStarCountsPart2(input, yIndex, xIndex)
                if (counts) sum += value
            }
        }
    }
    return sum
}

private fun checkIfThisStarCountsPart2(input: List<String>, yIndex: Int, xIndex: Int): Pair<Boolean, Int> {
    val connectedDirections = getAdjacentNumberPositions(input, yIndex, xIndex)
    return if (connectedDirections.size == 2) {
        val number1 = getNumberInPosition(input, connectedDirections[0].getYX(yIndex, xIndex))
        val number2 = getNumberInPosition(input, connectedDirections[1].getYX(yIndex, xIndex))
        Pair(true, number1*number2)
    } else {
        Pair(false, 0)
    }
}

private fun getAdjacentNumberPositions(input: List<String>, yIndex: Int, xIndex: Int): List<NumberDirection> {
    val maxXIndex = input.first().lastIndex
    val connectedDirections = mutableListOf<NumberDirection>()

    // check top
    if (yIndex > 0) {
        // check top middle
        if (input[yIndex-1][xIndex].isDigit()) {
            connectedDirections.add(NumberDirection.TOP_MIDDLE)
        } else {
            // if not number in top middle there could be ones diagonally
            // check top left
            if (xIndex > 0) {
                if (input[yIndex - 1][xIndex - 1].isDigit()) connectedDirections.add(NumberDirection.TOP_LEFT)
            }
            // check top right
            if (xIndex < maxXIndex) {
                if (input[yIndex-1][xIndex+1].isDigit()) connectedDirections.add(NumberDirection.TOP_RIGHT)
            }
        }
    }
    // check left
    if (xIndex > 0) {
        if (input[yIndex][xIndex-1].isDigit()) connectedDirections.add(NumberDirection.LEFT)
    }
    // check right
    if (xIndex < maxXIndex) {
        if (input[yIndex][xIndex+1].isDigit()) connectedDirections.add(NumberDirection.RIGHT)
    }
    // check bottom
    if (yIndex < input.lastIndex) {
        // check bottom middle
        if (input[yIndex+1][xIndex].isDigit()) {
            connectedDirections.add(NumberDirection.BOTTOM_MIDDLE)
        } else {
            // if not number in top middle there could be ones diagonally
            // check bottom left
            if (xIndex > 0) {
                if (input[yIndex+1][xIndex-1].isDigit()) connectedDirections.add(NumberDirection.BOTTOM_LEFT)
            }
            // check top right
            if (xIndex < maxXIndex) {
                if (input[yIndex+1][xIndex+1].isDigit()) connectedDirections.add(NumberDirection.BOTTOM_RIGHT)
            }
        }
    }
    return connectedDirections
}

private fun getNumberInPosition(input: List<String>, yx: Pair<Int, Int>): Int {
    val (y, x) = yx
    var startX = x
    var endX = x
    while (true) {
        if (startX > 0 && input[y][startX-1].isDigit()) {
            startX--
        } else {
            break
        }
    }
    while (true) {
        if (endX < input.first().lastIndex && input[y][endX+1].isDigit()) {
            endX++
        } else {
            break
        }
    }
    return input[y].substring(startX, endX+1).toInt()
}

enum class NumberDirection {
    TOP_LEFT, TOP_MIDDLE, TOP_RIGHT,
    LEFT, RIGHT,
    BOTTOM_LEFT, BOTTOM_MIDDLE, BOTTOM_RIGHT,
    ;

    fun getYX(sourceY: Int, sourceX: Int): Pair<Int, Int> {
        return when (this) {
            TOP_LEFT -> Pair(sourceY-1, sourceX-1)
            TOP_MIDDLE -> Pair(sourceY-1, sourceX)
            TOP_RIGHT -> Pair(sourceY-1, sourceX+1)
            LEFT -> Pair(sourceY, sourceX-1)
            RIGHT -> Pair(sourceY, sourceX+1)
            BOTTOM_LEFT -> Pair(sourceY+1, sourceX-1)
            BOTTOM_MIDDLE -> Pair(sourceY+1, sourceX)
            BOTTOM_RIGHT -> Pair(sourceY+1, sourceX+1)
        }
    }
}
