package twentythree

fun main() {
    val day = "02"
    // test if implementation meets criteria from the description:
    println("Day$day Test Answers:")
    val testInput = readInputLines("Day${day}_test")
    val part1 = part1(testInput)
    part1.println()
    check(part1 == 8)
    val part2 = part2(testInput)
    part2.println()
    check(part2 == 2286)

    println("---")
    println("Solutions:")
    val input = readInputLines("Day${day}_input")
    part1(input).println()
    part2(input).println()
}


private fun part1(input: List<String>): Int {
    var sum = 0
    input.forEach name@ { inputLine ->
        val (titleText, gamesText) = inputLine.split(":")
        val gameNumber = titleText.removePrefix("Game ").toInt()
        val games = gamesText.split(";")
        games.forEach { game ->
            val colorDraws = game.split(", ")
            colorDraws.forEach { color ->
                if (isOutOfBounds(color)) {
                    // don't count this color
                    return@name
                }
            }
        }
//        inputLine.println()
//        gameNumber.println()
        sum += gameNumber
    }
    return sum
}

fun isOutOfBounds(color: String): Boolean {
    val maxRed = 12
    val maxGreen = 13
    val maxBlue = 14
    when {
        color.contains("blue") -> {
            val count = color.removeSuffix("blue").trim()
            if (count.toInt() > maxBlue) return true
        }
        color.contains("green") -> {
            val count = color.removeSuffix("green").trim()
            if (count.toInt() > maxGreen) return true
        }
        color.contains("red") -> {
            val count = color.removeSuffix("red").trim()
            if (count.toInt() > maxRed) return true
        }
    }
    return false
}


private fun part2(input: List<String>): Int {
    var sum = 0
    input.forEach name@ { inputLine ->
        val (_, gamesText) = inputLine.split(":")
        val hands = gamesText.split(";")
        var gameMaxRed = 0
        var gameMaxGreen = 0
        var gameMaxBlue = 0
        hands.forEach { hand ->
            val colorDraws = hand.split(", ")
            colorDraws.forEach { colorString ->
                val color = Color.fromString(colorString)
                when (color) {
                    is RED -> if (color.value > gameMaxRed) gameMaxRed = color.value
                    is GREEN -> if (color.value > gameMaxGreen) gameMaxGreen = color.value
                    is BLUE -> if (color.value > gameMaxBlue) gameMaxBlue = color.value
                }
            }
        }
        val power = gameMaxRed * gameMaxBlue * gameMaxGreen
        sum += power
    }
    return sum
}

sealed class Color(value: Int) {
    companion object {
        fun fromString(color: String): Color {
            when {
                color.contains("blue") -> {
                    val count = color.removeSuffix("blue").trim()
                    return BLUE(count.toInt())
                }

                color.contains("green") -> {
                    val count = color.removeSuffix("green").trim()
                    return GREEN(count.toInt())
                }

                color.contains("red") -> {
                    val count = color.removeSuffix("red").trim()
                    return RED(count.toInt())
                }
                else -> throw(Exception("Whats the color"))
            }
        }
    }
}
data class RED(val value: Int): Color(value)
data class GREEN(val value: Int): Color(value)
data class BLUE(val value: Int): Color(value)
