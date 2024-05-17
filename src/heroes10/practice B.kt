package heroes10

private val testData = """
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
        val (left, right) = readStrings().map { Size.fromString(it) }
        println(left.compare(right))
    }
}

sealed interface Size {
    data class SMALL(val xtras: Int) : Size
    data object MEDIUM : Size
    data class LARGE(val xtras: Int) : Size

    companion object {
        fun fromString(input: String): Size {
            when {
                input.contains("S") -> return SMALL(input.count { it == 'X' })
                input.contains("M") -> return MEDIUM
                input.contains("L") -> return LARGE(input.count { it == 'X' })
            }
            throw Exception("No size character found")
        }
    }

    fun compare(other: Size): String {
        return when (this) {
            is SMALL -> {
                when (other) {
                    is SMALL -> {
                        if (this.xtras == other.xtras) "="
                        else if (this.xtras > other.xtras) "<" else ">"
                    }
                    MEDIUM -> {
                        "<"
                    }
                    is LARGE -> {
                        "<"
                    }
                }
            }
            MEDIUM -> {
                when (other) {
                    is SMALL -> {
                        ">"
                    }
                    MEDIUM -> {
                        "="
                    }
                    is LARGE -> {
                        "<"
                    }
                }
            }
            is LARGE -> {
                when (other) {
                    is SMALL -> {
                        ">"
                    }
                    MEDIUM -> {
                        ">"
                    }
                    is LARGE -> {
                        if (this.xtras == other.xtras) "="
                        else if (this.xtras < other.xtras) "<" else ">"
                    }
                }
            }
        }
    }
}

