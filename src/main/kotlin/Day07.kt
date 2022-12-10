import java.io.File

private val test = """
    $ cd /
    $ ls
    dir a
    14848514 b.txt
    8504156 c.dat
    dir d
    $ cd a
    $ ls
    dir e
    29116 f
    2557 g
    62596 h.lst
    $ cd e
    $ ls
    584 i
    $ cd ..
    $ cd ..
    $ cd d
    $ ls
    4060174 j
    8033020 d.log
    5626152 d.ext
    7214296 k
""".trimIndent()


private data class F1le(val name: String, val size: Int)

private class Directory(
    val name: String,
    val parent: Directory? = null,
    val children: MutableList<Directory> = mutableListOf(),
    val files: MutableList<F1le> = mutableListOf(),
) {

    override fun toString(): String {
        return "Directory(name=$name)"
    }

    fun addChildren(content: List<String>) {
        val filePattern = Regex("""^(\d+) ([\w.]+)$""")
        content.forEach { item ->
            val fileMatch = filePattern.matchEntire(item)
            if (fileMatch != null) {
                val (size, name) = fileMatch.groupValues.drop(1)
                files.add(F1le(name, size.toInt()))
            }
        }
    }
}

private fun firstOrCreate(
    allDirectories: MutableList<Directory>,
    parent: Directory,
    childName: String,
): Directory {
    var child = parent.children.firstOrNull { it.name == childName }

    if (child == null) {
        child = Directory(childName, parent)
        parent.children.add(child)
        allDirectories.add(child)
    }
    return child
}

private fun parse(inp: String): MutableList<Directory> {
    val cdPattern = Regex("""^\$ cd ([\w.]+)$""")
    val lsPattern = Regex("""^\$ ls$""")

    val rows = inp.split("\n")
    var currentDirectory = Directory("/")
    val allDirectories = mutableListOf(currentDirectory)
    var rowIndex = 1
    while (rowIndex < rows.size) {
        val currentRow = rows[rowIndex++]

        val cdMatch = cdPattern.matchEntire(currentRow)
        if (cdMatch != null) {
            val dirName = cdMatch.groupValues.last()
            currentDirectory = if (dirName == "..") {
                currentDirectory.parent!!
            } else {
                firstOrCreate(allDirectories, currentDirectory, dirName)
            }
        }

        val lsMatch = lsPattern.matchEntire(currentRow)
        if (lsMatch != null) {
            val dirContent = rows.drop(rowIndex).takeWhile { !it.startsWith("$") }
            currentDirectory.addChildren(dirContent)
            rowIndex += dirContent.size
        }
    }

    return allDirectories
}

private fun getSize(directory: Directory): Int {
    var size = directory.files.sumOf { it.size }
    directory.children.forEach { size += getSize(it) }
    return size
}

private fun partOne(inp: String): Int {
    val allDirectories = parse(inp)
    return allDirectories.map { getSize(it) }.filter { it <= 100000 }.sum()
}

private fun partTwo(inp: String): Int {
    val allDirectories = parse(inp)
    val total = 70000000
    val updateSize = 30000000
    val used = getSize(allDirectories.first())
    val unused = total - used
    return allDirectories.map { getSize(it) }.filter { (unused + it) >= updateSize }.min()
}

fun main() {
    val inp = File("src/main/resources/day07.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
