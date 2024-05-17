package heroes10

import java.math.BigInteger

private val testInput = """
4
2 2
2 1
10 3
50 36679020707840
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



val bigTwo = BigInteger.valueOf(2)

fun main() {
    val t = readInt() // read integer from the input
    for (i in 1..t) {
        val (nString, target) = readStrings()
        val n = nString.toInt()
        val bigTarget = target.toBigInteger()
        val logSize = bigTwo.pow(n)
        val cuts = cuts(logSize, bigTarget)
        println(cuts)
    }
}

fun cuts(inputLogSize: BigInteger, target: BigInteger): Int {
    var cuts = 0
    var logSize: BigInteger = inputLogSize
    for (j in 1..60) {
        logSize /= bigTwo
        if (logSize == target) {
            cuts += j
            break
        } else if (logSize <= target) {
            cuts += j

            val remainder = target - logSize
            cuts += cuts(logSize, remainder)
            break
        }
    }
    return cuts
}