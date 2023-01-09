import java.util.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInputLines("Day07_test")
    println(part1(testInput))
    check(part1(testInput) == 95_437)
    println(part2(testInput))
    check(part2(testInput) == 24_933_642)

    println("---")
    val input = readInputLines("Day07_input")
    println(part1(input))
    println(part2(input))

    testAlternativeSolutions()
}

private interface DirectoryElement {
    val size: Int
}

private class Directory(val name: String) : DirectoryElement {
    val directoryElements = mutableListOf<DirectoryElement>()

    override val size: Int
        get() = directoryElements.sumOf { it.size }

    fun add(directoryElement: DirectoryElement) {
        directoryElements.add(directoryElement)
    }
}

private data class DirectoryFile(override val size: Int, val name: String) : DirectoryElement

private sealed interface Command {
    object CDHome: Command
    object LS: Command
    data class Directory(val name: String): Command
    data class File(val size: Int, val name: String): Command
    data class CDInto(val directoryName: String): Command
    object CDOut: Command
}

private fun String.toCommand(): Command {
    if (this == "$ cd /") return Command.CDHome
    if (this == "$ ls") return Command.LS
    if (this == "$ cd ..") return Command.CDOut
    if (this.startsWith("dir ")) return Command.Directory(this.removePrefix("dir "))
    if (this.startsWith("$ cd ")) return Command.CDInto(this.removePrefix("$ cd "))
    val (size, name) = this.split(" ")
    return Command.File(size.toInt(), name)
}


private fun part1(input: List<String>): Int {
    val homeDirectory = input.toFileStructure()
    val dirsUnderSize = dirsUnderSize(homeDirectory, 100_000)
    return dirsUnderSize.sumOf { it.size }
}
private fun part2(input: List<String>): Int {
    val homeDirectory = input.toFileStructure()
    val requiredExtraSpace = homeDirectory.size - (70_000_000 - 30_000_000)
    val dirsOverSize = dirsOverSize(homeDirectory, requiredExtraSpace)
    return dirsOverSize.minBy { it.size }.size
}

private fun List<String>.toFileStructure(): Directory {
    val homeDirectory = Directory("/")
    val directoryStack = Stack<Directory>()
    directoryStack.push(homeDirectory)

    fun currentDirectory() = directoryStack.peek()

    this.forEach { inputLine ->
        when (val command = inputLine.toCommand()) {
            Command.CDHome -> {
                directoryStack.empty()
                directoryStack.add(homeDirectory)
            }
            is Command.CDInto -> {
                val directoryName = command.directoryName
                val dir = currentDirectory().directoryElements.filterIsInstance<Directory>().find { it.name == directoryName }
                directoryStack.push(dir)
            }
            Command.CDOut -> directoryStack.pop()
            is Command.Directory -> {
                val newDir = Directory(command.name)
                if (!currentDirectory().directoryElements.contains(newDir)) {
                    currentDirectory().add(newDir)
                }
            }
            is Command.File -> {
                val newFile = DirectoryFile(command.size, command.name)
                if (!currentDirectory().directoryElements.contains(newFile)) {
                    currentDirectory().add(newFile)
                }
            }
            Command.LS -> {}
        }
    }
    return homeDirectory
}

private fun dirsUnderSize(parentDir: Directory, size: Int): List<Directory> {
    val answer = mutableListOf<Directory>()
    if (parentDir.size <= size) {
        answer.add(parentDir)
    }
    parentDir.directoryElements.filterIsInstance<Directory>().forEach {
        answer.addAll(dirsUnderSize(it, size))
    }
    return answer
}

private fun dirsOverSize(parentDir: Directory, size: Int): List<Directory> {
    val answer = mutableListOf<Directory>()
    if (parentDir.size >= size) {
        answer.add(parentDir)
    }
    parentDir.directoryElements.filterIsInstance<Directory>().forEach {
        answer.addAll(dirsOverSize(it, size))
    }
    return answer
}

// ------------------------------------------------------------------------------------------------

private fun testAlternativeSolutions() {
    val testInput = readInputLines("Day07_test")
    check(part1AlternativeSolution(testInput) == 95_437)
    check(part2AlternativeSolution(testInput) == 24_933_642)

    println("Alternative Solutions:")
    val input = readInputLines("Day07_input")
    println(part1AlternativeSolution(input))
    println(part2AlternativeSolution(input))
}

private fun part1AlternativeSolution(input: List<String>): Int {
    val directories = constructDirMap(input)
    return directories.values.filter { it <= 100_000 }.sum()
}

private fun part2AlternativeSolution(input: List<String>): Int {
    val directories = constructDirMap(input)

    val totalSpace = 70_000_000
    val requiredForUpdate = 30_000_000
    val usedSpace: Int = directories["/"]!!
    val currentFreeSpace = totalSpace - usedSpace
    val extraFreeSpaceRequired = requiredForUpdate - currentFreeSpace

    return directories.values.filter { it >= extraFreeSpaceRequired }.min()
}

/**
 * Return map of directory path to directory size
 */
private fun constructDirMap(input: List<String>): Map<String, Int> {
    var currentPath = "/"
    val dirMap = mutableMapOf<String, Int>()
    for (line in input) {
        when {
            line.startsWith("$ cd ") -> {
                currentPath = when (val dirName = line.substringAfter("$ cd ")) {
                    "/" -> "/"
                    ".." -> currentPath.substringBeforeLast("/")
                    else -> if (currentPath == "/") "/$dirName" else "$currentPath/$dirName"
                }
            }
            line[0].isDigit() -> {
                val (size, _) = line.split(" ")
                var path = currentPath
                while (true) {
                    dirMap[path] = (dirMap[path] ?: 0) + size.toInt()
                    if (path == "/") break
                    path = path.substringBeforeLast("/")
                    if (path == "") path = "/"
                }
            }
            else -> { /* we don't care about this line */ }
        }
    }
    return dirMap
}
