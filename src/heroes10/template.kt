package heroes10

private val testInput = """
6
XXXS XS
XXXL XL
XL M
XXL XXL
XXXXXS M
L M
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

    }
}