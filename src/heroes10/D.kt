package heroes10

private val testInput = """
12
5
0 0 0 0 0
4
0 13 15 8
4
13 15 0 8
8
1 2 3 4 5 6 7 8
5
99999999 100000000 99999999 99999999 99999999
5
2 3 4 3 2
1
0
1
3
3
1 2 3 0
5
5 4 3 2 1
5
0 3 3 3 0
17
1 2 3 0 0 4 5 6 0 123 456 789 0 4 3 2 1
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
        readStr()
        val input = readInts()
        val lists = input.split(0)
        val answer = lists.sumOf { list ->
            if (list.isEmpty()) {
                0
            } else if (list.size == 1) {
                list.first()
            } else if ((list.size % 2) == 0) {
                list.chunked(2) { (a, b) -> 2 * (a + b) }.sum()
            } else {
                val editedList = list.toMutableList()
                editedList[1] = 0
                editedList[editedList.lastIndex - 1] = 0
                val maxValue = editedList.max()
                val biggestIndex = editedList.indexOf(editedList.max())
                val firstList = list.subList(0, biggestIndex)
                val secondList = if (biggestIndex == list.lastIndex) emptyList() else {
                    list.subList(biggestIndex + 1, list.size)
                }
                val firstValue = firstList.chunked(2) { (a, b) -> 2 * (a + b) }.sum()
                val secondValue = secondList.chunked(2) { (a, b) -> 2 * (a + b) }.sum()
                firstValue + maxValue + secondValue
            }
        }
        println(answer)
    }
}

private fun <T> List<T>.split(splitOn: T): List<List<T>> {
    val groups = mutableListOf<MutableList<T>>()
    var sublistCount = 0
    this.forEach { item ->
        if (item == splitOn) {
            sublistCount++
        } else {
            if (groups.size <= sublistCount) {
                groups.add(mutableListOf())
            }
            if (groups.size <= sublistCount) {
                groups.add(mutableListOf())
            }
            val sublist = groups[sublistCount]
            sublist.add(item)
        }
    }
    return groups
}