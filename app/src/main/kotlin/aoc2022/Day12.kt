package aoc2022

import java.io.File
import java.util.PriorityQueue

private val test = """
    Sabqponm
    abcryxxl
    accszExk
    acctuvwj
    abdefghi
""".trimIndent()

private fun getCoords(inp: String): Map<Position, Char> {
    val coords = mutableMapOf<Position, Char>()
    for ((i, row) in inp.split("\n").withIndex()) {
        for ((j, c) in row.withIndex()) {
            coords[Pair(i, j)] = c
        }
    }

    return coords
}

private fun decode(c: Char): Char {
    return mapOf(
        'S' to 'a',
        'E' to 'z',
    ).getOrDefault(c, c)
}

private fun dijkstra(
    coords: Map<Position, Char>,
    start: Position,
    end: Position,
): Int{
    val visited = mutableSetOf<Position>()
    val queue = PriorityQueue<Pair<Position, Int>>(compareBy { it.second })
    queue.add(Pair(start, 0))

    while (queue.isNotEmpty()) {
        val (pos, cost) = queue.poll()
        if (pos == end) {
            return cost
        }
        else if (pos in visited) {
            continue
        }
        else {
            visited.add(pos)
        }

        val currentChar = decode(coords.getValue(pos))
        val adjacent = listOf(-1, 1).flatMap {
            listOf(Pair(pos.first + it, pos.second), Pair(pos.first, pos.second + it))
        }
        for (neighbour in adjacent) {
            if (!coords.containsKey(neighbour)) {
                continue
            }

            val neighbourChar = decode(coords.getValue(neighbour))
            if (currentChar + 1 >= neighbourChar) {
                queue.add(Pair(neighbour, cost + 1))
            }
        }
    }

    return Int.MAX_VALUE
}

private fun partOne(inp: String): Int {
    val coords = getCoords(inp)
    val start = coords.filter { it.value == 'S' }.keys.first()
    val end = coords.filter { it.value == 'E' }.keys.first()
    return dijkstra(coords, start, end)
}

private fun partTwo(inp: String): Int {
    val coords = getCoords(inp)
    val starts = coords.filter { it.value == 'a' }.keys
    val end = coords.filter { it.value == 'E' }.keys.first()
    return starts.minOf { dijkstra(coords, it, end) }
}

fun main() {
    val inp = File("app/src/main/resources/day12.txt").readText().trim()
    println(partOne(inp))
    println(partTwo(inp))
}
