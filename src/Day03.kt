import kotlin.streams.toList

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 157)
    println(part2(testInput))
    check(part2(testInput) == 70)

    println("---")
    val input = readInput("Day03_input")
    println(part1(input))
    println(part2(input))
}

private const val points = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

private fun part1(input: List<String>): Int {
    val letters = input.map {
        val firstHalf = it.substring(0, it.length/2)
        val secondHalf = it.substring(it.length/2, it.length)
        val commonLetter = firstHalf.chars().toList().intersect(secondHalf.chars().toList())
        commonLetter.first().toChar()
    }
    val scores = letters.map { letter ->
        points.indexOf(letter) + 1
    }
    return scores.sum()
}


private fun part2(input: List<String>): Int {
    val groups = input.chunked(3)
    val badges = groups.map { (one, two, three) ->
        val oneList = one.chars().toList()
        val twoList = two.chars().toList()
        val threeList = three.chars().toList()
        oneList.intersect(twoList).intersect(threeList).first().toChar()
    }
    val scores = badges.map { letter ->
        points.indexOf(letter) + 1
    }
    return scores.sum()
}
