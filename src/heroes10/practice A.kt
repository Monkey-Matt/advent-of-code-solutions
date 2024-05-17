package heroes10

private val testData = """
4
4
11 13 11 11
5
1 4 4 4 4
10
3 3 3 3 10 3 3 3 3 3
3
20 20 10
""".trim()

private var line = 0
private fun customReadLine(): String {
    val string = testData.split("\n")[line]
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
        val inputNumbers = readInputLine()
        val distinct = inputNumbers.distinct()
        for (j in distinct) {
            if (inputNumbers.count { it == j } == 1) {
                val position = inputNumbers.indexOf(j)
                println(position + 1)
            }
        }
    }
}

fun readInputLine(): List<Int> {
    val ignore = readStr()
    val numbers = readInts()
    return numbers
}
