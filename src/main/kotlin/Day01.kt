import java.io.File

private val test = """
    1000
    2000
    3000

    4000

    5000
    6000

    7000
    8000
    9000

    10000
""".trimIndent()

private fun partOne(inp: String): Int {
    return inp.split("\n\n").maxOf { elf -> elf.split("\n").sumOf { it.toInt() } }
}

private fun partTwo(inp: String): Int {
    return inp.split("\n\n").map { elf ->
        elf.split("\n").sumOf { it.toInt() }
    }.sortedBy { it }.takeLast(3).sum()
}

fun main() {
    val inp = File("src/main/resources/day01.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
