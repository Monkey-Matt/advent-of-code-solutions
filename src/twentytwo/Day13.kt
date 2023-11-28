package twentytwo

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day13_test")
    val part1 = part1(testInput)
    println(part1)
    check(part1 == 13)
    val part2 = part2(testInput)
    println(part2)
    check(part2 == 140)

    println("---")
    val input = readInputLines("Day13_input")
    println(part1(input))
    println(part2(input))
}

private sealed interface Packet {
    data class IntPacket(val int: Int): Packet
    data class ListPacket(val list: List<Packet>): Packet, Comparable<Packet> {
        override fun compareTo(other: Packet): Int {
            return this.comesBefore(other)
        }
    }
}

private fun Packet.ListPacket.comesBefore(other: Packet): Int {
    val otherList = if (other is Packet.ListPacket) other else Packet.ListPacket(listOf(other))
    if (this.list.isEmpty() && otherList.list.isEmpty()) return 0
    val smallestList = minOf(this.list.lastIndex, otherList.list.lastIndex)
    if (smallestList == -1) return this.list.size.compareTo(otherList.list.size)
    for (i in 0 .. smallestList) {
        val a = this.list[i]
        val b = otherList.list[i]
        val result = if (a is Packet.ListPacket) {
            a.comesBefore(b)
        } else if (b is Packet.ListPacket) {
            -b.comesBefore(a)
        } else {
            (a as Packet.IntPacket).int.compareTo((b as Packet.IntPacket).int)
        }
        if (result != 0) return result
    }
    return this.list.size.compareTo(otherList.list.size)
}

private fun String.toListPacket(trim: Boolean = true): Packet.ListPacket {
    val trimmed = if (trim) this.removeSurrounding("[", "]") else this
    if (!trimmed.contains("[")) {
        val items = trimmed.split(",").filter { it.isNotBlank() }.map { it.toIntPacket() }
        return Packet.ListPacket(items)
    }

    val before = trimmed.substringBefore("[")
    val firstIndex = trimmed.indexOf("[")
    var index = firstIndex+1
    var nestingCount = 1
    while (nestingCount > 0) {
        when (trimmed[index]) {
            '[' -> nestingCount++
            ']' -> nestingCount--
        }
        index++
    }
    val beforeItems = before.split(",").filter { it.isNotBlank() }.map { it.toIntPacket() }
    val list = trimmed.substring(firstIndex, index).toListPacket()
    val afterItems = if (index < trimmed.lastIndex) {
        trimmed
            .substring(index+1)
            .removeSurrounding(",")
            .toListPacket(false)
            .list
    } else {
        emptyList()
    }
    val items = mutableListOf<Packet>()
    items.addAll(beforeItems)
    items.add(list)
    items.addAll(afterItems)
    return Packet.ListPacket(items)
}

private fun String.toIntPacket(): Packet.IntPacket = Packet.IntPacket(this.toInt())


private fun part1(input: List<String>): Int {
    val pairs: List<List<String>> = input.split("")
    val packetPairs = pairs.map { (a, b) ->
        Pair(a.toListPacket(), b.toListPacket())
    }
    val correctOrder = packetPairs.map { (a, b) ->
        a.compareTo(b)
    }

    return correctOrder
        .mapIndexed { index, i -> if (i == -1) index+1 else 0 }
        .sum()
}

private fun part2(input: List<String>): Int {
    val specialItem1 = Packet.ListPacket(listOf(Packet.ListPacket(listOf(Packet.IntPacket(2)))))
    val specialItem2 = Packet.ListPacket(listOf(Packet.ListPacket(listOf(Packet.IntPacket(6)))))

    val packets = input.filter { it != "" }.map { it.toListPacket() }.toMutableList()
    packets.add(specialItem1)
    packets.add(specialItem2)
    packets.sort()

    val spot1 = packets.indexOf(specialItem1)+1
    val spot2 = packets.indexOf(specialItem2)+1
    return spot1 * spot2
}
