package heroes10

private val testInput = """
5
1 1
5 14
5 15
10 110
99999999 1000000000
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
        val (period, currentTime) = readInts()
        var timeStartShowing = 2 * period
        var timeEndShowing = timeStartShowing + (period - 1)
        while (timeEndShowing < currentTime) {
            timeEndShowing += 3 * period
        }
        timeStartShowing = timeEndShowing - (period - 1)
        if (timeStartShowing <= currentTime) {
            println(0)
        } else {
            println(timeStartShowing - currentTime)
        }
    }
}