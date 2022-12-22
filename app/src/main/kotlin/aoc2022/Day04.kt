package aoc2022

import java.io.File

private val test = """
    2-4,6-8
    2-3,4-5
    5-7,7-9
    2-8,3-7
    6-6,4-6
    2-6,4-8
""".trimIndent()

private fun compute(inp: String, part: Int): Int {
    return inp.split("\n").filter { pair ->
        val (first, second) = pair.split(",").map { range ->
            range.split("-").map { it.toInt() }
        }.map { (it.first()..it.last()).toSet() }
        if (part == 1) {
            first.minus(second).isEmpty() || second.minus(first).isEmpty()
        }
        else {
            first.intersect(second).isNotEmpty() || second.intersect(first).isNotEmpty()
        }
    }.size
}

private fun partOne(inp: String): Int {
    return compute(inp, 1)
}

private fun partTwo(inp: String): Int {
    return compute(inp, 2)
}

fun main() {
    val inp = File("app/src/main/resources/day04.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
