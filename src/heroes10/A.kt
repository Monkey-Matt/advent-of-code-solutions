package heroes10

private val testInput = """
5
7
8
42
2
11
""".trim()

private var line = 0
private fun customReadLine(): String {
    val string = testInput.split("\n")[line]
    line++
    return string
}


private fun readStr() = customReadLine() // string line


// Submit the below
//fun readStr() = readln()
private fun readInt() = readStr().toInt() // single int
private fun readStrings() = readStr().split(" ") // list of strings
private fun readInts() = readStrings().map { it.toInt() } // list of ints




fun main() {
    val n = readInt() // read integer from the input
    for (i in 1..n) {
        val input = readInt()
        val options = mutableListOf(
            (input % 5) % 3,
            (input % 3),
        )
        for (j in 5..input step 5) {
            options.add((input - j) % 3)
        }

//        if (input > 5) options.add((input - 5) % 3)
//        if (input > 10) options.add((input - 10) % 3)
//        if (input > 20) options.add((input - 20) % 3)

        println(options.min())
    }
}