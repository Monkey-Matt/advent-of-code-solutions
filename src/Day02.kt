fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day02_test")
    println(part1(testInput))
    check(part1(testInput) == 15)
    println(part2(testInput))
    check(part2(testInput) == 12)

    println("---")
    val input = readInputLines("Day02_input")
    println(part1(input))
    println(part2(input))
}

enum class Move(val moveScore: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    fun playAgainst(opponent: Move): Result {
        return when {
            this == opponent -> Result.DRAW
            this.losesTo() == opponent -> Result.LOSE
            this.winsAgainst() == opponent -> Result.WIN
            else -> throw Exception("$this + $opponent case not covered")
        }
    }

    fun winsAgainst(): Move {
        return when (this) {
            PAPER -> ROCK
            ROCK -> SCISSORS
            SCISSORS -> PAPER
        }
    }

    fun losesTo(): Move {
        return when (this) {
            PAPER -> SCISSORS
            ROCK -> PAPER
            SCISSORS -> ROCK
        }
    }

    companion object {
        fun fromInput(char: Char): Move {
            return when (char) {
                'A' -> ROCK
                'B' -> PAPER
                'C' -> SCISSORS
                'X' -> ROCK
                'Y' -> PAPER
                'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }
    }
}

enum class Result(val score: Int) {
    WIN(6), DRAW(3), LOSE(0);

    companion object {
        fun fromInput(char: Char): Result {
            return when (char) {
                'X' -> LOSE
                'Y' -> DRAW
                'Z' -> WIN
                else -> throw IllegalArgumentException()
            }
        }
    }
}


private fun part1(input: List<String>): Int {
    val games = input.map {
        val (opponentMove, myMove) = it.split(" ")
        Pair(Move.fromInput(opponentMove.first()), Move.fromInput(myMove.first()))
    }
    val scores = games.map { (opponentMove, myMove) ->
        val moveScore = myMove.moveScore
        val gameScore = myMove.playAgainst(opponentMove).score
        moveScore + gameScore
    }
    return scores.sum()
}


private fun part2(input: List<String>): Int {
    val games = input.map {
        val (opponentMove, desiredResult) = it.split(" ")
        Pair(Move.fromInput(opponentMove.first()), Result.fromInput(desiredResult.first()))
    }
    val scores = games.map { (opponentMove, desiredResult) ->
        val gameScore = desiredResult.score
        val myMove = when (desiredResult) {
            Result.DRAW -> opponentMove
            Result.WIN -> opponentMove.losesTo()
            Result.LOSE -> opponentMove.winsAgainst()
        }
        val moveScore = myMove.moveScore
        gameScore + moveScore
    }
    return scores.sum()
}
