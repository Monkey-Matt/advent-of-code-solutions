package heroes10

private val testInput = """
5
11 2
aAaaBACacbE
2 2
ab
4 1
aaBB
6 0
abBAcC
5 3
cbccb
11 0
aAaaBACacbE
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



// save and print answers at the end to be more efficient?
fun main() {
    val n = readInt() // read integer from the input
    val counter = PairCounter()
    for (i in 1..n) {
        val (_, maxOperations) = readInts()
        val score = counter.countPairs(readStr(), maxOperations)
        println(score)
    }
}


class PairCounter {
    private var count: Int = 0
    private var operationsRemaining: Int = 0

    fun countPairs(input: String, maxOperations: Int): Int {
        count = 0
        operationsRemaining = maxOperations
        checkFirstLetterPairRecursively(input)
        return count
    }

    // use chars/ints instead of strings to be more efficient?
    private fun checkFirstLetterPairRecursively(input: String) {
        if (input.length <= 1) return
        val firstChar = input.first()
        val pairChar = pairsMap[firstChar]
        val pairIndex = input.indexOfFirst { it == pairChar }
        if (pairIndex == -1) {
            if (operationsRemaining > 0) {
                val operationIndex = input.replaceFirst(firstChar.toString(), "").indexOfFirst { it == firstChar }
                if (operationIndex != -1) {
                    count++
                    operationsRemaining--
                    val inputWithoutPair = input.replaceFirst(firstChar.toString(), "").replaceFirst(firstChar.toString(), "")
                    checkFirstLetterPairRecursively(inputWithoutPair)
                } else {
                    val inputWithoutFirst = input.replaceFirst(firstChar.toString(), "")
                    checkFirstLetterPairRecursively(inputWithoutFirst)
                }
            } else {
                val inputWithoutFirst = input.replaceFirst(firstChar.toString(), "")
                checkFirstLetterPairRecursively(inputWithoutFirst)
            }
        } else {
            count++
            val inputWithoutPair = input.replaceFirst(firstChar.toString(), "").replaceFirst(pairChar.toString(), "")
            checkFirstLetterPairRecursively(inputWithoutPair)
        }
    }
}

private val pairsMap = mapOf(
    'a' to 'A',
    'b' to 'B',
    'c' to 'C',
    'd' to 'D',
    'e' to 'E',
    'f' to 'F',
    'g' to 'G',
    'h' to 'H',
    'i' to 'I',
    'j' to 'J',
    'k' to 'K',
    'l' to 'L',
    'm' to 'M',
    'n' to 'N',
    'o' to 'O',
    'p' to 'P',
    'q' to 'Q',
    'r' to 'R',
    's' to 'S',
    't' to 'T',
    'u' to 'U',
    'v' to 'V',
    'w' to 'W',
    'x' to 'X',
    'y' to 'Y',
    'z' to 'Z',
    'A' to 'a',
    'B' to 'b',
    'C' to 'c',
    'D' to 'd',
    'E' to 'e',
    'F' to 'f',
    'G' to 'g',
    'H' to 'h',
    'I' to 'i',
    'J' to 'j',
    'K' to 'k',
    'L' to 'l',
    'M' to 'm',
    'N' to 'n',
    'O' to 'o',
    'P' to 'p',
    'Q' to 'q',
    'R' to 'r',
    'S' to 's',
    'T' to 't',
    'U' to 'u',
    'V' to 'v',
    'W' to 'w',
    'X' to 'x',
    'Y' to 'y',
    'Z' to 'z',
)
