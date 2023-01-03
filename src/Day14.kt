fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day14_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 24)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 93)

    println("---")
    val input = readInputLines("Day14_input")
    println(part1(input))
    println(part2(input))
}


private fun part1(input: List<String>): Int {
    val map = createSandMap(input)

    MapPrinter(map).printInterestingRange()

    while (true) {
        val stayedOnMap = map.dropSandEndless()
        if (!stayedOnMap) break
    }

    return map.sandCount
}


private fun part2(input: List<String>): Int {
    val map = createSandMap(input)

//    MapPrinter(map).printInterestingRange()

    while (true) {
        val sourceBlocked = map.dropSandWithBottom()
        if (sourceBlocked) break
    }

    return map.sandCount
}

private fun createSandMap(input: List<String>): SandMap {
    val map = SandMap()
    val pointLists = input.map { line ->
        line
            .split(" -> ")
            .map { point ->
                val (x, y) = point.split(",").map { it.toInt() }
                Point(x, y)
            }
    }

    pointLists.forEach { points ->
        points.windowed(2).forEach { (a, b) ->
            map.addLine(a, b)
        }
    }
    return map
}

private class SandMap {
    private val map: MutableList<MutableList<Thing>> =
        MutableList(800) { MutableList(400) { Thing.EMPTY } }

    val sandCount: Int
        get() {
            var count = 0
            map.forEach { line ->
                count += line.count { it == Thing.SAND }
            }
            return count
        }

    fun getAt(x: Int, y: Int): Thing {
        return map[x][y]
    }

    fun addLine(point1: Point, point2: Point) {
        for (x in point1.x toward point2.x) {
            for (y in point1.y toward point2.y) {
                map[x][y] = Thing.ROCK
            }
        }
    }

    /**
     * drops one piece of sand into the map, a map where sand can fall off
     * return true if the sand stayed within the map
     */
    fun dropSandEndless(): Boolean {
        val bottomHeight = getInterestingRange().second.y
        val startPosition = Point(500, 0)
        var sandPosition = startPosition

        while (true) {
            val belowPosition = Point(sandPosition.x, sandPosition.y + 1)
            val belowThing = getAt(belowPosition.x, belowPosition.y)
            val leftPosition = Point(sandPosition.x - 1, sandPosition.y + 1)
            val leftThing = getAt(leftPosition.x, leftPosition.y)
            val rightPosition = Point(sandPosition.x + 1, sandPosition.y + 1)
            val rightThing = getAt(rightPosition.x, rightPosition.y)
            if (belowThing == Thing.EMPTY) {
                sandPosition = belowPosition
            } else if (leftThing == Thing.EMPTY) {
                sandPosition = leftPosition
            } else if (rightThing == Thing.EMPTY) {
                sandPosition = rightPosition
            } else {

                map[sandPosition.x][sandPosition.y] = Thing.SAND
                return true
            }

            if (sandPosition.y >= bottomHeight + 1) {
                return false
            }
        }
    }

    /**
     * drops one piece of sand into the map, a map which has a bottom line that sits below all
     * other lines
     * return true if the sand blocked the start position
     */
    fun dropSandWithBottom(): Boolean {
        val bottomHeight = getInterestingRange().second.y
        val startPosition = Point(500, 0)
        var sandPosition = startPosition

        while (true) {
            val belowPosition = Point(sandPosition.x, sandPosition.y + 1)
            val belowThing = getAt(belowPosition.x, belowPosition.y)
            val leftPosition = Point(sandPosition.x - 1, sandPosition.y + 1)
            val leftThing = getAt(leftPosition.x, leftPosition.y)
            val rightPosition = Point(sandPosition.x + 1, sandPosition.y + 1)
            val rightThing = getAt(rightPosition.x, rightPosition.y)
            if (belowThing == Thing.EMPTY) {
                sandPosition = belowPosition
            } else if (leftThing == Thing.EMPTY) {
                sandPosition = leftPosition
            } else if (rightThing == Thing.EMPTY) {
                sandPosition = rightPosition
            } else {
                map[sandPosition.x][sandPosition.y] = Thing.SAND
                return sandPosition == Point(500, 0)
            }

            if (sandPosition.y >= bottomHeight + 1) {
                map[sandPosition.x][sandPosition.y] = Thing.SAND
                return false
            }
        }
    }

    /**
     * The range that contains rocks
     * Returns pair of start/lowest to end/highest position of things
     */
    fun getInterestingRange(): Pair<Point, Point> {
        val smallestX = map.indexOfFirst { it.contains(Thing.ROCK) }
        val largestX = map.indexOfLast { it.contains(Thing.ROCK) }
        var largestY = 0
        map.forEach { list ->
            val last = list.indexOfLast { it == Thing.ROCK }
            if (last > largestY) largestY = last
        }
        return Pair(Point(smallestX, 0), Point(largestX, largestY))
    }
}

private data class Point(val x: Int, val y: Int)

private enum class Thing {
    SAND, ROCK, EMPTY
}

private infix fun Int.toward(to: Int): IntProgression {
    val step = if (this > to) -1 else 1
    return IntProgression.fromClosedRange(this, to, step)
}

private class MapPrinter(val map: SandMap) {
    private val interestingPoints = map.getInterestingRange()
    private val smallestPoint = interestingPoints.first
    private val largestPoint = interestingPoints.second

    fun printInterestingRange() {
        printXNumbers()
        for (i in smallestPoint.y .. largestPoint.y) {
            printLine(i)
        }

    }

    private fun printXNumbers() {
        print("    ")
        for (i in smallestPoint.x .. largestPoint.x) {
            print(i.toString()[0])
        }
        print("\n")
        print("    ")
        for (i in smallestPoint.x .. largestPoint.x) {
            print(i.toString()[1])
        }
        print("\n")
        print("    ")
        for (i in smallestPoint.x .. largestPoint.x) {
            print(i.toString()[2])
        }
        print("\n")
    }

    private fun printLine(y: Int) {
        print(y)
        val ySize = y.toString().count()
        repeat(4 - ySize) { print(" ") }
        for (i in smallestPoint.x .. largestPoint.x) {
            if (y == 0 && i == 500) {
                print("+")
                continue
            }
            print(map.getAt(i, y).symbol)
        }
        print("\n")
    }

    private val Thing.symbol: String
        get() = when (this) {
            Thing.SAND -> "O"
            Thing.ROCK -> "#"
            Thing.EMPTY -> "."
        }
}
