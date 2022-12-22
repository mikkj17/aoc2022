package aoc2022

import java.io.File

private val test = """
    A Y
    B X
    C Z
""".trimIndent()

private val transform = mapOf(
    Pair("A", "R"),
    Pair("B", "P"),
    Pair("C", "S"),
    Pair("X", "R"),
    Pair("Y", "P"),
    Pair("Z", "S"),
)

private val wins = mapOf(
    Pair("R", "S"),
    Pair("P", "R"),
    Pair("S", "P"),
)

private val loses = mapOf(
    Pair("R", "P"),
    Pair("P", "S"),
    Pair("S", "R"),
)

private val scores = mapOf(
    Pair("R", 1),
    Pair("P", 2),
    Pair("S", 3),
)

private fun scoreOne(row: String): Int {
    val (opponent, me) = row.split(" ").map { transform[it] }
    val score = if (wins[me] == opponent) 6 else if (opponent == me) 3 else 0
    return score + scores[me]!!
}

private fun partOne(inp: String): Int {
    return inp.split("\n").sumOf { scoreOne(it) }
}

private fun scoreTwo(row: String): Int {
    var (opponent, mode) = row.split(" ")
    opponent = transform[opponent]!!
    val me: String
    val score: Int
    when (mode) {
        "Z" -> {
            me = loses[opponent]!!
            score = 6
        }
        "Y" -> {
            me = opponent
            score = 3
        }
        else -> {
            me = wins[opponent]!!
            score = 0
        }
    }
    return score + scores[me]!!
}

private fun partTwo(inp: String): Int {
    return inp.split("\n").sumOf { scoreTwo(it) }
}

fun main() {
    val inp = File("app/src/main/resources/day02.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
