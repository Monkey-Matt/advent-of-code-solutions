
fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day03_test")
    println(part1(testInput))
    check(part1(testInput) == 157)
    println(part2(testInput))
    check(part2(testInput) == 70)

    println("---")
    val input = readInputLines("Day03_input")
    println(part1(input))
    println(part2(input))

    testAlternativeSolutions()
}

private const val points = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

private fun part1(input: List<String>): Int {
    val letters = input.map {
        val firstHalf = it.substring(0, it.length/2)
        val secondHalf = it.substring(it.length/2, it.length)
        val commonLetter = firstHalf.toSet().intersect(secondHalf.toSet())
        commonLetter.first()
    }
    val scores = letters.map { letter ->
        points.indexOf(letter) + 1
    }
    return scores.sum()
}


private fun part2(input: List<String>): Int {
    val groups = input.chunked(3)
    val badges = groups.map { (one, two, three) ->
        val oneSet = one.toSet()
        val twoSet = two.toSet()
        val threeSet = three.toSet()
        oneSet.intersect(twoSet).intersect(threeSet).first()
    }
    val scores = badges.map { letter ->
        points.indexOf(letter) + 1
    }
    return scores.sum()
}

// ------------------------------------------------------------------------------------------------

private fun testAlternativeSolutions() {
    val testInput = readInputLines("Day03_test")
    check(part1AlternativeSolution(testInput) == 157)
    check(part2AlternativeSolution(testInput) == 70)

    println("Alternative Solutions:")
    val input = readInputLines("Day03_input")
    println(part1(input))
    println(part2(input))
}

private fun part1AlternativeSolution(input: List<String>): Int {
    return input
        .map {
            it.substring(0, it.length/2) to it.substring(it.length/2, it.length)
        }
        .map { (firstHalf, secondHalf) ->
            (firstHalf intersect secondHalf).single()
        }
        .sumOf { it.score }

    // or ever shorter
//    input.sumOf {
//        val firstHalf = it.substring(0, it.length/2)
//        val secondHalf = it.substring(it.length/2, it.length)
//        (firstHalf intersect secondHalf).single().score
//    }
}


private fun part2AlternativeSolution(input: List<String>): Int {
    return input
        .chunked(3)
        .map { (one, two, three) ->
            (one intersect two intersect three).single()
        }
        .sumOf { it.score }

    // shortened but less readable
//    input.chunked(3).sumOf { (one, two, three) ->
//        (one intersect two intersect three).single().score
//    }
}

private val Char.score
    get() = points.indexOf(this) + 1

infix fun String.intersect(other: String): Set<Char> = this.toSet() intersect other.toSet()
infix fun Set<Char>.intersect(other: String): Set<Char> = this intersect other.toSet()