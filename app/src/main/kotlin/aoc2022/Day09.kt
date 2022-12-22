package aoc2022

import java.io.File
import kotlin.math.abs

private val test = """
    R 4
    U 4
    L 3
    D 1
    R 4
    D 1
    L 5
    R 2
""".trimIndent()

private val test2 = """
    R 5
    U 8
    L 8
    D 3
    R 17
    D 10
    L 25
    U 20
""".trimIndent()

typealias Position = Pair<Int, Int>

private fun moveHead(position: Position, direction: String): Position {
    return when (direction) {
        "L" -> {
            Pair(position.first, position.second - 1)
        }
        "R" -> {
            Pair(position.first, position.second + 1)
        }
        "U" -> {
            Pair(position.first - 1, position.second)
        }
        else -> {
            Pair(position.first + 1, position.second)
        }
    }
}

private fun moveTail(headPosition: Position, tailPosition: Position): Position {
    val headX = headPosition.first
    val x = tailPosition.first
    val headY = headPosition.second
    val y = tailPosition.second
    return Pair(
        if (headX > x) x + 1 else if (headX == x) x else x - 1,
        if (headY > y) y + 1 else if (headY == y) y else y - 1,
    )
}

private fun shouldMove(headPosition: Position, tailPosition: Position): Boolean {
    return abs(headPosition.first - tailPosition.first) > 1 || abs(headPosition.second - tailPosition.second) > 1
}

private fun partOne(inp: String): Int {
    val motions = inp.split("\n")

    var headPosition = Pair(0, 0)
    var tailPosition = Pair(0, 0)
    val positions = mutableSetOf(tailPosition)
    motions.forEach { motion ->
        val (direction, step) = motion.split(" ")

        repeat(step.toInt()) {
            headPosition = moveHead(headPosition, direction)
            if (shouldMove(headPosition, tailPosition)) {
                tailPosition = moveTail(headPosition, tailPosition)
            }
            positions.add(tailPosition)
        }
    }

    return positions.size
}

private fun partTwo(inp: String): Int {
    val motions = inp.split("\n")

    val positions = (1..10).map { Pair(0, 0) }.toMutableList()
    val visited = mutableSetOf(Pair(0, 0))
    motions.forEach { motion ->
        val (direction, step) = motion.split(" ")

        repeat(step.toInt()) {
            positions[0] = moveHead(positions.first(), direction)
            positions.drop(1).forEachIndexed { index, position ->
                if (shouldMove(positions[index], position)) {
                    positions[index + 1] = moveTail(positions[index], position)
                }

            }
            visited.add(positions.last())
        }
    }

    return visited.size
}

fun main() {
    val inp = File("app/src/main/resources/day09.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
