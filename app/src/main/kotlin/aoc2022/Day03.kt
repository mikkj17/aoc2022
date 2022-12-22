package aoc2022

import java.io.File

private val test = """
    vJrwpWtwJgWrhcsFMMfFFhFp
    jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
    PmmdzqPrVvPwwTWBwg
    wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
    ttgJtRGJQctTZtZT
    CrZsJsPPZsGzwwsLwLmpwMDw
""".trimIndent()

private fun getPriority(item: Char): Int {
    val prio = mutableListOf<Char>()
    ('a'..'z').forEach { prio.add(it) }
    ('A'..'Z').forEach { prio.add(it) }
    return prio.indexOf(item) + 1
}

private fun partOne(inp: String): Int {
    return inp.split("\n").sumOf { rucksack ->
        val middle = rucksack.length / 2
        val (first, second) = rucksack.chunked(middle).map { it.toSet() }
        getPriority(first.intersect(second).first())
    }
}

private fun partTwo(inp: String): Int {
    return inp.split("\n").chunked(3).sumOf { chunk ->
        val (first, second, third) = chunk.map { it.toSet() }
        getPriority(first.intersect(second).intersect(third).first())
    }
}

fun main() {
    val inp = File("app/src/main/resources/day03.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
