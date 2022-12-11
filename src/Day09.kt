fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day09_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 13)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 1)


    val testInputPart2 = readInputLines("Day09_test_part2")
    val part2SecondInput = part2(testInputPart2)
    println(part2SecondInput)
    check(part2SecondInput == 36)

    println("---")
    val input = readInputLines("Day09_input")
    println(part1(input))
    println(part2(input))
}

private data class Position9(val x: Int, val y: Int)

private enum class Direction {
    LEFT, RIGHT, UP, DOWN
}

private fun part1(input: List<String>): Int {
    val visitedPositions = mutableSetOf<Position9>()
    var headPosition = Position9(0, 0)
    var tailPosition = Position9(0, 0)
    val moves = input.toMoves()
    moves.forEach { (direction, distance) ->
        for (i in 0 until distance) {
            val oldHeadPosition = headPosition
            headPosition = when (direction) {
                Direction.LEFT -> Position9(headPosition.x-1, headPosition.y)
                Direction.RIGHT -> Position9(headPosition.x+1, headPosition.y)
                Direction.UP -> Position9(headPosition.x, headPosition.y+1)
                Direction.DOWN -> Position9(headPosition.x, headPosition.y-1)
            }
            when (direction) {
                Direction.LEFT -> {
                    if (tailPosition.x == headPosition.x + 2) {
                        tailPosition = oldHeadPosition
                    }
                }
                Direction.RIGHT -> {
                    if (tailPosition.x == headPosition.x - 2) {
                        tailPosition = oldHeadPosition
                    }
                }
                Direction.UP -> {
                    if (tailPosition.y == headPosition.y - 2) {
                        tailPosition = oldHeadPosition
                    }
                }
                Direction.DOWN -> {
                    if (tailPosition.y == headPosition.y + 2) {
                        tailPosition = oldHeadPosition
                    }
                }
            }
            visitedPositions.add(tailPosition)
        }
    }
    return visitedPositions.size
}

private fun moveTail(
    direction: Direction,
    oldHeadPosition: Position9,
    newHeadPosition: Position9,
    oldTailPosition: Position9
): Position9 {
    when (direction) {
        Direction.LEFT -> {
            if (oldTailPosition.x == newHeadPosition.x + 2) {
                return oldHeadPosition
            }
        }
        Direction.RIGHT -> {
            if (oldTailPosition.x == newHeadPosition.x - 2) {
                return oldHeadPosition
            }
        }
        Direction.UP -> {
            if (oldTailPosition.y == newHeadPosition.y - 2) {
                return oldHeadPosition
            }
        }
        Direction.DOWN -> {
            if (oldTailPosition.y == newHeadPosition.y + 2) {
                return oldHeadPosition
            }
        }
    }
    return oldTailPosition
}

private fun List<String>.toMoves(): List<Pair<Direction, Int>> {
    return this.map {
        val dir = when (it[0]) {
            'R' -> Direction.RIGHT
            'L' -> Direction.LEFT
            'U' -> Direction.UP
            'D' -> Direction.DOWN
            else -> error("unexpected input ${it[0]}")
        }
        val distance = it.split(" ")[1].toInt()
        dir to distance
    }
}


private fun part2(input: List<String>): Int {
    val visitedPositions = mutableSetOf<Position9>()
    var headPosition = Position9(0, 0)
    val tailPositions = MutableList(9) { Position9(0, 0) }
    val moves = input.toMoves()

    moves.forEach { (direction, distance) ->
        for (i in 0 until distance) {
            val oldHeadPosition = headPosition
            headPosition = when (direction) {
                Direction.LEFT -> Position9(headPosition.x-1, headPosition.y)
                Direction.RIGHT -> Position9(headPosition.x+1, headPosition.y)
                Direction.UP -> Position9(headPosition.x, headPosition.y+1)
                Direction.DOWN -> Position9(headPosition.x, headPosition.y-1)
            }
            val oldTailPositions = tailPositions.toList()
            tailPositions[0] = moveTail(direction, oldHeadPosition, headPosition, tailPositions[0])
            tailPositions[1] = moveTailSpecial(oldTailPositions[0], tailPositions[0], tailPositions[1])
            tailPositions[2] = moveTailSpecial(oldTailPositions[1], tailPositions[1], tailPositions[2])
            tailPositions[3] = moveTailSpecial(oldTailPositions[2], tailPositions[2], tailPositions[3])
            tailPositions[4] = moveTailSpecial(oldTailPositions[3], tailPositions[3], tailPositions[4])
            tailPositions[5] = moveTailSpecial(oldTailPositions[4], tailPositions[4], tailPositions[5])
            tailPositions[6] = moveTailSpecial(oldTailPositions[5], tailPositions[5], tailPositions[6])
            tailPositions[7] = moveTailSpecial(oldTailPositions[6], tailPositions[6], tailPositions[7])
            tailPositions[8] = moveTailSpecial(oldTailPositions[7], tailPositions[7], tailPositions[8])
            visitedPositions.add(tailPositions[8])
        }
    }



    return visitedPositions.size
}

private enum class DirectionSpecial {
    NONE, LEFT, RIGHT, UP, DOWN, UP_RIGHT, UP_LEFT, DOWN_RIGHT, DOWN_LEFT
}

private fun moveTailSpecial(
    oldHeadPosition: Position9,
    newHeadPosition: Position9,
    oldTailPosition: Position9
): Position9 {
    if (oldTailPosition.x <= newHeadPosition.x + 1 && oldTailPosition.x >= newHeadPosition.x - 1 &&
            oldTailPosition.y <= newHeadPosition.y + 1 && oldTailPosition.y >= newHeadPosition.y - 1) {
        return oldTailPosition
    }
    when (oldHeadPosition.directionTo(newHeadPosition)) {
        DirectionSpecial.LEFT -> {
            if (oldTailPosition.x == newHeadPosition.x + 2) {
                return oldHeadPosition
            }
        }
        DirectionSpecial.RIGHT -> {
            if (oldTailPosition.x == newHeadPosition.x - 2) {
                return oldHeadPosition
            }
        }
        DirectionSpecial.UP -> {
            if (oldTailPosition.y == newHeadPosition.y - 2) {
                return oldHeadPosition
            }
        }
        DirectionSpecial.DOWN -> {
            if (oldTailPosition.y == newHeadPosition.y + 2) {
                return oldHeadPosition
            }
        }
        DirectionSpecial.NONE -> return oldTailPosition
        DirectionSpecial.UP_RIGHT -> {
            if (oldTailPosition.y <= oldHeadPosition.y && oldTailPosition.x <= oldHeadPosition.x) {
                return Position9(oldTailPosition.x+1, oldTailPosition.y+1)
            }
            if (oldTailPosition.y == newHeadPosition.y - 2) {
                return Position9(oldTailPosition.x, oldTailPosition.y + 1)
            }
            if (oldTailPosition.x ==  newHeadPosition.x - 2) {
                return Position9(oldTailPosition.x + 1, oldTailPosition.y)
            }
        }
        DirectionSpecial.UP_LEFT -> {
            if (oldTailPosition.y <= oldHeadPosition.y && oldTailPosition.x >= oldHeadPosition.x) {
                return Position9(oldTailPosition.x-1, oldTailPosition.y+1)
            }
            if (oldTailPosition.y == newHeadPosition.y - 2) {
                return Position9(oldTailPosition.x, oldTailPosition.y + 1)
            }
            if (oldTailPosition.x ==  newHeadPosition.x + 2) {
                return Position9(oldTailPosition.x - 1, oldTailPosition.y)
            }
        }
        DirectionSpecial.DOWN_RIGHT -> {
            if (oldTailPosition.y >= oldHeadPosition.y && oldTailPosition.x <= oldHeadPosition.x) {
                return Position9(oldTailPosition.x+1, oldTailPosition.y-1)
            }
            if (oldTailPosition.y == newHeadPosition.y + 2) {
                return Position9(oldTailPosition.x, oldTailPosition.y - 1)
            }
            if (oldTailPosition.x ==  newHeadPosition.x - 2) {
                return Position9(oldTailPosition.x + 1, oldTailPosition.y)
            }
        }
        DirectionSpecial.DOWN_LEFT -> {
            if (oldTailPosition.y >= oldHeadPosition.y && oldTailPosition.x >= oldHeadPosition.x) {
                return Position9(oldTailPosition.x-1, oldTailPosition.y-1)
            }
            if (oldTailPosition.y == newHeadPosition.y + 2) {
                return Position9(oldTailPosition.x, oldTailPosition.y - 1)
            }
            if (oldTailPosition.x ==  newHeadPosition.x + 2) {
                return Position9(oldTailPosition.x - 1, oldTailPosition.y)
            }
        }
    }
    return oldTailPosition
}

private fun Position9.directionTo(other: Position9): DirectionSpecial {
    if (this == other) return DirectionSpecial.NONE
    if (this.x == other.x && this.y == other.y - 1) return DirectionSpecial.UP
    if (this.x == other.x && this.y == other.y + 1) return DirectionSpecial.DOWN
    if (this.x <= other.x - 1 && this.y <= other.y - 1) return DirectionSpecial.UP_RIGHT
    if (this.x >= other.x + 1 && this.y <= other.y - 1) return DirectionSpecial.UP_LEFT
    if (this.x <= other.x - 1 && this.y >= other.y + 1) return DirectionSpecial.DOWN_RIGHT
    if (this.x >= other.x + 1 && this.y >= other.y + 1) return DirectionSpecial.DOWN_LEFT
    if (this.x == other.x - 1 && this.y == other.y) return DirectionSpecial.RIGHT
    if (this.x == other.x + 1 && this.y == other.y) return DirectionSpecial.LEFT
    error("direction missing from calc. $this - $other")
}
