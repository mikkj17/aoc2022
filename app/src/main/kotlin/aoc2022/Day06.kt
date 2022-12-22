package aoc2022

import java.io.File

private val test = """
    mjqjpqmgbljsphdztnvjfqwrcgsmlb
""".trimIndent()

private fun compute(inp: String, length: Int): Int {
    var index = length
    for (window in inp.windowed(length)) {
        if (window.toSet().size == length) {
            break
        }
        index++
    }
    return index
}

private fun partOne(inp: String): Int {
    return compute(inp, 4)
}

private fun partTwo(inp: String): Int {
    return compute(inp, 14)
}

fun main() {
    val inp = File("app/src/main/resources/day06.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
